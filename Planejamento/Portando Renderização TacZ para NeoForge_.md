

# **Technical Guide: Porting Custom Item Rendering from Forge 1.20.1 to NeoForge 1.21.1 \- A Deep Dive into IClientItemExtensions**

## **Section 1: Architectural Evolution of Item Rendering in NeoForge 1.21**

The migration of custom item rendering systems from Forge 1.20.1 to NeoForge 1.21.1 represents more than a simple API update; it reflects a fundamental architectural shift in how the game engine handles client-side item properties. The transition from a direct BlockEntityWithoutLevelRenderer (BEWLR) association to the comprehensive IClientItemExtensions interface is a deliberate move towards a more centralized, capable, and maintainable design pattern. Understanding this evolution is critical for a successful and robust port.

### **The Legacy Paradigm (Forge 1.20.1): Direct Renderer Association**

In the Forge 1.20.1 environment, associating a custom renderer with an item was a direct and singular action. Developers would typically create a class extending BlockEntityWithoutLevelRenderer and then, within the Item.Properties during item registration, specify an instance of this renderer. This established a simple, one-to-one relationship: this specific item uses this specific renderer.

While functional for its intended purpose—rendering dynamic models for items that could not be represented by static JSON files—this approach had limitations. It solved only one piece of the client-side puzzle. Other custom behaviors, such as unique first-person hand animations, third-person player model poses, or custom armor models, required entirely separate systems. A developer might need to subscribe to various render events, use different hooks, or employ other disparate mechanisms to achieve a full suite of custom visual features. This resulted in logic that was scattered across multiple classes and event handlers, making the codebase less cohesive and harder to maintain.

### **The Modern Paradigm (NeoForge 1.21.1): The IClientItemExtensions Interface**

NeoForge 1.21.1 introduces IClientItemExtensions as a centralized hub for virtually all special client-side behaviors an item might possess. This is the cornerstone of the new architecture. It's crucial to recognize that the BlockEntityWithoutLevelRenderer class itself has not been removed; its role in performing the actual rendering operations remains intact. What has fundamentally changed is the *method of providing it* to the game's rendering engine.1

The IClientItemExtensions interface is a comprehensive contract that an item can fulfill to declare its various client-side properties. A review of its methods reveals its broad scope 4:

* getCustomRenderer(): This is the new, designated location for providing the BEWLR instance for custom item model rendering.  
* applyForgeHandTransform(PoseStack, LocalPlayer,...): A dedicated hook for applying transformations to the item model in first-person view, perfect for custom animations like aiming down sights or unique swing motions.5  
* getArmPose(LivingEntity, InteractionHand,...): Allows the item to specify a custom HumanoidModel.ArmPose for the entity holding it, enabling unique third-person stances and animations.6  
* getHumanoidArmorModel(LivingEntity,...): The proper mechanism for providing a custom HumanoidModel when the item is worn as armor.  
* getFont(ItemStack,...): Enables the use of a custom Font for rendering text or information on the item, such as an ammo counter.

This consolidation demonstrates that IClientItemExtensions is far more than a simple BEWLR provider. It is a complete client-side properties object that encapsulates a wide range of rendering and animation concerns.

This architectural change mirrors the design philosophy of the well-established server-side Capability system. In the same way that capabilities bundle related functionalities (e.g., IItemHandler, IEnergyStorage) into a single, queryable interface attached to an object like an ItemStack or Entity, IClientItemExtensions does the same for client-side behaviors. The core game engine no longer needs to know the specific class of an item to determine how to render it. Instead, it follows a more abstract and robust pattern: it asks the item, "Do you provide client extensions? If so, give me your custom renderer," or "give me your current arm pose."

