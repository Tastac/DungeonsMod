package io.github.tastac.dungeonsmod;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsConfig {

    private static final String CONFIG_ARTIFACT = DungeonsMod.MOD_ID + ".artifact.";
    private static final String CONFIG_ARTIFACT_PROPERTIES = CONFIG_ARTIFACT + "properties.";

    public static class Client {

        public ForgeConfigSpec.IntValue regenerationTotemColor;
        public ForgeConfigSpec.IntValue shieldTotemColor;

        public Client(ForgeConfigSpec.Builder builder) {
            int colorMin = Integer.MIN_VALUE;
            int colorMax = Integer.MAX_VALUE;
            this.regenerationTotemColor = builder.defineInRange(CONFIG_ARTIFACT_PROPERTIES + "regenerationTotemColor", 0xff1600, colorMin, colorMax);
            this.shieldTotemColor = builder.defineInRange(CONFIG_ARTIFACT_PROPERTIES + "shieldTotemColor", 0xcc8f00, colorMin, colorMax);
        }
    }

    public static class Server {

        public ForgeConfigSpec.BooleanValue canRightClickEquip;

        public ForgeConfigSpec.DoubleValue totemOfRegenerationSpeed;
        public ForgeConfigSpec.DoubleValue shieldBounceOffset;

        public Server(ForgeConfigSpec.Builder builder) {
            this.canRightClickEquip = builder.comment("If true, you can press the right-mouse button to equip an artifact").define(CONFIG_ARTIFACT + "canRightClickEquip", true);

            this.totemOfRegenerationSpeed = builder.comment("How long it takes the Totem Of Regeneration it heal nearby players per second")
                    .defineInRange(CONFIG_ARTIFACT_PROPERTIES + "totemOfRegenerationSpeed", .75d, .5d, 60d);
            this.shieldBounceOffset = builder.comment("How close a projectile appears to bounce of the Totem Of Shielding")
                    .defineInRange(CONFIG_ARTIFACT_PROPERTIES + "shieldBounceOffset", .5d, 0d, 1d);
        }
    }
}
