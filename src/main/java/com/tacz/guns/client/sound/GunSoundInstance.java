package com.tacz.guns.client.sound;

import com.mojang.blaze3d.audio.SoundBuffer;
import com.tacz.guns.client.resource.ClientAssetsManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;

public class GunSoundInstance extends EntityBoundSoundInstance {
    public GunSoundInstance(SoundEvent soundEvent, SoundSource source, float volume, float pitch, Entity entity, int soundDistance) {
        super(soundEvent, source, volume, pitch, entity, 943);
        this.attenuation = Attenuation.NONE;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            this.volume = volume * (1.0F - Math.min(1.0F, (float) Math.sqrt(player.distanceToSqr(x, y, z)) / soundDistance));
            this.volume *= this.volume;
        }
    }

    public void setStop() {
        this.stop();
    }

    
}































































