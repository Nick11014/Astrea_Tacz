package com.tacz.guns.event;

import com.tacz.guns.init.ModAttributes;
import com.tacz.guns.init.ModDamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
// MigraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o NeoForge 1.21.1: LivingHurtEvent pode ter mudado de localizaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class EntityDamageEvent {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingHurt(LivingDamageEvent event){
        // TODO: MigraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o NeoForge 1.21.1 - API LivingDamageEvent mudou significativamente
        // As APIs getSource(), getAmount(), setAmount() precisam ser investigadas
        // ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o temporariamente desabilitada para manter compilaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o
        /*
        if (event.getSource().is(ModDamageTypes.BULLETS_TAG)) {
            LivingEntity living = event.getEntity();

            // MigraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o NeoForge 1.21.1: Attributes agora usam Holder system
            AttributeInstance resistance = living.getAttribute(ModAttributes.BULLET_RESISTANCE);
            if (resistance != null) {
                float modifiedDamage = event.getAmount() * (float) (1 - resistance.getValue());
                event.setAmount(modifiedDamage);
            }
        }
        */
    }
}































































