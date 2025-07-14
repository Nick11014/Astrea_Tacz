package com.tacz.guns.client.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.AnimationListener;
import com.tacz.guns.api.client.animation.ObjectAnimationChannel;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.client.model.bedrock.BedrockPart;
import com.tacz.guns.client.model.bedrock.ModelRendererWrapper;
import com.tacz.guns.client.model.functional.*;
import com.tacz.guns.client.model.listener.model.ModelAdditionalMagazineListener;
// import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.client.resource.pojo.display.gun.TextShow;
import com.tacz.guns.client.resource.pojo.model.BedrockModelPOJO;
import com.tacz.guns.client.resource.pojo.model.BedrockVersion;
import com.tacz.guns.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

import static com.tacz.guns.client.model.GunModelConstant.*;

public class BedrockGunModel extends BedrockAnimatedModel {
    protected final EnumMap<AttachmentType, List<BedrockPart>> refitAttachmentViewPath = Maps.newEnumMap(AttachmentType.class);
    private final EnumMap<AttachmentType, ItemStack> currentAttachmentItem = Maps.newEnumMap(AttachmentType.class);
    private final Set<String> adapterToRender = Sets.newHashSet();
    // private final ArrayList<ShellRender> shellRenderList = new ArrayList<>(); // TODO: Habilitar quando ShellRender estiver disponÃƒÆ’Ã‚Â­vel

    // ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> ironSightPath;
    // ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°idleÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> idleSightPath;
    // ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> thirdPersonHandOriginPath;
    // ÃƒÂ¥Ã‚Â±Ã¢â‚¬Â¢ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> fixedOriginPath;
    // ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ©Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> groundOriginPath;
    // ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â¿ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¥Ã…Â Ã‚Â©ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
    protected @Nullable List<BedrockPart> scopePosPath;
    // ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ§Ã¢â‚¬Å¾Ã‚Â°ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> muzzleFlashPosPath;
    // ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾
    protected @Nullable BedrockPart root;
    // ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾
    protected @Nullable BedrockPart magazineNode;
    // ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚ÂºÃ…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾
    protected @Nullable BedrockPart additionalMagazineNode;
    protected @Nullable List<BedrockPart> laserBeamPaths;

    private boolean renderHand = true;
    private ItemStack currentGunItem;
    private int currentExtendMagLevel = 0;

