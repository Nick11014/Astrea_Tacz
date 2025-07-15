package com.tacz.guns.client.model.functional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.model.BedrockAmmoModel;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.client.resource.pojo.display.gun.ShellEjection;
import com.tacz.guns.compat.oculus.OculusCompat;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.concurrent.ConcurrentLinkedDeque;

public class ShellRender implements IFunctionalRenderer {
    // ÃƒÂ¦Ã…Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â£Ã‚Â³ÃƒÂ©Ã‹Å“Ã…Â¸ÃƒÂ¥Ã‹â€ Ã¢â‚¬â€
    private final ConcurrentLinkedDeque<Data> SHELL_QUEUE = new ConcurrentLinkedDeque<>();
    public static boolean isSelf = false;

    private final BedrockGunModel bedrockGunModel;

    public ShellRender(BedrockGunModel bedrockGunModel) {
        this.bedrockGunModel = bedrockGunModel;
    }

    public void addShell(Vector3f randomVelocity) {
        if (SHELL_QUEUE.size() > 128) {
            SHELL_QUEUE.pollFirst();
        }
        double xRandom = Math.random() * randomVelocity.x();
        double yRandom = Math.random() * randomVelocity.y();
        double zRandom = Math.random() * randomVelocity.z();
        Vector3f vector3f = new Vector3f((float) xRandom, (float) yRandom, (float) zRandom);
        SHELL_QUEUE.offerLast(new Data(System.currentTimeMillis(), vector3f));
    }

    private void renderShell(ClientGunIndex display, GunData gunData, PoseStack poseStack, BedrockGunModel gunModel) {
        ShellEjection shellEjection = display.getShellEjection();
        if (shellEjection == null) {
            SHELL_QUEUE.clear();
            return;
        }
        TimelessAPI.getClientAmmoIndex(gunData.getAmmoId()).ifPresent(ammoIndex -> {
            BedrockAmmoModel model = ammoIndex.getShellModel();
            if (model == null) {
                return;
            }
            ResourceLocation location = ammoIndex.getShellTextureLocation();
            if (location == null) {
                return;
            }
            long lifeTime = (long) (shellEjection.getLivingTime() * 1000);

            // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¨Ã‚Â¸Ã‚Â¢ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¥Ã…Â½Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‹Å“Ã…Â¸ÃƒÂ¥Ã‹â€ Ã¢â‚¬â€
            checkShellQueue(lifeTime);

            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ§Ã‚Â§Ã‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œ
            Vector3f initialVelocity = shellEjection.getInitialVelocity();
            Vector3f acceleration = shellEjection.getAcceleration();
            Vector3f angularVelocity = shellEjection.getAngularVelocity();

            // ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ PoseStack
            for (Data data : SHELL_QUEUE) {
                if (data.normal == null && data.pose == null) {
                    data.normal = new Matrix3f(poseStack.last().normal());
                    data.pose = new Matrix4f(poseStack.last().pose());
                }
            }

            // ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¦Ã…Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â£Ã‚Â³
            gunModel.delegateRender((poseStack1, vertexConsumer1, transformType1, light, overlay) ->{
                SHELL_QUEUE.forEach(data -> renderSingleShell(transformType1, light, overlay, data, initialVelocity, acceleration, angularVelocity, model, location));
            });
        });
    }

    private void renderSingleShell(ItemDisplayContext transformType1, int light, int overlay, Data data, Vector3f initialVelocity, Vector3f acceleration, Vector3f angularVelocity, BedrockAmmoModel model, ResourceLocation location) {
        // ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡
        if (data.normal == null && data.pose == null) {
            return;
        }
        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬Ëœ
        PoseStack poseStack2 = new PoseStack();
        poseStack2.last().normal().mul(data.normal);
        poseStack2.last().pose().mul(data.pose);

        // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ§Ã¢â‚¬Â¢Ã¢â€žÂ¢ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ§Ã‚Â§Ã‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        long remindTime = System.currentTimeMillis() - data.timeStamp;
        double time = remindTime / 1000.0;
        Vector3f randomOffset = data.randomOffset;

        // ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â»Ã‚Â¡ÃƒÂ¨Ã‚Â¶Ã‚Â³ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã…â€™Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ§Ã‚ÂºÃ‚Â¿ÃƒÂ¨Ã‚Â¿Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â¨
        double x = (initialVelocity.x() + randomOffset.x()) * time + 0.5 * acceleration.x() * time * time;
        double y = (initialVelocity.y() + randomOffset.y()) * time + 0.5 * acceleration.y() * time * time;
        double z = (initialVelocity.z() + randomOffset.z()) * time + 0.5 * acceleration.z() * time * time;
        poseStack2.translate(-x, -y, z);

        // ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬
        double xw = time * angularVelocity.x();
        double yw = time * angularVelocity.y();
        double zw = time * angularVelocity.z();
        poseStack2.mulPose(Axis.XN.rotationDegrees((float) xw));
        poseStack2.mulPose(Axis.YN.rotationDegrees((float) yw));
        poseStack2.mulPose(Axis.ZP.rotationDegrees((float) zw));
        poseStack2.translate(0, -1.5, 0);

        model.render(poseStack2, transformType1, RenderType.entityCutout(location), light, overlay);
    }

    private void checkShellQueue(long lifeTime) {
        if (!SHELL_QUEUE.isEmpty()) {
            Data data = SHELL_QUEUE.peekFirst();
            if ((System.currentTimeMillis() - data.timeStamp) > lifeTime) {
                SHELL_QUEUE.pollFirst();
                checkShellQueue(lifeTime);
            }
        }
    }

    @Override
    public void render(PoseStack poseStack, VertexConsumer vertexBuffer, ItemDisplayContext transformType, int light, int overlay) {
        if (OculusCompat.isRenderShadow()) {
            return;
        }
        if (!isSelf) {
            return;
        }
        ItemStack currentGunItem = bedrockGunModel.getCurrentGunItem();
        IGun iGun = IGun.getIGunOrNull(currentGunItem);
        if (iGun == null) {
            return;
        }
        GunData gunData = TimelessAPI.getClientGunIndex(iGun.getGunId(currentGunItem)).map(ClientGunIndex::getGunData).orElse(null);
        if (gunData == null) {
            return;
        }
        TimelessAPI.getGunDisplay(currentGunItem).ifPresent(display -> {
            this.renderShell(display, gunData, poseStack, bedrockGunModel);
        });

    }

    public static class Data {
        public final long timeStamp;
        public final Vector3f randomOffset;

        public Matrix3f normal = null;
        public Matrix4f pose = null;

        public Data(long timeStamp, Vector3f randomOffset) {
            this.timeStamp = timeStamp;
            this.randomOffset = randomOffset;
        }
    }
}































