This promotes loose coupling and better adherence to the Single Responsibility Principle. The Item class is responsible for its core properties and logic, while the IClientItemExtensions implementation is solely responsible for its client-side presentation. This design is not an isolated change but part of a broader effort in NeoForge 1.21 to abstract and reorganize the rendering pipeline, as evidenced by the significant reworks to shaders, vertex handling, and model loading systems.7 Embracing this new pattern, rather than merely patching the old code, will result in mods that are more resilient, maintainable, and aligned with the future direction of the platform.

## **Section 2: Implementing IClientItemExtensions: The New Foundation**

The practical implementation of this new architecture is channeled through a single method in the Item class. This provides a clean and consistent entry point for all custom client-side behaviors, streamlining a process that was previously fragmented.

### **The initializeClient Method: Your New Entry Point**

The sole method that must be overridden in an Item class to engage with this system is initializeClient(Consumer\<IClientItemExtensions\>).1 The game engine calls this method for each item during the client initialization phase. The mod's responsibility is to "consume" the provided

Consumer object by passing it an instance of its IClientItemExtensions implementation.

The use of a Consumer is a key aspect of this design. It represents a form of Inversion of Control (IoC). Rather than the mod imperatively telling the game engine, "Here is my renderer, set it now," the mod instead provides a declarative factory or provider object (IClientItemExtensions). The game engine retains control, holding onto this provider and deciding *when* to query it for information, such as calling getCustomRenderer() when the ItemRenderer is being set up or applyForgeHandTransform() during each frame's viewmodel render pass.

This deferred execution model has a subtle but important implication for initialization logic. The IClientItemExtensions instance and its renderer should be lightweight. Any resource-intensive setup, like retrieving models from the ModelManager or acquiring vertex buffers, should not happen in the renderer's constructor. Instead, this work should be performed inside the renderByItem method itself, which is called every frame and has access to the necessary rendering context. The GeckoLib animation library's examples demonstrate this principle effectively by lazily instantiating the renderer within the provider, ensuring it is only created when first needed.10

### **Implementation Strategies: Anonymous Class vs. Static Instance**

There are two primary strategies for providing the IClientItemExtensions instance, each with its own advantages.

#### **Strategy 1: The Anonymous Inner Class**

The most direct and common implementation involves creating a new anonymous inner class directly within the initializeClient method. This approach is ideal for items that have a unique set of client-side behaviors.

Java

// In your item class  
@Override  
public void initializeClient(java.util.function.Consumer\<net.minecraftforge.client.extensions.common.IClientItemExtensions\> consumer) {  
    consumer.accept(new IClientItemExtensions() {  
        private final BlockEntityWithoutLevelRenderer renderer \= new GunItemRendererWrapper();

        @Override  
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {  
            return renderer;  
        }

        // Other method overrides for animations, etc. would go here  
    });  
}

This pattern is simple, self-contained, and easy to read, as the client-side behavior is defined directly within the item it applies to.1

#### **Strategy 2: The Cached Static Instance**

For mods that feature multiple items sharing the exact same client-side behavior (e.g., a set of tools with identical models and animations, differing only in texture), creating a new anonymous class for each one is inefficient. It results in multiple identical object instances being created and stored in memory. A more performant approach is to use a single, shared, static instance.

First, a dedicated class implementing IClientItemExtensions is created.

Java

// In a separate client-side file, e.g., MyClientExtensions.java  
public class MySharedClientExtensions implements IClientItemExtensions {  
    public static final MySharedClientExtensions INSTANCE \= new MySharedClientExtensions();  
    private final BlockEntityWithoutLevelRenderer renderer \= new MySharedItemRenderer();

    @Override  
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {  
        return renderer;  
    }  
}

Then, all relevant items can refer to this single instance in their initializeClient method.

Java

// In each of your items that share the behavior  
@Override  
public void initializeClient(java.util.function.Consumer\<net.minecraftforge.client.extensions.common.IClientItemExtensions\> consumer) {  
    consumer.accept(MySharedClientExtensions.INSTANCE);  
}

This pattern ensures that only one instance of the extension and its associated renderer is ever created, reducing the mod's memory footprint, especially in cases with a large number of similar items.

