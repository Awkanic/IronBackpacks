package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IUpgradableBackpack;
import gr8pefish.ironbackpacks.api.register.IAllRecipesRegistry;
import gr8pefish.ironbackpacks.api.register.ItemIBackpackRegistry;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.crafting.BackpackAddUpgradeRecipe;
import gr8pefish.ironbackpacks.crafting.BackpackIncreaseTierRecipe;
import gr8pefish.ironbackpacks.crafting.BackpackRemoveUpgradeRecipe;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.libs.recipes.ItemBackpackRecipes;
import gr8pefish.ironbackpacks.libs.recipes.ItemCraftingRecipes;
import gr8pefish.ironbackpacks.libs.recipes.ItemUpgradeRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Register all the recipes here.
 */
public class RecipeRegistry {

	/**
	 * Main method that registers all the recipes
	 */
	public static void registerAllRecipes() {

        //Basic Item Recipes
        ItemCraftingRecipes.registerItemCraftingRecipes(); //register the recipes to get the recipes items
        ItemUpgradeRecipes.registerItemUpgradeRecipes(); //register the recipes to get the the upgrades as items //broken (crashes) //TODO: figure this bope out
		ItemBackpackRecipes.registerItemBackpackRecipes(); //register all the recipes to get the backpacks directly

        //=============Fancy Iron Backpacks Recipes===========

        //Register as special recipes
        RecipeSorter.register("RemoveUpgrade", BackpackRemoveUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, ""); //register my special recipe
        RecipeSorter.register("AddUpgrade", BackpackAddUpgradeRecipe.class, RecipeSorter.Category.SHAPELESS, ""); //register my special recipe
        RecipeSorter.register("IncreaseBackpackTier", BackpackIncreaseTierRecipe.class, RecipeSorter.Category.SHAPED, ""); //register my special recipe

        //Register the recipes themselves
        registerBackpackTierRecipes(); //register the recipes to upgrade a backpack to the next tier
		registerBackpackUpgradeRemovalRecipes(); //register the recipes to remove upgrades from backpacks
        registerBackpackUpgradeAdditionRecipes(); //register the recipes to add upgrades from backpacks


	}

    /**
     * Sets all the non tiered backpack recipes.
     * Not in items initialization in case one items uses another items from this mod, as that items may not be initialized yet (very likely).
     */
    public static void setAllNonTierRecipes(){
        setItemBackpackRecipes();
        setUpgradeRecipes();
    }

	//===========================================================================Set Recipes==================================================================

    private static void setItemBackpackRecipes(){
        ItemRegistry.basicBackpack.setItemRecipe(ItemBackpackRecipes.basicBackpackRecipe);
    }

    private static void setUpgradeRecipes(){
        ItemRegistry.additionalUpgradePointsUpgrade.setItemRecipe(ItemUpgradeRecipes.additionalUpgradePointsRecipe);
        ItemRegistry.buttonUpgrade.setItemRecipe(ItemUpgradeRecipes.buttonUpgradeRecipe);
        ItemRegistry.damageBarUpgrade.setItemRecipe(ItemUpgradeRecipes.damageBarUpgradeRecipe);
        ItemRegistry.depthUpgrade.setItemRecipe(ItemUpgradeRecipes.depthUpgradeRecipe);
        ItemRegistry.eternityUpgrade.setItemRecipe(ItemUpgradeRecipes.eternityUpgradeRecipe);
        if (ConfigHandler.renamingUpgradeRequired) ItemRegistry.renamingUpgrade.setItemRecipe(ItemUpgradeRecipes.renamingUpgradeRecipe);
        ItemRegistry.nestingUpgrade.setItemRecipe(ItemUpgradeRecipes.nestingUpgradeRecipe);
        ItemRegistry.nestingAdvancedUpgrade.setItemRecipe(ItemUpgradeRecipes.nestingAdvancedUpgradeRecipe);
        ItemRegistry.quickDepositUpgrade.setItemRecipe(ItemUpgradeRecipes.quickDepositUpgradeRecipe);
        ItemRegistry.quickDepositPreciseUpgrade.setItemRecipe(ItemUpgradeRecipes.quickDepositPreciseUpgradeRecipe);
        ItemRegistry.craftingUpgrade.setItemRecipe(ItemUpgradeRecipes.craftingUpgradeRecipe);
        ItemRegistry.craftingSmallUpgrade.setItemRecipe(ItemUpgradeRecipes.craftingSmallUpgradeRecipe);
        ItemRegistry.craftingTinyUpgrade.setItemRecipe(ItemUpgradeRecipes.craftingTinyUpgradeRecipe);
        ItemRegistry.filterBasicUpgrade.setItemRecipe(ItemUpgradeRecipes.filterBasicUpgradeRecipe);
        ItemRegistry.filterFuzzyUpgrade.setItemRecipe(ItemUpgradeRecipes.filterFuzzyUpgradeRecipe);
        ItemRegistry.filterOreDictUpgrade.setItemRecipe(ItemUpgradeRecipes.filterOreDictUpgradeRecipe);
        ItemRegistry.filterModSpecificUpgrade.setItemRecipe(ItemUpgradeRecipes.filterModSpecificUpgradeRecipe);
        ItemRegistry.filterAdvancedUpgrade.setItemRecipe(ItemUpgradeRecipes.filterAdvancedUpgradeRecipe);
        ItemRegistry.filterMiningUpgrade.setItemRecipe(ItemUpgradeRecipes.filterMiningUpgradeRecipe);
        ItemRegistry.filterVoidUpgrade.setItemRecipe(ItemUpgradeRecipes.filterVoidUpgradeRecipe);
        ItemRegistry.restockingUpgrade.setItemRecipe(ItemUpgradeRecipes.restockingUpgradeRecipe);
    }

