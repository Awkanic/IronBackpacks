package gr8pefish.ironbackpacks.items.upgrades;

import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IPackUpgrade;
import gr8pefish.ironbackpacks.items.ItemBase;
import gr8pefish.ironbackpacks.util.TextUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract base class for all upgrades
 */
//public abstract class ItemUpgradeBase extends ItemBase implements IPackUpgrade {
//
//    private int typeID;
//    private String[] tooltip;
//    private int upgradeCost;
//    private String name;
//
//    public ItemUpgradeBase(String unlocName, int typeID, int upgradeCost, String... descriptions) {
//        super(unlocName, unlocName); //unlocName, textureName (they are the same)
//        setMaxStackSize(16);
//        this.typeID = typeID;
//        this.tooltip = descriptions;
//        this.upgradeCost = upgradeCost;
//        this.name = unlocName;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    @SuppressWarnings("unchecked")
//    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced) {
//        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
//            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.cost", getUpgradeCost(itemStack), getUpgradeCost(itemStack) == 1 ? "" : "s"));
//            list.addAll(getTooltip(itemStack));
//        } else {
//            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.shift"));
//        }
//    }
//
//    @Override
//    public String getName(ItemStack stack){
//        return this.name;
//    }
//
////    @Override
//    public int getId(ItemStack stack){
//        return this.typeID;
//    }
//
//    @Override
//    public int getUpgradeCost(ItemStack stack) {
//        return this.upgradeCost;
//    }
//
//    @Override
//    public List<String> getTooltip(ItemStack stack){
//        return Arrays.asList(tooltip);
//    }
//
//
//}