## **Section 3: Practical Migration of ModernKineticGunItem.java**

This section provides a concrete, step-by-step code transformation for the ModernKineticGunItem class, migrating it from the legacy Forge 1.20.1 registration pattern to the modern NeoForge 1.21.1 IClientItemExtensions system.

### **ModernKineticGunItem.java \- Before (Forge 1.20.1)**

In the 1.20.1 environment, the association with the custom renderer was typically done within the Item.Properties. The code would have likely resembled the following, where a BlockEntityWithoutLevelRenderer was passed directly to the properties builder.

Java

// LIKELY 1.20.1 IMPLEMENTATION (FOR ILLUSTRATION)  
package com.tacz.guns.item;

import com.tacz.guns.client.renderer.item.GunItemRenderer;  
import net.minecraft.world.item.Item;  
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ModernKineticGunItem extends GunItem {  
    public ModernKineticGunItem() {  
        // The old way: BEWLR is passed into the properties, which is now removed.  
        super(new Item.Properties().stacksTo(1).setNoRepair()  
           .setISTER(() \-\> GunItemRenderer::new)); //.setISTER is a conceptual representation of the old method.  
    }  
      
    //... other item logic...  
}

### **ModernKineticGunItem.java \- After (NeoForge 1.21.1)**

The updated class for NeoForge 1.21.1 removes the renderer logic from the constructor and implements the initializeClient method. This new implementation is cleaner, more explicit, and provides the necessary hooks for advanced animations, which will be detailed later.

Java

// FINAL 1.21.1 IMPLEMENTATION  
package com.tacz.guns.item;

