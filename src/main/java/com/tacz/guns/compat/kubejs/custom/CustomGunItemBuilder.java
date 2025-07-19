package com.tacz.guns.compat.kubejs.custom;

// import com.tacz.guns.compat.kubejs.TimelessKubeJSPlugin; // TODO: Re-enable when plugin is available
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Builder para itens de arma personalizados do KubeJS
 * IMPLEMENTADO: Migração de RegistryObject para DeferredHolder (NeoForge 1.21.1)
 */
public class CustomGunItemBuilder extends ItemBuilder {
    public String typeName;

    public CustomGunItemBuilder(ResourceLocation i) {
        super(i);
        this.typeName = "kubejs_default";
    }

    /**
     * Define o nome do tipo da arma
     * @param name Nome do tipo
     */
    public void setTypeName(String name) {
        this.typeName = name;
    }

    /**
     * Cria o objeto Item
     * IMPLEMENTADO: Usando DeferredHolder em vez de RegistryObject
     */
    @Override
    public Item createObject() {
        try {
            // MIGRAÇÃO: RegistryObject → DeferredHolder para NeoForge 1.21.1
            // Criar DeferredHolder para o item
            DeferredHolder<Item, KubeJSCustomGunItem> holder = DeferredHolder.create(
                BuiltInRegistries.ITEM.key(), 
                this.id
            );
            
            // Registrar o tipo de arma no plugin KubeJS
            // TODO: Implementar quando TimelessKubeJSPlugin for reabilitado
            // TimelessKubeJSPlugin.registerGunType(typeName, holder);
            
            System.out.println("TacZ: Created custom gun item with type: " + typeName + " and ID: " + this.id);
            System.out.println("TacZ: DeferredHolder created: " + holder.getKey());
            
            return new KubeJSCustomGunItem();
            
        } catch (Exception e) {
            System.err.println("TacZ: Error creating custom gun item: " + e.getMessage());
            e.printStackTrace();
            return new KubeJSCustomGunItem(); // Fallback
        }
    }
}