    public BedrockGunModel(BedrockModelPOJO pojo, BedrockVersion version) {
        super(pojo, version);

        this.magazineNode = Optional.ofNullable(modelMap.get(MAG_NORMAL_NODE)).map(ModelRendererWrapper::getModelRenderer).orElse(null);
        this.additionalMagazineNode = Optional.ofNullable(modelMap.get(MAG_ADDITIONAL_NODE)).map(ModelRendererWrapper::getModelRenderer).orElse(null);

        // ÃƒÂ¥Ã‚Â·Ã‚Â¦ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¨Ã¢â‚¬Â¡Ã¢â‚¬Å¡
        // this.setFunctionalRenderer(LEFTHAND_POS_NODE, bedrockPart -> new LeftHandRender(this)); // TODO: Habilitar quando LeftHandRender estiver funcionando
        // ÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¨Ã¢â‚¬Â¡Ã¢â‚¬Å¡
        // this.setFunctionalRenderer(RIGHTHAND_POS_NODE, bedrockPart -> new RightHandRender(this)); // TODO: Habilitar quando RightHandRender estiver funcionando
        // ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ§Ã¢â‚¬Å¾Ã‚Â°
        // this.setFunctionalRenderer(MUZZLE_FLASH_ORIGIN_NODE, bedrockPart -> new MuzzleFlashRender(this)); // TODO: Habilitar quando MuzzleFlashRender estiver disponÃƒÆ’Ã‚Â­vel
        // ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ©Ã¢â‚¬â€Ã‚Â­ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°
        this.setFunctionalRenderer(BULLET_IN_BARREL, bedrockPart -> ammoHiddenRender(bedrockPart, iGun -> iGun.hasBulletInBarrel(currentGunItem)));
        // ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
        this.setFunctionalRenderer(BULLET_IN_MAG, bedrockPart -> ammoHiddenRender(bedrockPart, iGun -> iGun.getCurrentAmmoCount(currentGunItem) > 0));
        // ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ©Ã¢â‚¬Å“Ã‚Â¾
        this.setFunctionalRenderer(BULLET_CHAIN, bedrockPart -> ammoHiddenRender(bedrockPart, iGun -> iGun.getCurrentAmmoCount(currentGunItem) > 0));
        // ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¯Ã‚Â¼ÃƒÂ¨Ã‚Â½Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ AKM ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¯Ã‚Â¼ÃƒÂ¨Ã‚Â½Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
        this.setFunctionalRenderer(MOUNT, bedrockPart -> scopeHiddenRender(bedrockPart, scopeItem -> scopeItem != null && !scopeItem.isEmpty()));
        // ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã‚Â§Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ M4 ÃƒÂ¤Ã‚Â¸Ã…Â 
        this.setFunctionalRenderer(CARRY, bedrockPart -> scopeHiddenRender(bedrockPart, scopeItem -> scopeItem == null || scopeItem.isEmpty()));
        // ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â Ã‹Å“ÃƒÂ¥Ã‚ÂÃ‚Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·
        this.setFunctionalRenderer(SIGHT_FOLDED, bedrockPart -> scopeHiddenRender(bedrockPart, scopeItem -> scopeItem != null && !scopeItem.isEmpty()));
        // ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã‚Â§Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·
        this.setFunctionalRenderer(SIGHT, bedrockPart -> scopeHiddenRender(bedrockPart, scopeItem -> scopeItem == null || scopeItem.isEmpty()));
        // ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚Âº
        this.setFunctionalRenderer(MAG_EXTENDED_1, bedrockPart -> extendedMagHiddenRender(bedrockPart, 1));
        // ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¤Ã‚ÂºÃ…â€™ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚Âº
        this.setFunctionalRenderer(MAG_EXTENDED_2, bedrockPart -> extendedMagHiddenRender(bedrockPart, 2));
        // ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚Âº
        this.setFunctionalRenderer(MAG_EXTENDED_3, bedrockPart -> extendedMagHiddenRender(bedrockPart, 3));
        // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚Âº
        this.setFunctionalRenderer(MAG_STANDARD, bedrockPart -> extendedMagHiddenRender(bedrockPart, 0));
        // ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¤Ã‚Â¸Ã‚Â¤ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ§Ã‚Â Ã‚Â
        this.setFunctionalRenderer(MAG_ADDITIONAL_NODE, this::renderAdditionalMagazine);
        // ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¦Ã…Â Ã‚Â¤ÃƒÂ¦Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
        this.setFunctionalRenderer(HANDGUARD_DEFAULT_NODE, this::handguardDefaultRender);
        // ÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã…â€œÃ‚Â¯ÃƒÂ¦Ã…Â Ã‚Â¤ÃƒÂ¦Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
        this.setFunctionalRenderer(HANDGUARD_TACTICAL_NODE, this::handguardTacticalRender);
        // ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾
        this.cacheOtherPath();
        // ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ UI ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¨Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾
        this.cacheRefitAttachmentViewPath();
        // ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¦Ã…Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â£Ã‚Â³ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€
        // this.cacheShellOriginNodes(); // TODO: Habilitar quando ShellRender estiver disponÃƒÆ’Ã‚Â­vel
        // ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
        this.allAttachmentRender();
        // ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
        this.setFunctionalRenderer(ATTACHMENT_ADAPTER_NODE, this::attachmentAdapterNodeRender);
    }

    private void cacheOtherPath() {
        ironSightPath = getPath(modelMap.get(IRON_VIEW_NODE));
        idleSightPath = getPath(modelMap.get(IDLE_VIEW_NODE));
        thirdPersonHandOriginPath = getPath(modelMap.get(THIRD_PERSON_HAND_ORIGIN_NODE));
        fixedOriginPath = getPath(modelMap.get(FIXED_ORIGIN_NODE));
        groundOriginPath = getPath(modelMap.get(GROUND_ORIGIN_NODE));
        muzzleFlashPosPath = getPath(modelMap.get(MUZZLE_FLASH_ORIGIN_NODE));
        scopePosPath = getPath(modelMap.get(AttachmentType.SCOPE.name().toLowerCase() + ATTACHMENT_POS_SUFFIX));
        laserBeamPaths = getPath(modelMap.get("laser_beam"));
        root = Optional.ofNullable(modelMap.get(ROOT_NODE)).map(ModelRendererWrapper::getModelRenderer).orElse(null);
    }