import com.tacz.guns.client.renderer.item.GunItemRendererWrapper;  
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;  
import net.minecraft.world.item.Item;  
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ModernKineticGunItem extends GunItem {

    public ModernKineticGunItem() {  
        // The constructor is now clean of any rendering logic.  
        // It only passes standard Item.Properties to the superclass.  
        super(new Item.Properties().stacksTo(1).setNoRepair());  
    }

    /\*\*  
     \* This is the new entry point for all client-side item behaviors in NeoForge 1.21.1.  
     \* It is called by the game during initialization to get a provider for client-side properties.  
     \*/  
    @Override  
    public void initializeClient(Consumer\<IClientItemExtensions\> consumer) {  
        consumer.accept(new IClientItemExtensions() {  
            // A single, cached instance of the renderer is created here to avoid  
            // creating new objects on every call, which is a performance best practice.  
            private GunItemRendererWrapper renderer;

            /\*\*  
             \* This method provides the custom renderer instance to the game engine.  
             \* It replaces the old Item.Properties.setISTER() method.  
             \* @return The instance of our custom item renderer.  
             \*/  
            @Override  
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {  
                if (renderer \== null) {  
                    renderer \= new GunItemRendererWrapper();  
                }  
                return renderer;  
            }

            /\*\*  
             \* This is the hook for first-person viewmodel animations (e.g., aiming down sights).  
             \* We will implement this in Section 5\. For now, returning false allows  
             \* the default vanilla hand rendering to occur.  
             \* @return true to override vanilla transforms, false to allow them.  
             \*/  
            @Override  
            public boolean applyForgeHandTransform(com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.player.LocalPlayer player, net.minecraft.world.entity.HumanoidArm arm, net.minecraft.world.item.ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {  
                // TODO: Implement first-person animation logic here.  
                return false;  
            }

            /\*\*  
             \* This is the hook for third-person player model animations.  
             \* We will implement this in Section 5\. Returning null or ArmPose.EMPTY  
             \* results in the default player holding animation.  
             \* @return A custom ArmPose to change the player model's animation.  
             \*/  
            @Override  
            public net.minecraft.client.model.HumanoidModel.ArmPose getArmPose(net.minecraft.world.entity.LivingEntity entityLiving, net.minecraft.world.InteractionHand hand, net.minecraft.world.item.ItemStack itemStack) {  
                // TODO: Implement third-person animation logic here.  
                return net.minecraft.client.model.HumanoidModel.ArmPose.EMPTY;  
            }  
        });  
    }  
      
    //... other existing item logic...  
}

This refactored code correctly ports the custom rendering registration. It explicitly separates the core item logic from the client-side presentation and prepares the class for the straightforward integration of animation logic through the newly provided hooks.

## **Section 4: Adapting GunItemRendererWrapper.java for the Modern Pipeline**

While the method of registering a custom renderer has changed significantly, the renderer class itself remains remarkably stable. Developers who have invested significant effort in complex rendering logic within their BlockEntityWithoutLevelRenderer subclass will find that the core of their work does not need a complete rewrite. The focus of adaptation shifts to correctly handling the provided rendering contexts.

### **Class Structure and renderByItem**

The GunItemRendererWrapper class can, and should, continue to extend BlockEntityWithoutLevelRenderer. The primary method, renderByItem, which serves as the entry point for all rendering operations, retains its signature from previous versions.1

Its parameters remain:

* ItemStack itemStack: The specific item stack being rendered, useful for retrieving data components that might affect the render (e.g., attachments, skins).  
* ItemDisplayContext displayContext: A critical enum that specifies the context in which the item is being rendered (e.g., in the GUI, in-hand, on the ground).  
* PoseStack poseStack: The transformation stack used to position, rotate, and scale the model.  
* MultiBufferSource bufferSource: The provider for VertexConsumer instances, used to draw the model's vertices.  
* int combinedLight: The packed light value at the item's position.  
* int combinedOverlay: The packed overlay value (e.g., for damage effects).

The most important task when adapting the renderer is to properly utilize the ItemDisplayContext.

### **Subsection 4.1: Mastering Rendering Perspectives with ItemDisplayContext**

An item appears in many different places in the game world, and its appearance must be tailored for each one. A gun model that is perfectly positioned in first-person view will be incorrectly scaled and rotated when displayed in an inventory slot or dropped on the ground unless explicitly handled. The ItemDisplayContext enum is the mechanism for this control.

The most robust and readable way to manage these different perspectives is with a switch statement on the displayContext parameter within the renderByItem method. This structure ensures that each rendering context has its own dedicated set of transformations applied to the PoseStack. This pattern directly addresses common rendering issues where transforms from one context bleed into another, causing strange offsets.11

Below is an example implementation for GunItemRendererWrapper.java demonstrating this switch statement structure.

Java

// In GunItemRendererWrapper.java  
@Override  
public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {  
    // Before applying any context-specific transforms, save the current state of the PoseStack.  
    poseStack.pushPose();

    // Use a switch statement to handle each rendering perspective differently.  
    switch (context) {  
        case GUI:  
            // GUI: Flat in inventory. Often requires significant scaling and rotation.  
            // Example transforms:  
            // poseStack.translate(0.5, 0.5, 0.5);  
            // poseStack.mulPose(Axis.XP.rotationDegrees(30));  
            // poseStack.scale(0.6f, 0.6f, 0.6f);  
            break;

        case GROUND:  
            // GROUND: Dropped on the floor. May need slight scaling.  
            // Example transforms:  
            // poseStack.translate(0.5, 0.5, 0.5);  
            // poseStack.scale(0.5f, 0.5f, 0.5f);  
            break;

        case FIXED:  
            // FIXED: In an item frame. Often similar to GUI but may need different translation.  
            // Example transforms:  
            // poseStack.translate(0.5, 0.5, 0.0);  
            // poseStack.scale(0.5f, 0.5f, 0.5f);  
            break;

        case THIRD\_PERSON\_RIGHT\_HAND:  
        case THIRD\_PERSON\_LEFT\_HAND:  
            // THIRD\_PERSON: Held by a player in F5 mode or by another player.  
            // This usually requires translation to fit into the player model's hand correctly.  
            // Example transforms:  
            // poseStack.translate(0, 0.1, 0.1);  
            // poseStack.mulPose(Axis.YP.rotationDegrees(180));  
            break;

        case FIRST\_PERSON\_RIGHT\_HAND:  
        case FIRST\_PERSON\_LEFT\_HAND:  
            // FIRST\_PERSON: The viewmodel. This is often the most detailed transform,  
            // positioning the gun exactly where the player should see it.  
            // Example transforms:  
            // poseStack.translate(0.5, 0.5, 0.5);  
            // poseStack.mulPose(Axis.YP.rotationDegrees(-90));  
            break;  
              
        case HEAD:  
            // HEAD: When worn as a helmet. Unlikely for a gun, but should be handled.  
            break;

        case NONE:  
            // NONE: Fallback, typically no special transform is needed.  
            break;  
    }

    // After applying the correct transformations, render the actual model.  
    // This logic (getting the BakedModel, rendering its quads) remains the same as in 1.20.1.  
    // Minecraft.getInstance().getItemRenderer().render(stack, context, false, poseStack, buffer, light, overlay, model);

    // Restore the PoseStack to its original state to avoid affecting other rendering operations.  
    poseStack.popPose();  
}

#### **ItemDisplayContext Reference Table**

To facilitate development and reduce the need for trial-and-error, the following table provides a comprehensive reference for each ItemDisplayContext value, its corresponding key in model JSON files, and its usage in-game.12

| ItemDisplayContext Enum Value | JSON Key | Usage Description |
| :---- | :---- | :---- |
| THIRD\_PERSON\_RIGHT\_HAND | "thirdperson\_righthand" | Held in the right hand in third-person view (F5 mode or on other players). |
| THIRD\_PERSON\_LEFT\_HAND | "thirdperson\_lefthand" | Held in the left hand in third-person view. |
| FIRST\_PERSON\_RIGHT\_HAND | "firstperson\_righthand" | Held in the right hand in first-person view (the viewmodel). |
| FIRST\_PERSON\_LEFT\_HAND | "firstperson\_lefthand" | Held in the left hand in first-person view. |
| HEAD | "head" | Rendered in the head armor slot (e.g., via commands). |
| GUI | "gui" | Rendered flat in an inventory slot, creative tab, or the hotbar. |
| GROUND | "ground" | Rendered as a dropped item entity in the world. |
| FIXED | "fixed" | Rendered within an item frame. |
| NONE | "none" | A fallback context, not typically used in model files. |

By diligently implementing the switch statement and using this table as a guide, developers can ensure their custom items are rendered correctly and consistently across all possible game contexts.

## **Section 5: Re-integrating and Enhancing Custom Animations**

The IClientItemExtensions API provides superior, purpose-built hooks for custom animations, replacing older, more cumbersome event-based systems. These hooks are called at the precise moment in the render loop, with all necessary context provided, which greatly simplifies the implementation of complex visual effects like aiming, reloading, or custom melee swings.

This new architecture naturally decouples the rendering of the item model itself from the animation of the player's hands and arms holding it. The renderByItem method is concerned with *what* to draw (the gun model) and its static appearance in various contexts. The animation hooks, applyForgeHandTransform and getArmPose, are concerned with *where the player's hand is* and how it moves. This separation of concerns is a powerful architectural benefit. For example, a reloading animation involving a moving bolt on the gun model can be handled entirely within renderByItem (by changing which model parts are rendered based on a timer), while the overall motion of the gun being pulled away from the screen is handled in applyForgeHandTransform. If the gun model itself is rendered incorrectly, the issue lies in renderByItem. If the gun is positioned incorrectly during an animation, the issue is in the animation hooks. This clear division of responsibility, also seen in the design of libraries like GeckoLib with its "Perspective-Aware Animation Handling" 10, makes complex animations far easier to develop, debug, and maintain.

### **Subsection 5.1: First-Person Viewmodel Animations via applyForgeHandTransform**

The applyForgeHandTransform method is the dedicated hook for all first-person (viewmodel) animations. It gives complete control over the PoseStack before the item is rendered in the player's hand.4

The method's signature provides all the necessary context 6:

* PoseStack poseStack: The stack to apply transformations to.  
* LocalPlayer player: The client-side player entity.  
* HumanoidArm arm: The arm (RIGHT or LEFT) holding the item.  
* ItemStack itemInHand: The item stack being animated.  
* float partialTick: For smooth interpolation between game ticks.  
* float equipProcess: The progress of the "equip" animation (0.0 to 1.0).  
* float swingProcess: The progress of the "swing" animation (0.0 to 1.0).

A critical detail is the return value: returning true from this method signals to the rendering engine that all necessary transformations have been applied, and it should *skip* the default vanilla item-in-hand transforms. Returning false will cause the vanilla transforms to be applied *after* the custom ones, which is rarely the desired behavior for a fully custom animation.4

The following code, to be placed within the IClientItemExtensions implementation in ModernKineticGunItem.java, demonstrates a simple "aim down sights" (ADS) animation. It assumes the gun's aiming state is stored in a data component on the ItemStack.

Java

// In ModernKineticGunItem.java, inside the IClientItemExtensions anonymous class  
@Override  
public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {  
    // We only want to apply this to the main hand  
    if (player.getUsedItemHand()\!= (arm \== HumanoidArm.RIGHT? InteractionHand.MAIN\_HAND : InteractionHand.OFF\_HAND)) {  
        return false;  
    }

    // Check a custom data component to see if the player is aiming.  
    // boolean isAiming \= itemInHand.get(MyDataComponents.IS\_AIMING).orElse(false);  
    boolean isAiming \= player.isUsingItem(); // A simpler check for demonstration

    if (isAiming) {  
        // Apply transformations for the ADS pose.  
        // These values are examples and must be tuned for the specific gun model.  
        poseStack.translate(0.2, \-0.3, \-0.5); // Move the gun to the center  
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(5)); // Slight rotation  
          
        // Return true to prevent vanilla transforms from messing up our ADS pose.  
        return true;  
    }

    // If not aiming, return false to let the default "hip fire" pose be rendered.  
    // Or, you could apply your own custom hip-fire transforms and return true.  
    return false;  
}

