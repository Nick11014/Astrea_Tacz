package com.tacz.guns.network;

import net.minecraft.network.FriendlyByteBuf;
// TODO: Migrar para sistema de Payloads do NeoForge 1.21.1
// import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface IMessage<T> {
    void encode(T message, FriendlyByteBuf buffer);

    T decode(FriendlyByteBuf buffer);

    void handle(T message, Supplier<NetworkEvent.Context> supplier);
}