    private void cacheRefitAttachmentViewPath() {
        for (AttachmentType type : AttachmentType.values()) {
            if (type == AttachmentType.NONE) {
                refitAttachmentViewPath.put(type, getPath(modelMap.get(REFIT_VIEW_NODE)));
                continue;
            }
            String nodeName = REFIT_VIEW_PREFIX + type.name().toLowerCase() + REFIT_VIEW_SUFFIX;
            refitAttachmentViewPath.put(type, getPath(modelMap.get(nodeName)));
        }
    }

    // TODO: Habilitar quando ShellRender estiver disponÃƒÆ’Ã‚Â­vel
    // private void cacheShellOriginNodes() {
    //     ModelRendererWrapper rendererWrapper = modelMap.get(SHELL_ORIGIN_NODE);
    //     int i = 1;
    //     while (rendererWrapper != null) {
    //         ShellRender shellRender = new ShellRender(this);
    //         this.setFunctionalRenderer(rendererWrapper.getModelRenderer().name, bedrockPart -> shellRender);
    //         shellRenderList.add(shellRender);
    //         rendererWrapper = modelMap.get(SHELL_ORIGIN_NODE_PREFIX + i);
    //         i++;
    //     }
    // }

    @Nullable
    private IFunctionalRenderer attachmentAdapterNodeRender(BedrockPart bedrockPart) {
        for (BedrockPart child : bedrockPart.children) {
            if (child.name == null) {
                child.visible = false;
                continue;
            }
            child.visible = adapterToRender.contains(child.name);
        }
        return null;
    }

    private void allAttachmentRender() {
        for (AttachmentType type : AttachmentType.values()) {
            // ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¥Ã¢â‚¬Â°Ã‚Â
            if (type == AttachmentType.NONE || type == AttachmentType.SCOPE) {
                continue;
            }
            String positionNodeName = type.name().toLowerCase() + ATTACHMENT_POS_SUFFIX;
            String defaultNodeName = type.name().toLowerCase() + DEFAULT_ATTACHMENT_SUFFIX;
            this.setFunctionalRenderer(positionNodeName, bedrockPart -> {
                bedrockPart.visible = false;
                return new AttachmentRender(this, type); // HABILITADO: AttachmentRender agora estÃƒÆ’Ã‚Â¡ funcional
            });
            this.setFunctionalRenderer(defaultNodeName, bedrockPart -> {
                ItemStack attachmentItem = currentAttachmentItem.get(type);
                if (type == AttachmentType.MUZZLE && checkShowMuzzle(bedrockPart, attachmentItem)) {
                    return null;
                }
                bedrockPart.visible = attachmentItem == null || attachmentItem.isEmpty();
                return null;
            });
        }
    }

    private static boolean checkShowMuzzle(BedrockPart bedrockPart, ItemStack attachmentItem) {
        IAttachment iAttachment = IAttachment.getIAttachmentOrNull(attachmentItem);
        if (iAttachment != null) {
            ResourceLocation attachmentId = iAttachment.getAttachmentId(attachmentItem);
            var attachmentIndex = TimelessAPI.getClientAttachmentIndex(attachmentId);
            if (attachmentIndex.isPresent()) {
                // bedrockPart.visible = attachmentIndex.get().isShowMuzzle(); // TODO: Implementar quando ClientAttachmentIndex tiver mÃƒÆ’Ã‚Â©todo isShowMuzzle()
                bedrockPart.visible = true; // ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
                return true;
            }
        }
        return false;
    }

    @Nullable
    private IFunctionalRenderer handguardTacticalRender(BedrockPart bedrockPart) {
        ItemStack laserItem = currentAttachmentItem.get(AttachmentType.LASER);
        ItemStack gripItem = currentAttachmentItem.get(AttachmentType.GRIP);
        bedrockPart.visible = !laserItem.isEmpty() || !gripItem.isEmpty();
        return null;
    }

    @Nullable
    private IFunctionalRenderer handguardDefaultRender(BedrockPart bedrockPart) {
        ItemStack laserItem = currentAttachmentItem.get(AttachmentType.LASER);
        ItemStack gripItem = currentAttachmentItem.get(AttachmentType.GRIP);
        bedrockPart.visible = laserItem.isEmpty() && gripItem.isEmpty();
        return null;
    }