### **Subsection 5.2: Third-Person Player Animations via getArmPose**

The getArmPose method controls the animation of the entire player model in third-person view by returning a HumanoidModel.ArmPose. This allows the gun to influence the player's stance, making them appear to be holding and aiming the weapon correctly to other players or in F5 mode.4

First, a custom ArmPose must be defined. This is typically done as a static final field. The IArmPoseTransformer lambda defines how the model's arms should be rotated.

Java

// In ModernKineticGunItem.java, or a shared class  
public static final HumanoidModel.ArmPose AIMING\_GUN\_POSE \= HumanoidModel.ArmPose.create(  
    "TACZ\_AIM\_GUN", // Unique name for the pose  
    false, // 'twoHanded' \= false, as we transform each arm individually  
    (model, entity, arm) \-\> {  
        // Apply custom rotations to the arm parts of the HumanoidModel  
        if (arm \== HumanoidArm.RIGHT) {  
            model.rightArm.xRot \= \-1.5f; // Point arm forward  
            model.rightArm.yRot \= \-0.1f;  
        } else { // LEFT arm  
            model.leftArm.xRot \= \-1.45f; // Point arm forward, slightly different for support  
            model.leftArm.yRot \= 0.1f;  
        }  
    }  
);

Then, the getArmPose method override in IClientItemExtensions will return this custom pose when appropriate.

