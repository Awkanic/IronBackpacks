package gr8pefish.ironbackpacks.api.items.backpacks;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IUpgradableBackpack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ItemIUpgradableITieredBackpack extends Item implements IUpgradableBackpack, ITieredBackpack {

    private final String name; //display name
    private final int rowLength; //length of each row
    private final int rowCount; //number of rows
    private final int size; //size of the backpack
    private final int upgradePoints; //number of upgradePoints
    private final int additionalPoints; //the number of additional upgrade points

    private final ResourceLocation guiResourceLocation; //the resource location of the gui to display
    private final int guiXSize; //the width of the gui
    private final int guiYSize; //the height of the gui

    private List<ITieredBackpack> backpacksAbove; //the backpacks 1 higher in tier (likely a singleton list)
    private int tier; //the tier of the backpack (0 is basic, 1 is iron, 2 is gold, etc.)
    private List<Object[]> tierRecipes; //the recipes that this backpack can be used in to increase it's tier
    private IRecipe itemRecipe; //get the recipe to obtain the backpack as an items

    private final ResourceLocation modelTexture; //the texture for the modelBackpack


    /**
     * The Item that represents an AbstractUpgradableTieredBackpack
     */
    public ItemIUpgradableITieredBackpack(String name, int rowLength, int rowCount, int upgradePoints, int additionalPoints, ResourceLocation guiResourceLocation, int guiXSize, int guiYSize, ResourceLocation modelTexture){
        setMaxStackSize(1);
        setNoRepair();

        setUnlocalizedName(Constants.MODID + "." + IronBackpacksAPI.ITEM_BACKPACK_BASE_NAME + "." + name);

        this.name = name;
        this.rowLength = rowLength;
        this.rowCount = rowCount;
        this.size = rowCount * rowLength;
        this.upgradePoints = upgradePoints;
        this.additionalPoints = additionalPoints;

        this.guiResourceLocation = guiResourceLocation;
        this.guiXSize = guiXSize;
        this.guiYSize = guiYSize;

        this.backpacksAbove = new ArrayList<ITieredBackpack>(); //empty list
        this.tierRecipes = new ArrayList<Object[]>(); //empty list

        this.modelTexture = modelTexture;
    }

    //================================================Override Vanilla Item Methods=========================================

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false; //no more items backpack bobbing hopefully
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false; //no enchants on the backpacks
    }
    

    //=====================================================IBackpack=========================================================

    @Override
    public String getName(ItemStack backpack) {
        return name;
    }

    @Override
    public int getRowCount(ItemStack backpack) {
        return rowCount;
    }

    @Override
    public int getRowLength(ItemStack backpack) {
        return rowLength;
    }

    @Override
    public ResourceLocation getGuiResourceLocation(ItemStack backpack) {
        return guiResourceLocation;
    }

    @Override
    public int getGuiXSize(ItemStack backpack) {
        return guiXSize;
    }

    @Override
    public int getGuiYSize(ItemStack backpack) {
        return guiYSize;
    }

    @Override
    public IRecipe getItemRecipe(ItemStack backpack) {
        return itemRecipe;
    }

    @Override
    public void setItemRecipe(IRecipe recipe) {
        itemRecipe = recipe;
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack backpack) {
        return modelTexture;
    }

    @Override
    public int getSize(ItemStack backpack) {
        return size;
    }

    //====================================================Upgrades======================================================

    @Override
    public int getUpgradePoints(ItemStack backpack) {
        return upgradePoints;
    }

    @Override
    public int getAdditionalUpgradePoints(ItemStack backpack) {
        return additionalPoints;
    }

    //====================================================Tiering=====================================================


    @Override
    public List<ITieredBackpack> getBackpacksAbove(ItemStack backpack) {
        return backpacksAbove;
    }

    @Override
    public boolean hasBackpacksAbove(ItemStack backpack) {
        return (!backpacksAbove.isEmpty());
    }

    @Override
    public void setBackpacksAbove(ItemStack baseBackpack, List<ITieredBackpack> aboveBackpacks) {
        backpacksAbove = aboveBackpacks;
    }

    @Override
    public void setTier(ItemStack backpack, int tier) {
        this.tier = tier;
    }

    @Override
    public int getTier(ItemStack backpack) {
        return tier;
    }

    @Override
    public void setTierRecipes(ItemStack backpack, List<Object[]> tierRecipes) {
        this.tierRecipes = tierRecipes;
    }

    @Override
    public List<Object[]> getTierRecipes(ItemStack backpack) {
        return tierRecipes;
    }

}
