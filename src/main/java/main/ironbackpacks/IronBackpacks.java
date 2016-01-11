package main.ironbackpacks;

import main.ironbackpacks.client.gui.GuiHandler;
import main.ironbackpacks.crafting.ItemRecipeRegistry;
import main.ironbackpacks.entity.Entities;
import main.ironbackpacks.events.FMLEventHandler;
import main.ironbackpacks.events.ForgeEventHandler;
import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.integration.InterModSupport;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND)//, guiFactory = ModInformation.GUIFACTORY)
public class IronBackpacks {

	//Make a custom creative tab with the iron backpack as the logo
	public static final CreativeTabs creativeTab = new CreativeTabs(ModInformation.ID) {
		@Override
		public Item getTabIconItem() {
			return ItemRegistry.ironBackpack;
		}
	};

	//The proxies for siding
	@SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
	public static CommonProxy proxy;

	//The instance of this mod
	@Mod.Instance
	public static IronBackpacks instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		//compatibility
		InterModSupport.preinit();

		//init the keybindings and rendering
		proxy.init();

		//config file
		File config = event.getSuggestedConfigurationFile();
		ConfigHandler.init(config);

		//networking
		NetworkingHandler.initPackets();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		//items
		ItemRegistry.registerItems();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		//compatibility
		InterModSupport.init();

		//event handlers
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
		FMLCommonHandler.instance().bus().register(new FMLEventHandler());

		//recipes
		ItemRecipeRegistry.registerItemRecipes();

		//entity registering
		Entities.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		//compatibility
		InterModSupport.postinit();

	}
}
