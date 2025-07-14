package com.tacz.guns.api;

import com.google.common.reflect.TypeToken;
import com.tacz.guns.api.modifier.ParameterizedCachePair;
import com.tacz.guns.resource.modifier.custom.InaccuracyModifier;
import com.tacz.guns.resource.pojo.data.gun.*;
import it.unimi.dsi.fastutil.Pair;

import java.util.LinkedList;
import java.util.Map;

/**
 * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½AttachmentCachePropertyÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾key
 */
public class GunProperties {
    public static final GunProperty<Float>                                      ADS_TIME            = GunProperty.of("ads", Float.class);
    /**@deprecated
     * ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬Å¾Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬â€œÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ¥Ã‚Â¤Ã‚Â±ÃƒÂ¨Ã‚Â¯Ã‚Â¯ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™{@link InaccuracyModifier}ÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚Â<br/>
     * ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â Ã‚ÂÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ©Ã¢â€žÂ¢Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¯Ã‚Â·ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ {@link InaccuracyModifier} <br/>
     *
     * ÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¤ModifierÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾idÃƒÂ¤Ã‚Â¹Ã…Â¸ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‹â€ Ã‚Â° {@link InaccuracyModifier} <br/>
     * */
    @Deprecated
    public static final GunProperty<Map<InaccuracyType, Float>>                 AIM_INACCURACY      = GunProperty.of("inaccuracy", new TypeToken<>() {});
    public static final GunProperty<Float>                                      AMMO_SPEED          = GunProperty.of("ammo_speed", Float.class);
    public static final GunProperty<Float>                                      ARMOR_IGNORE        = GunProperty.of("armor_ignore", Float.class);
    public static final GunProperty<LinkedList<ExtraDamage.DistanceDamagePair>> DAMAGE              = GunProperty.of("damage", new TypeToken<>() {});
    public static final GunProperty<Float>                                      EFFECTIVE_RANGE     = GunProperty.of("effective_range", Float.class);
    public static final GunProperty<ExplosionData>                              EXPLOSION           = GunProperty.of("explosion", ExplosionData.class);
    public static final GunProperty<MoveSpeed>                                  MOVE_SPEED          = GunProperty.of("movement_speed", MoveSpeed.class);
    public static final GunProperty<Float>                                      HEADSHOT_MULTIPLIER = GunProperty.of("head_shot", Float.class);
    public static final GunProperty<Ignite>                                     IGNITE              = GunProperty.of("ignite", Ignite.class);
    public static final GunProperty<Map<InaccuracyType, Float>>                 INACCURACY          = GunProperty.of("inaccuracy", new TypeToken<>() {});
    public static final GunProperty<Float>                                      KNOCKBACK           = GunProperty.of("knockback", Float.class);
    public static final GunProperty<Integer>                                    PIERCE              = GunProperty.of("pierce", Integer.class);
    public static final GunProperty<ParameterizedCachePair<Float, Float>>       RECOIL              = GunProperty.of("recoil", new TypeToken<>() {});
    public static final GunProperty<Integer>                                    ROUNDS_PER_MINUTE   = GunProperty.of("rpm", Integer.class);
    public static final GunProperty<Pair<Integer, Boolean>>                     SILENCE             = GunProperty.of("silence", new TypeToken<>() {});
    public static final GunProperty<Float>                                      WEIGHT              = GunProperty.of("weight_modifier", Float.class);

    private GunProperties() {
    }
}































