    @NotNull
    private IFunctionalRenderer renderAdditionalMagazine(BedrockPart bedrockPart) {
        return (poseStack, vertexBuffer, transformType, light, overlay) -> {
            // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - renderizaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de magazine adicional desabilitada temporariamente
            // Requer cast de Object para tipos especÃƒÆ’Ã‚Â­ficos
            /*
            if (bedrockPart.visible) {
                bedrockPart.compile(poseStack.last(), vertexBuffer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                for (BedrockPart part : bedrockPart.children) {
                    part.render(poseStack, transformType, vertexBuffer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                }
                if (magazineNode != null && magazineNode.visible) {
                    magazineNode.compile(poseStack.last(), vertexBuffer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    for (BedrockPart part : magazineNode.children) {
                        part.render(poseStack, transformType, vertexBuffer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            }
            */
        };
    }

    /**
     * ÃƒÂ¦Ã‚Â·Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚Âº
     */
    public void setTextShowList(Map<String, TextShow> textShowList) {
        // TODO: Restaurar quando TextShowRender tiver implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o completa
        // Por enquanto, registra os text shows mas nÃƒÆ’Ã‚Â£o os renderiza
        if (textShowList != null) {
            textShowList.forEach((name, textShow) -> {
                // Registrar o text show para uso futuro
                // this.setFunctionalRenderer(name, bedrockPart -> new TextShowRender(this, textShow, currentGunItem));
                
                // Log temporÃƒÆ’Ã‚Â¡rio para debugging
                // System.out.println("Registering text show: " + name + " -> " + textShow.getTextKey());
            });
        }
    }

    public void render(PoseStack matrixStack, ItemStack gunItem, ItemDisplayContext transformType, RenderType renderType, int light, int overlay) {
        IGun iGun = IGun.getIGunOrNull(gunItem);
        if (iGun == null) {
            return;
        }
        currentGunItem = gunItem;
        currentExtendMagLevel = 0;
        adapterToRender.clear();
        // ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¤Ã‚Â¾Ã¢â‚¬ÂºÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨
        for (AttachmentType type : AttachmentType.values()) {
            if (type == AttachmentType.NONE) {
                continue;
            }
            ItemStack attachmentItem = iGun.getAttachment(gunItem, type);
            if (attachmentItem.isEmpty()) {
                attachmentItem = iGun.getBuiltinAttachment(gunItem, type);
            }
            currentAttachmentItem.put(type, attachmentItem);
            IAttachment attachment = IAttachment.getIAttachmentOrNull(attachmentItem);
            if (attachment != null) {
                TimelessAPI.getClientAttachmentIndex(attachment.getAttachmentId(attachmentItem)).ifPresent(index -> {
                    // TODO: Implementar quando ClientAttachmentIndex estiver completo
                    // ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã‚ÂÃ…Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡
                    // if (type == AttachmentType.EXTENDED_MAG) {
                    //     currentExtendMagLevel = index.getData().getExtendedMagLevel();
                    // }
                    // ÃƒÂ¦Ã‚Â·Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ‚Â£
                    // if (index.getAdapterNodeName() != null) {
                    //     adapterToRender.add(index.getAdapterNodeName());
                    // }
                });
            }
        }
        if (laserBeamPaths != null) {
            // BeamRenderer.renderLaserBeam(gunItem, matrixStack, transformType, laserBeamPaths); // TODO: Implementar quando BeamRenderer estiver completo
        }
        // ÃƒÂ©Ã¢â‚¬Â¢Ã…â€œÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¿ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
        ItemStack attachmentItem = currentAttachmentItem.get(AttachmentType.SCOPE);
        IAttachment iAttachment = IAttachment.getIAttachmentOrNull(attachmentItem);
        if (scopePosPath != null && attachmentItem != null && !attachmentItem.isEmpty()) {
            matrixStack.pushPose();
            for (BedrockPart bedrockPart : scopePosPath) {
                bedrockPart.translateAndRotateAndScale(matrixStack);
            }
            // AttachmentRender.renderAttachment(attachmentItem, currentGunItem, matrixStack, transformType, light, overlay); // HABILITADO: Funcionalidade restaurada gradualmente
            matrixStack.popPose();
            // ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¿ÃƒÂ¦Ã‚ÂµÃ¢â‚¬Â¹ÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ©Ã¢â‚¬Â¢Ã…â€œÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“
            if (iAttachment != null) {
                // TODO: Implementar quando ClientAttachmentIndex estiver completo
                // Optional<ClientAttachmentIndex> attachmentIndex = TimelessAPI.getClientAttachmentIndex(iAttachment.getAttachmentId(attachmentItem));
                // attachmentIndex.ifPresent(index -> {
                //     if (index.isScope() && index.isSight()) { // ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‹â€ ÃƒÂ©Ã¢â‚¬Â¢Ã…â€œ
                //         RenderHelper.enableItemEntityStencilTest();
                //         RenderSystem.stencilFunc(GL11.GL_GREATER, 127, 0xFF);
                //     } else if (index.isScope()) { // ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿ÃƒÂ§Ã‚Â­Ã¢â‚¬â„¢ÃƒÂ©Ã¢â‚¬Â¢Ã…â€œ
                //         RenderHelper.enableItemEntityStencilTest();
                //         RenderSystem.stencilFunc(GL11.GL_EQUAL, 0, 0xFF);
                //     }
                // });
            }
        }
        RenderSystem.stencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
        super.render(matrixStack, transformType, renderType, light, overlay);
        RenderHelper.disableItemEntityStencilTest();
        RenderSystem.clearStencil(0);
        RenderSystem.clear(GL11.GL_STENCIL_BUFFER_BIT, Minecraft.ON_OSX);
    }