    //=================================================================================Helper Registers==========================================================

    /**
     * Register the upgrade removal recipes for every backpack (if it is an IUpgradableBackpack)
     */
    private static void registerBackpackUpgradeRemovalRecipes(){
        for (int i = 0; i < ItemIBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemIBackpackRegistry.getBackpackAtIndex(i);
            if (backpack instanceof IUpgradableBackpack) {
                BackpackRemoveUpgradeRecipe recipe = new BackpackRemoveUpgradeRecipe(new ItemStack((ItemBackpack)backpack), new ItemStack((ItemBackpack)backpack)); //Hardcoded to ItemBackpack
                GameRegistry.addRecipe(recipe);
                IAllRecipesRegistry.registerUpgradeRemovalRecipe(recipe);
            }
        }
    }

    /**
     * Register the recipe for the addition of upgrades to an IUpgradeableBackpack.
     * If the backpack is also an ITieredBackpack, then the tier of the upgrade must be lower than or equal to the backpack to allow it to be applied.
     */
    private static void registerBackpackUpgradeAdditionRecipes() {
        ArrayList<ItemStack> upgrades = new ArrayList<ItemStack>();
        for (int i = 0; i < ItemIUpgradeRegistry.getTotalSize(); i++)
            upgrades.add(new ItemStack(ItemRegistry.upgradeItem, 1, i));

        for (int i = 0; i < ItemIBackpackRegistry.getSize(); i++) {
            IBackpack backpack = ItemIBackpackRegistry.getBackpackAtIndex(i);
            if (backpack instanceof IUpgradableBackpack) {
                for (ItemStack upgrade : upgrades) {
                    int upgradeTier = ItemIUpgradeRegistry.getItemUpgrade(upgrade).getTier(upgrade);
                    if (backpack instanceof ITieredBackpack) {
                        int backpackTier = ((ITieredBackpack) backpack).getTier(null);
                        if (upgradeTier <= backpackTier) {
                            BackpackAddUpgradeRecipe recipe = new BackpackAddUpgradeRecipe(new ItemStack((ItemBackpack) backpack), upgrade, new ItemStack((ItemBackpack) backpack)); //Hardcoded to ItemBackpack
                            GameRegistry.addRecipe(recipe);
                            IAllRecipesRegistry.registerUpgradeAdditionRecipe(recipe);
                        }
                    } else {
                        //TODO: remove casting for other backpacks (and in above for non-tiered ones)
                        BackpackAddUpgradeRecipe recipe = new BackpackAddUpgradeRecipe(new ItemStack((ItemBackpack) backpack), upgrade, new ItemStack((ItemBackpack) backpack)); //Hardcoded to ItemBackpack
                        GameRegistry.addRecipe(recipe);
                        IAllRecipesRegistry.registerUpgradeAdditionRecipe(recipe);
                    }
                }
            }
        }
    }

    /**
     * Register the recipe for the backpack to increase a tier.
     */
    public static void registerBackpackTierRecipes(){
        for (int i = 0; i < ItemIBackpackRegistry.getSize(); i++){
            IBackpack backpack = ItemIBackpackRegistry.getBackpackAtIndex(i);
            if (backpack instanceof ITieredBackpack) {
                ITieredBackpack newPack = (ITieredBackpack) backpack;
                List<Object[]> recipes = newPack.getTierRecipes(null);
                if (recipes == null) break; //if you have no recipe to upgrade, you can't register that
                List<ITieredBackpack> upgradedPacks = newPack.getBackpacksAbove(null); //unused items stack parameter
                if (!recipes.isEmpty() && upgradedPacks != null && upgradedPacks.size() == recipes.size()) {
                    for (int j = 0; j < recipes.size(); j++) {
                        BackpackIncreaseTierRecipe tierRecipe = new BackpackIncreaseTierRecipe(new ItemStack((ItemBackpack)upgradedPacks.get(j)), recipes.get(j)); //hardcoded to ItemBackpack
                        GameRegistry.addRecipe(tierRecipe);
                        IAllRecipesRegistry.registerTierIncreaseRecipe(tierRecipe);
                    }
                }
            }
        }
    }
}
