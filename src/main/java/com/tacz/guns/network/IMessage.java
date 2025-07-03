package com.tacz.guns.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Interface base para mensagens de rede no NeoForge 1.21.1
 * Utiliza o novo sistema de CustomPacketPayload
 */
public interface IMessage extends CustomPacketPayload {
    
    /**
     * Manipula a mensagem quando recebida
     * @param context Contexto do payload contendo informações do remetente/receptor
     */
    void handle(IPayloadContext context);
    
    /**
     * Retorna o StreamCodec para serialização/deserialização da mensagem
     */
    StreamCodec<ByteBuf, ? extends IMessage> getStreamCodec();
}
