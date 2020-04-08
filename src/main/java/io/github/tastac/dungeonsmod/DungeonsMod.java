package io.github.tastac.dungeonsmod;

import io.github.tastac.dungeonsmod.commands.TestCommand;
import io.github.tastac.dungeonsmod.common.init.DungeonsBlocks;
import io.github.tastac.dungeonsmod.common.init.DungeonsEntities;
import io.github.tastac.dungeonsmod.common.init.DungeonsItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.logging.Logger;

import static io.github.tastac.dungeonsmod.DungeonsMod.MOD_ID;

@SuppressWarnings("unused")
@Mod(MOD_ID)
public class DungeonsMod {

    public static final String MOD_ID = "dungeonsmod";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static final ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(DungeonsBlocks.CRATE.get());
        }
    };

    public DungeonsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);

        DungeonsItems.ITEMS.register(modBus);
        DungeonsBlocks.BLOCKS.register(modBus);
        DungeonsEntities.ENTITIES.register(modBus);
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        ClientEvents.registerBlockColors();
        ClientEvents.registerBlockRenderLayers();
        ClientEvents.registerEntityRenderers();
    }

    @SubscribeEvent
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        TestCommand.register(event.getCommandDispatcher());
    }
}