    @Nullable
    private IFunctionalRenderer ammoHiddenRender(BedrockPart bedrockPart, Predicate<IGun> predicate) {
        IGun iGun = IGun.getIGunOrNull(currentGunItem);
        if (iGun != null) {
            bedrockPart.visible = predicate.test(iGun);
        }
        return null;
    }

    @Nullable
    private IFunctionalRenderer scopeHiddenRender(BedrockPart bedrockPart, Predicate<ItemStack> predicate) {
        // ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã‚Â§Ã‚Â
        ItemStack scopeItem = currentAttachmentItem.get(AttachmentType.SCOPE);
        bedrockPart.visible = predicate.test(scopeItem);
        return null;
    }

    @Nullable
    private IFunctionalRenderer extendedMagHiddenRender(BedrockPart bedrockPart, int level) {
        bedrockPart.visible = currentExtendMagLevel == level;
        return null;
    }

    @Override
    public AnimationListener supplyListeners(String nodeName, ObjectAnimationChannel.ChannelType type) {
        AnimationListener listener = super.supplyListeners(nodeName, type);
        if (listener == null) {
            return null;
        }
        if (nodeName.equals(MAG_ADDITIONAL_NODE)) {
            // ÃƒÂ©Ã‚Â¢Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬â€œÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â®Ã†â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¥Ã‚Â¸Ã‚Â§ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÂ¦Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
            return new ModelAdditionalMagazineListener(listener, this);
        }
        return listener;
    }

    @Override
    public void cleanAnimationTransform() {
        super.cleanAnimationTransform();
        if (additionalMagazineNode != null) {
            additionalMagazineNode.visible = false;
        }
    }

    public EnumMap<AttachmentType, ItemStack> getCurrentAttachmentItem() {
        return currentAttachmentItem;
    }

    public ItemStack getCurrentGunItem() {
        return currentGunItem;
    }

    @Nullable
    public BedrockPart getAdditionalMagazineNode() {
        return additionalMagazineNode;
    }

    @Nullable
    public List<BedrockPart> getIronSightPath() {
        return ironSightPath;
    }

    @Nullable
    public List<BedrockPart> getIdleSightPath() {
        return idleSightPath;
    }

    @Nullable
    public List<BedrockPart> getThirdPersonHandOriginPath() {
        return thirdPersonHandOriginPath;
    }

    @Nullable
    public List<BedrockPart> getFixedOriginPath() {
        return fixedOriginPath;
    }

    @Nullable
    public List<BedrockPart> getGroundOriginPath() {
        return groundOriginPath;
    }

    @Nullable
    public List<BedrockPart> getMuzzleFlashPosPath() {
        return muzzleFlashPosPath;
    }

    @Nullable
    public List<BedrockPart> getScopePosPath() {
        return scopePosPath;
    }

    @Nullable
    public List<BedrockPart> getRefitAttachmentViewPath(AttachmentType type) {
        return refitAttachmentViewPath.get(type);
    }

    // TODO: Habilitar quando ShellRender estiver disponÃƒÆ’Ã‚Â­vel
    // @Nullable
    // public ShellRender getShellRender(int index) {
    //     if (index < 0 || index >= shellRenderList.size()) {
    //         return null;
    //     }
    //     return shellRenderList.get(index);
    // }

    @Nullable
    public BedrockPart getRootNode() {
        return root;
    }

    public boolean getRenderHand() {
        return renderHand;
    }

    public void setRenderHand(boolean renderHand) {
        this.renderHand = renderHand;
    }
}































































