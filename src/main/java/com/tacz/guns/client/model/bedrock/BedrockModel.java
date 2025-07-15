package com.tacz.guns.client.model.bedrock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.resource.pojo.model.*;
import com.tacz.guns.compat.oculus.OculusCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class BedrockModel {
    public static BedrockModel dummyModel = new BedrockModel();
    /**
     * ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã¢â‚¬Å¡Ã‚Â¨ ModelRender ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ HashMap
     */
    protected final HashMap<String, ModelRendererWrapper> modelMap = new HashMap<>();
    /**
     * ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã¢â‚¬Å¡Ã‚Â¨ Bones ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ HashMapÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ©Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¯Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â¾ÃƒÂ§Ã‹â€ Ã‚Â¶ÃƒÂ©Ã‚ÂªÃ‚Â¨ÃƒÂ©Ã‚ÂªÃ‚Â¼ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
     */
    protected final HashMap<String, BonesItem> indexBones = new HashMap<>();
    /**
     * ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂªÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ§Ã‹â€ Ã‚Â¶ÃƒÂ©Ã‚ÂªÃ‚Â¨ÃƒÂ©Ã‚ÂªÃ‚Â¼ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ©Ã‚ÂªÃ‚Â¨ÃƒÂ©Ã‚ÂªÃ‚Â¼ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
     */
    protected final List<BedrockPart> shouldRender = new LinkedList<>();
    /**
     * ÃƒÂ¥Ã‚Â§Ã¢â‚¬ÂÃƒÂ¦Ã¢â‚¬Â°Ã‹Å“ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¨Ã¢â‚¬Â¡Ã¢â‚¬Å¡
     */
    protected List<IFunctionalRenderer> delegateRenderers = new ArrayList<>();
    /**
     * ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â¿Ã†â€™ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹
     */
    protected @Nullable Vec3 offset = null;
    /**
     * ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â°Ã‚Â
     */
    protected @Nullable Vec2 size = null;

    public BedrockModel(BedrockModelPOJO pojo, BedrockVersion version) {
        if (version == BedrockVersion.LEGACY) {
            loadLegacyModel(pojo);
        }
        if (version == BedrockVersion.NEW) {
            loadNewModel(pojo);
        }
        for (ModelRendererWrapper rendererWrapper : modelMap.values()) {
            if (rendererWrapper.getModelRenderer().name != null && rendererWrapper.getModelRenderer().name.endsWith("_illuminated")) {
                rendererWrapper.getModelRenderer().illuminated = true;
            }
        }
    }

    protected BedrockModel() {
    }

    public void delegateRender(IFunctionalRenderer renderer) {
        delegateRenderers.add(renderer);
    }

    private void setRotationAngle(BedrockPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
        modelRenderer.setInitRotationAngle(x, y, z);
    }

    protected void loadNewModel(BedrockModelPOJO pojo) {
        assert pojo.getGeometryModelNew() != null;
        pojo.getGeometryModelNew().deco();
        if (pojo.getGeometryModelNew().getBones() == null) {
            return;
        }
        Description description = pojo.getGeometryModelNew().getDescription();
        int texWidth = description.getTextureWidth();
        int texHeight = description.getTextureHeight();

        List<Float> offset = description.getVisibleBoundsOffset();
        float offsetX = offset.get(0);
        float offsetY = offset.get(1);
        float offsetZ = offset.get(2);
        this.offset = new Vec3(offsetX, offsetY, offsetZ);
        float width = description.getVisibleBoundsWidth() / 2.0f;
        float height = description.getVisibleBoundsHeight() / 2.0f;
        this.size = new Vec2(width, height);

        for (BonesItem bones : pojo.getGeometryModelNew().getBones()) {
            indexBones.putIfAbsent(bones.getName(), bones);
            modelMap.putIfAbsent(bones.getName(), new ModelRendererWrapper(new BedrockPart(bones.getName())));
        }

        for (BonesItem bones : pojo.getGeometryModelNew().getBones()) {
            String name = bones.getName();
            @Nullable List<Float> rotation = bones.getRotation();
            @Nullable String parent = bones.getParent();
            BedrockPart model = modelMap.get(name).getModelRenderer();

            model.mirror = bones.isMirror();

            model.setPos(convertPivot(bones, 0), convertPivot(bones, 1), convertPivot(bones, 2));

            if (rotation != null) {
                setRotationAngle(model, convertRotation(rotation.get(0)), convertRotation(rotation.get(1)), convertRotation(rotation.get(2)));
            }

            if (parent != null) {
                BedrockPart parentPart = modelMap.get(parent).getModelRenderer();
                parentPart.addChild(model);
                model.parent = parentPart;
            } else {
                shouldRender.add(model);
                model.parent = null;
            }

            if (bones.getCubes() == null) {
                continue;
            }

            for (CubesItem cube : bones.getCubes()) {
                List<Float> uv = cube.getUv();
                @Nullable FaceUVsItem faceUv = cube.getFaceUv();
                List<Float> size = cube.getSize();
                @Nullable List<Float> cubeRotation = cube.getRotation();
                boolean mirror = cube.isMirror();
                float inflate = cube.getInflate();

                if (cubeRotation == null) {
                    if (faceUv == null) {
                        model.cubes.add(new BedrockCubeBox(uv.get(0), uv.get(1),
                                convertOrigin(bones, cube, 0), convertOrigin(bones, cube, 1), convertOrigin(bones, cube, 2),
                                size.get(0), size.get(1), size.get(2), inflate, mirror,
                                texWidth, texHeight));
                    } else {
                        model.cubes.add(new BedrockCubePerFace(
                                convertOrigin(bones, cube, 0), convertOrigin(bones, cube, 1), convertOrigin(bones, cube, 2),
                                size.get(0), size.get(1), size.get(2), inflate,
                                texWidth, texHeight, faceUv));
                    }
                }
                else {
                    BedrockPart cubeRenderer = new BedrockPart(null);
                    cubeRenderer.setPos(convertPivot(bones, cube, 0), convertPivot(bones, cube, 1), convertPivot(bones, cube, 2));
                    setRotationAngle(cubeRenderer, convertRotation(cubeRotation.get(0)), convertRotation(cubeRotation.get(1)), convertRotation(cubeRotation.get(2)));
                    if (faceUv == null) {
                        cubeRenderer.cubes.add(new BedrockCubeBox(uv.get(0), uv.get(1),
                                convertOrigin(cube, 0), convertOrigin(cube, 1), convertOrigin(cube, 2),
                                size.get(0), size.get(1), size.get(2), inflate, mirror,
                                texWidth, texHeight));
                    } else {
                        cubeRenderer.cubes.add(new BedrockCubePerFace(
                                convertOrigin(cube, 0), convertOrigin(cube, 1), convertOrigin(cube, 2),
                                size.get(0), size.get(1), size.get(2), inflate,
                                texWidth, texHeight, faceUv));
                    }

                    model.addChild(cubeRenderer);
                }
            }
        }
    }

    protected void loadLegacyModel(BedrockModelPOJO pojo) {
        assert pojo.getGeometryModelLegacy() != null;
        pojo.getGeometryModelLegacy().deco();
        if (pojo.getGeometryModelLegacy().getBones() == null) {
            return;
        }

        int texWidth = pojo.getGeometryModelLegacy().getTextureWidth();
        int texHeight = pojo.getGeometryModelLegacy().getTextureHeight();

        List<Float> offset = pojo.getGeometryModelLegacy().getVisibleBoundsOffset();
        float offsetX = offset.get(0);
        float offsetY = offset.get(1);
        float offsetZ = offset.get(2);
        this.offset = new Vec3(offsetX, offsetY, offsetZ);
        float width = pojo.getGeometryModelLegacy().getVisibleBoundsWidth() / 2.0f;
        float height = pojo.getGeometryModelLegacy().getVisibleBoundsHeight() / 2.0f;
        this.size = new Vec2(width, height);

        for (BonesItem bones : pojo.getGeometryModelLegacy().getBones()) {
            indexBones.putIfAbsent(bones.getName(), bones);
            modelMap.putIfAbsent(bones.getName(), new ModelRendererWrapper(new BedrockPart(bones.getName())));
        }

        for (BonesItem bones : pojo.getGeometryModelLegacy().getBones()) {
            String name = bones.getName();
            @Nullable List<Float> rotation = bones.getRotation();
            @Nullable String parent = bones.getParent();
            BedrockPart model = modelMap.get(name).getModelRenderer();

            model.mirror = bones.isMirror();

            model.setPos(convertPivot(bones, 0), convertPivot(bones, 1), convertPivot(bones, 2));

            if (rotation != null) {
                setRotationAngle(model, convertRotation(rotation.get(0)), convertRotation(rotation.get(1)), convertRotation(rotation.get(2)));
            }

            if (parent != null) {
                modelMap.get(parent).getModelRenderer().addChild(model);
            } else {
                shouldRender.add(model);
            }

            if (bones.getCubes() == null) {
                continue;
            }

            for (CubesItem cube : bones.getCubes()) {
                List<Float> uv = cube.getUv();
                List<Float> size = cube.getSize();
                boolean mirror = cube.isMirror();
                float inflate = cube.getInflate();

                model.cubes.add(new BedrockCubeBox(uv.get(0), uv.get(1),
                        convertOrigin(bones, cube, 0), convertOrigin(bones, cube, 1), convertOrigin(bones, cube, 2),
                        size.get(0), size.get(1), size.get(2), inflate, mirror,
                        texWidth, texHeight));
            }
        }
    }

    /**
     * ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â¿Ã†â€™ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ Java ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚ÂªÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â Ã‚Â·ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã‚ÂÃ‚Â¢
     * <p>
     * ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
     * <li>xÃƒÂ¯Ã‚Â¼Ã…â€™z ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ - ÃƒÂ§Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
     * <li>y ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã…Â¡ÃƒÂ§Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ - ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
     * <p>
     * ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
     * <li>xÃƒÂ¯Ã‚Â¼Ã…â€™z ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂÃ‹Å“
     * <li>y ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã…Â¡24 - ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
     *
     * @param index ÃƒÂ¦Ã‹Å“Ã‚Â¯ xyz ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂªÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™x ÃƒÂ¦Ã‹Å“Ã‚Â¯ 0ÃƒÂ¯Ã‚Â¼Ã…â€™y ÃƒÂ¦Ã‹Å“Ã‚Â¯ 1ÃƒÂ¯Ã‚Â¼Ã…â€™z ÃƒÂ¦Ã‹Å“Ã‚Â¯ 2
     */
    protected float convertPivot(BonesItem bones, int index) {
        if (bones.getParent() != null) {
            if (index == 1) {
                return indexBones.get(bones.getParent()).getPivot().get(index) - bones.getPivot().get(index);
            } else {
                return bones.getPivot().get(index) - indexBones.get(bones.getParent()).getPivot().get(index);
            }
        } else {
            if (index == 1) {
                return 24 - bones.getPivot().get(index);
            } else {
                return bones.getPivot().get(index);
            }
        }
    }

    protected float convertPivot(BonesItem parent, CubesItem cube, int index) {
        assert cube.getPivot() != null;
        if (index == 1) {
            return parent.getPivot().get(index) - cube.getPivot().get(index);
        } else {
            return cube.getPivot().get(index) - parent.getPivot().get(index);
        }
    }

    /**
     * ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ Java ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¨Ã‚ÂµÃ‚Â·ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¤Ã‚Â¹Ã…Â¸ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™Java ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â€šÂ¬Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â y ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â€šÂ¬Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â y ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â¸Ã‚Â¤ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â§Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â¾Ã‹â€ ÃƒÂ§Ã‚Â®Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã‹â€ Ã¢â‚¬ËœÃƒÂ¦Ã¢â‚¬Â°Ã‚Â¾ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‹â€ ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‹Å“Ã…Â½ÃƒÂ§Ã¢â€žÂ¢Ã‚Â½ÃƒÂ¥Ã¢â‚¬â„¢Ã¢â‚¬Â¹ÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * <li>ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ xÃƒÂ¯Ã‚Â¼Ã…â€™z ÃƒÂ¨Ã‚Â½Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¨Ã‚ÂµÃ‚Â·ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã…Â½Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
     * <li>ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ y ÃƒÂ¨Ã‚Â½Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã…Â½Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¨Ã‚ÂµÃ‚Â·ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã…Â½Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ y ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿ÃƒÂ¥Ã‚ÂºÃ‚Â¦
     *
     * @param index ÃƒÂ¦Ã‹Å“Ã‚Â¯ xyz ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂªÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™x ÃƒÂ¦Ã‹Å“Ã‚Â¯ 0ÃƒÂ¯Ã‚Â¼Ã…â€™y ÃƒÂ¦Ã‹Å“Ã‚Â¯ 1ÃƒÂ¯Ã‚Â¼Ã…â€™z ÃƒÂ¦Ã‹Å“Ã‚Â¯ 2
     */
    protected float convertOrigin(BonesItem bone, CubesItem cube, int index) {
        if (index == 1) {
            return bone.getPivot().get(index) - cube.getOrigin().get(index) - cube.getSize().get(index);
        } else {
            return cube.getOrigin().get(index) - bone.getPivot().get(index);
        }
    }

    protected float convertOrigin(CubesItem cube, int index) {
        assert cube.getPivot() != null;
        if (index == 1) {
            return cube.getPivot().get(index) - cube.getOrigin().get(index) - cube.getSize().get(index);
        } else {
            return cube.getOrigin().get(index) - cube.getPivot().get(index);
        }
    }

    /**
     * ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™Java ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚Â¼Ã‚Â§ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¾Ã‹â€ ÃƒÂ§Ã‚Â®Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢
     */
    protected float convertRotation(float degree) {
        return (float) (degree * Math.PI / 180);
    }

    public BedrockPart getNode(String nodeName) {
        ModelRendererWrapper rendererWrapper = modelMap.get(nodeName);
        if (rendererWrapper != null) {
            return rendererWrapper.getModelRenderer();
        } else {
            return null;
        }
    }

    public BonesItem getBone(String name) {
        return indexBones.get(name);
    }

    public void render(PoseStack matrixStack, ItemDisplayContext transformType, RenderType renderType, int light, int overlay) {
        render(matrixStack, transformType, renderType, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void render(PoseStack matrixStack, ItemDisplayContext transformType, RenderType renderType, int light, int overlay, float red, float green, float blue, float alpha) {
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer builder = bufferSource.getBuffer(renderType);

        matrixStack.pushPose();
        for (BedrockPart model : shouldRender) {
            model.render(matrixStack, transformType, builder, light, overlay, red, green, blue, alpha);
        }
        matrixStack.popPose();
        if (!OculusCompat.endBatch(bufferSource)) {
            bufferSource.endBatch(renderType);
        }

        for (IFunctionalRenderer renderer : delegateRenderers) {
            renderer.render(matrixStack, builder, transformType, light, overlay);
        }
        delegateRenderers = new ArrayList<>();
    }

    protected List<BedrockPart> getPath(@Nullable ModelRendererWrapper rendererWrapper) {
        if (rendererWrapper == null) {
            return null;
        }
        BedrockPart part = rendererWrapper.getModelRenderer();
        List<BedrockPart> path = new ArrayList<>();
        Stack<BedrockPart> stack = new Stack<>();
        do {
            stack.push(part);
            part = part.getParent();
        } while (part != null);
        while (!stack.isEmpty()) {
            part = stack.pop();
            path.add(part);
        }
        return path;
    }

    @Nullable
    public Vec3 getOffset() {
        return offset;
    }

    @Nullable
    public Vec2 getSize() {
        return size;
    }

    public List<BedrockPart> getShouldRender() {
        return shouldRender;
    }

    public HashMap<String, BonesItem> getIndexBones() {
        return indexBones;
    }
}































































