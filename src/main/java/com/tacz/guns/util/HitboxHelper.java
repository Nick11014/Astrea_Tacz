package com.tacz.guns.util;

import com.tacz.guns.api.entity.ITargetEntity;
import com.tacz.guns.config.common.OtherConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedList;
import java.util.WeakHashMap;

public final class HitboxHelper {
    // ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¨Ã‚Â¡Ã‚Â¨
    private static final WeakHashMap<Player, LinkedList<Vec3>> PLAYER_POSITION = new WeakHashMap<>();
    // ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã‚Â®Ã‚Â±ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¨Ã‚Â¡Ã‚Â¨
    private static final WeakHashMap<Player, LinkedList<AABB>> PLAYER_HITBOXES = new WeakHashMap<>();
    // ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¨Ã‚Â¡Ã‚Â¨
    private static final WeakHashMap<Player, LinkedList<Vec3>> PLAYER_VELOCITY = new WeakHashMap<>();
    // ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã‚Â®Ã‚Â±ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ Tick ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ©Ã¢â€žÂ¢Ã‚Â
    private static final int SAVE_TICK = Mth.floor(OtherConfig.SERVER_HITBOX_LATENCY_MAX_SAVE_MS.get() / 1000 * 20 + 0.5);

    public static void onPlayerTick(Player player) {
        if (player.isSpectator()) {
            PLAYER_POSITION.remove(player);
            PLAYER_HITBOXES.remove(player);
            PLAYER_VELOCITY.remove(player);
            return;
        }
        LinkedList<Vec3> positions = PLAYER_POSITION.computeIfAbsent(player, p -> new LinkedList<>());
        LinkedList<AABB> boxes = PLAYER_HITBOXES.computeIfAbsent(player, p -> new LinkedList<>());
        LinkedList<Vec3> velocities = PLAYER_VELOCITY.computeIfAbsent(player, p -> new LinkedList<>());
        positions.addFirst(player.position());
        boxes.addFirst(player.getBoundingBox());
        velocities.addFirst(getPlayerVelocity(player));
        // Position ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ 2 ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
        if (positions.size() > 2) {
            positions.removeLast();
        }
        // ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã‚Â®Ã‚Â±ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶
        if (boxes.size() > SAVE_TICK) {
            boxes.removeLast();
            velocities.removeLast();
        }
    }

    public static void onPlayerLoggedOut(Player player) {
        PLAYER_POSITION.remove(player);
        PLAYER_HITBOXES.remove(player);
        PLAYER_VELOCITY.remove(player);
    }

    public static Vec3 getPlayerVelocity(Player entity) {
        LinkedList<Vec3> positions = PLAYER_POSITION.computeIfAbsent(entity, player -> new LinkedList<>());
        if (positions.size() > 1) {
            Vec3 currPos = positions.getFirst();
            Vec3 prevPos = positions.getLast();
            return new Vec3(currPos.x - prevPos.x, currPos.y - prevPos.y, currPos.z - prevPos.z);
        }
        return new Vec3(0, 0, 0);
    }

    public static AABB getBoundingBox(Player entity, int ping) {
        if (PLAYER_HITBOXES.containsKey(entity)) {
            LinkedList<AABB> boxes = PLAYER_HITBOXES.get(entity);
            int index = Mth.clamp(ping, 0, boxes.size() - 1);
            return boxes.get(index);
        }
        return entity.getBoundingBox();
    }

    public static Vec3 getVelocity(Player entity, int ping) {
        if (PLAYER_VELOCITY.containsKey(entity)) {
            LinkedList<Vec3> velocities = PLAYER_VELOCITY.get(entity);
            int index = Mth.clamp(ping, 0, velocities.size() - 1);
            return velocities.get(index);
        }
        return getPlayerVelocity(entity);
    }

    public static AABB getFixedBoundingBox(Entity entity, Entity owner) {
        AABB boundingBox = entity.getBoundingBox();
        Vec3 velocity = new Vec3(entity.getX() - entity.xOld, entity.getY() - entity.yOld, entity.getZ() - entity.zOld);
        // hitbox ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸ÃƒÂ¨Ã‚Â¡Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ‚Â¿ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¹Ã…Â¸ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â°Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸ÃƒÂ¨Ã‚Â¡Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ‚Â¿ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€
        if (OtherConfig.SERVER_HITBOX_LATENCY_FIX.get() && entity instanceof ServerPlayer player && owner instanceof ServerPlayer serverPlayerOwner) {
            // Migrado para NeoForge 1.21.1: serverPlayerOwner.latency -> serverPlayerOwner.connection.latency()
            int ping = Mth.floor((serverPlayerOwner.connection.latency() / 1000.0) * 20.0 + 0.5);
            boundingBox = getBoundingBox(player, ping);
            velocity = getVelocity(player, ping);
        }
        // ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¨Ã‚Â¹Ã‚Â²ÃƒÂ¤Ã‚Â¼Ã‚ÂÃƒÂ¥Ã‚Â¯Ã‚Â¼ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ hitbox ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¥Ã‚Â½Ã‚Â¢
        double expandHeight = entity instanceof Player && !entity.isCrouching() ? 0.0625 : 0.0;
        boundingBox = boundingBox.expandTowards(0, expandHeight, 0);
        // ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â±Ã¢â‚¬Â¢ hitbox
        boundingBox = boundingBox.expandTowards(velocity.x, velocity.y, velocity.z);
        // ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ hitbox ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ Config ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´
        double playerHitboxOffset = OtherConfig.SERVER_HITBOX_OFFSET.get();
        if (entity instanceof ServerPlayer) {
            if (entity.getVehicle() != null) {
                boundingBox = boundingBox.move(velocity.multiply(playerHitboxOffset / 2, playerHitboxOffset / 2, playerHitboxOffset / 2));
            }
            boundingBox = boundingBox.move(velocity.multiply(playerHitboxOffset, playerHitboxOffset, playerHitboxOffset));
        }
        // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ Hitbox ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ©Ã‚ÂªÃ…â€™ÃƒÂ¥Ã‚Â¾Ã¢â‚¬â€ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
        if (entity.getVehicle() != null || entity instanceof ITargetEntity) {
            boundingBox = boundingBox.move(velocity.multiply(-2.5, -2.5, -2.5));
        }
        boundingBox = boundingBox.move(velocity.multiply(-5, -5, -5));
        return boundingBox;
    }
}































