Java

// In ModernKineticGunItem.java, inside the IClientItemExtensions anonymous class  
@Override  
public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {  
    // If the entity is actively "using" the gun (i.e., aiming)  
    if (entityLiving.isUsingItem() && entityLiving.getUseItem().equals(itemStack)) {  
        return AIMING\_GUN\_POSE; // Return our custom aiming pose  
    }  
      
    // Otherwise, return a default pose.  
    return HumanoidModel.ArmPose.EMPTY;  
}

With these implementations, the custom gun will now feature context-aware animations in both first- and third-person perspectives, fully integrated into the modern NeoForge rendering system.

## **Section 6: Finalizing the Port: Best Practices and Broader Context**

A successful port goes beyond just making the code compile. It involves adopting best practices for performance, being aware of other common API changes, and knowing how to debug the inevitable visual glitches that arise.

### **Performance Considerations: Caching Renderers**

It is critical to avoid creating new instances of the renderer within the getCustomRenderer method. This method can be called frequently, and instantiating a new object every time is a significant performance anti-pattern that will create unnecessary garbage collection pressure. The renderer instance should be created once and cached, either as a field within the anonymous IClientItemExtensions class (as shown in the Section 3 example) or as part of a shared static instance.

### **Checklist of Common API Changes (1.20.x \-\> 1.21.x)**

