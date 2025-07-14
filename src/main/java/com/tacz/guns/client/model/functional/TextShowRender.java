package com.tacz.guns.client.model.functional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.model.bedrock.BedrockModel;
import com.tacz.guns.client.model.papi.PapiManager;
import com.tacz.guns.client.resource.pojo.display.gun.TextShow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class TextShowRender implements IFunctionalRenderer {
    private final BedrockModel bedrockModel;
    private final TextShow textShow;
    private final ItemStack gunStack;

    public TextShowRender(BedrockModel bedrockModel, TextShow textShow, ItemStack gunStack) {
        this.bedrockModel = bedrockModel;
        this.textShow = textShow;
        this.gunStack = gunStack;
    }

    @Override
    public void render(PoseStack poseStack, VertexConsumer vertexBuffer, ItemDisplayContext transformType, int light, int overlay) {
        // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - renderizaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de texto desabilitada temporariamente
        // Requer resoluÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de imports: ItemDisplayContext, PoseStack, VertexConsumer, Minecraft, etc.
        
        /*
        // Cast para tipos especÃƒÆ’Ã‚Â­ficos quando necessÃƒÆ’Ã‚Â¡rio  
        // TODO: Remover casts quando interface voltar a usar tipos especÃƒÆ’Ã‚Â­ficos
        if (!(transformType instanceof ItemDisplayContext)) {
            return;
        }
        ItemDisplayContext displayContext = (ItemDisplayContext) transformType;
        if (!displayContext.firstPerson()) {
            return;
        }
        String text = PapiManager.getTextShow(textShow.getTextKey(), gunStack);
        if (StringUtils.isBlank(text)) {
            return;
        }
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
        Matrix3f normal = new Matrix3f(poseStack.last().normal());
        Matrix4f pose = new Matrix4f(poseStack.last().pose());

        // Resto da implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o comentada temporariamente...
        */
    }
    
    /*
    // Resto da implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o comentada temporariamente - cÃƒÆ’Ã‚Â³digo ÃƒÆ’Ã‚Â³rfÃƒÆ’Ã‚Â£o removido
    // TODO: Restaurar quando imports estiverem funcionando
    */
}































