While porting the rendering system, developers will likely encounter other breaking changes between Forge 1.20.1 and NeoForge 1.21.1. Being aware of these will expedite the process:

* **ResourceLocation Instantiation:** The new ResourceLocation(...) constructor has been deprecated. It should be replaced with the static factory methods ResourceLocation.fromNamespaceAndPath(namespace, path) or ResourceLocation.parse(string).8 This change improves clarity and error handling.  
* **Data Components:** The primary system for storing data on ItemStacks has shifted from NBT tags to Data Components. This is a major architectural change. Any logic that previously read or wrote NBT to the item stack (e.g., for storing ammo count or aiming state) must be migrated to use the Data Component system.7  
* **VertexConsumer/BufferBuilder API:** For developers performing low-level, manual vertex rendering within their BEWLR, the VertexConsumer API has changed. Method names have been updated for consistency: vertex() is now addVertex(), color() is setColor(), uv() is setUv(), and endVertex() has been removed entirely.8  
* **Registry Access:** A minor but common change is the renaming of RegistryAccess.registry(...) to RegistryAccess.lookup(...).14

### **Debugging Common Visual Artifacts**

* **Problem:** The item is in the wrong position, has the wrong rotation, or is scaled incorrectly.  
  * **Solution:** This is almost always an issue with PoseStack transformations. Verify that you are applying the correct transforms within the correct case of your ItemDisplayContext switch statement. Remember that transforms are cumulative. Use poseStack.pushPose() and poseStack.popPose() to isolate transforms for each context.  
* **Problem:** The item clips through the player's hand or the world (Z-fighting).  
  * **Solution:** Adjust the translation of the model on the Z-axis within the PoseStack. A small adjustment can often resolve depth-fighting issues. Ensure you are using the appropriate RenderType from the MultiBufferSource that has the correct depth test settings.  
* **Problem:** The item is rendered as a solid black and purple checkerboard.  
  * **Solution:** This is the classic "missing texture" error. The ResourceLocation pointing to your model's texture is incorrect. Double-check the path and filename, ensure the asset is in the correct assets/\<modid\>/textures/ folder, and verify you are using ResourceLocation.fromNamespaceAndPath() correctly. This is a frequent issue noted in various modding communities.10  
* **Problem:** The first-person animation (e.g., ADS) is not playing or is being overridden by the default animation.  
  * **Solution:** Ensure that your applyForgeHandTransform method is returning true. If it returns false, the vanilla transformations will be applied after yours, likely resetting your custom pose.

### **Conclusion**

The migration from the legacy BEWLR registration to the IClientItemExtensions API in NeoForge 1.21.1 is a significant step forward for the Minecraft modding ecosystem. While it requires a conceptual adjustment, the new system offers far more power, flexibility, and better code organization. By centralizing all client-side behaviors into a single, cohesive interface, NeoForge provides a robust and extensible foundation for creating items with complex and dynamic visual presentations. By following the guidelines and patterns outlined in this report, developers can not only port their existing rendering systems but also leverage the new architecture to build more sophisticated and maintainable features for their mods.

#### **Referências citadas**

1. BlockEntityWithoutLevelRenderer \- Forge Documentation, acessado em junho 19, 2025, [https://docs.minecraftforge.net/en/latest/items/bewlr/](https://docs.minecraftforge.net/en/latest/items/bewlr/)  
2. BlockEntityRenderer | NeoForged docs, acessado em junho 19, 2025, [https://docs.neoforged.net/docs/1.21.1/blockentities/ber](https://docs.neoforged.net/docs/1.21.1/blockentities/ber)  
3. BlockEntityWithoutLevelRenderer | NeoForged docs, acessado em junho 19, 2025, [https://docs.neoforged.net/docs/1.20.6/items/bewlr/](https://docs.neoforged.net/docs/1.20.6/items/bewlr/)  
4. IClientItemExtensions (forge 1.19.3-44.1.8) \- nekoyue.github.io, acessado em junho 19, 2025, [https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.19.3/net/minecraftforge/client/extensions/common/IClientItemExtensions.html](https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.19.3/net/minecraftforge/client/extensions/common/IClientItemExtensions.html)  
5. Consumables | NeoForged docs, acessado em junho 19, 2025, [https://docs.neoforged.net/docs/items/consumables/](https://docs.neoforged.net/docs/items/consumables/)  
6. Custom Item Animations \- Forge Community Wiki \- Gemwire, acessado em junho 19, 2025, [https://forge.gemwire.uk/wiki/Custom\_Item\_Animations](https://forge.gemwire.uk/wiki/Custom_Item_Animations)  
7. NeoForge 21.5 for Minecraft 1.21.5, acessado em junho 19, 2025, [https://neoforged.net/news/21.5release/](https://neoforged.net/news/21.5release/)  
8. .github/primers/1.21/index.md at main · neoforged/.github · GitHub, acessado em junho 19, 2025, [https://github.com/neoforged/.github/blob/main/primers/1.21/index.md](https://github.com/neoforged/.github/blob/main/primers/1.21/index.md)  
9. Minecraft 1.20.5/6 \-\> 1.21 Mod Migration Primer · GitHub, acessado em junho 19, 2025, [https://gist.github.com/ChampionAsh5357/d895a7b1a34341e19c80870720f9880f](https://gist.github.com/ChampionAsh5357/d895a7b1a34341e19c80870720f9880f)  
10. Geckolib Items (Geckolib5) \- GitHub, acessado em junho 19, 2025, [https://github.com/bernie-g/geckolib/wiki/Geckolib-Items-(Geckolib5)](https://github.com/bernie-g/geckolib/wiki/Geckolib-Items-\(Geckolib5\))  
11. Help rendering items in custom SpecialModelRenderer NeoForge 21.4.137 \- Reddit, acessado em junho 19, 2025, [https://www.reddit.com/r/ModdedMinecraft/comments/1la4pxv/help\_rendering\_items\_in\_custom/](https://www.reddit.com/r/ModdedMinecraft/comments/1la4pxv/help_rendering_items_in_custom/)  
12. Baked Models | NeoForged docs, acessado em junho 19, 2025, [https://docs.neoforged.net/docs/1.21.1/resources/client/models/bakedmodel](https://docs.neoforged.net/docs/1.21.1/resources/client/models/bakedmodel)  
13. Geckolib Items (Geckolib4) \- GitHub, acessado em junho 19, 2025, [https://github.com/bernie-g/geckolib/wiki/Geckolib-Items-(Geckolib4)](https://github.com/bernie-g/geckolib/wiki/Geckolib-Items-\(Geckolib4\))  
14. NeoForge 21.2 for Minecraft 1.21.2, acessado em junho 19, 2025, [https://neoforged.net/news/21.2release/](https://neoforged.net/news/21.2release/)