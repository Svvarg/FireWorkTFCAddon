package com.svvarg.fireworkstfcaddon;

//import net.minecraft.entity.Entity;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.Iterator;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraftforge.common.util.EnumHelper;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

@Mod(modid = FireworksTFCAddon.MODID, version = FireworksTFCAddon.VERSION)
public class FireworksTFCAddon {

    public static final String MODID = "svvarg_fireworks_tfcaddon";
    public static final String VERSION = "0.1";

    public static Item fireworksFlask;
    public static Item powderCharge;

    public static Item shelmet;

//    ArmorMaterial sarmor = EnumHelper.addArmorMaterial("sarmor", 20, new int[]{3, 7, 6, 3}, 10);
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        fireworksFlask = new ItemFireworks();
        GameRegistry.registerItem(fireworksFlask, "fireworks");

//        shelmet = new ItemSArmor(sarmor, 0, "shelmet");
//        GameRegistry.registerItem(shelmet, "SHelmet");        

/*        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        Iterator<IRecipe> Leash = recipes.iterator();
        while (Leash.hasNext()) {
            ItemStack is = Leash.next().getRecipeOutput();
            //System.out.println( is.getDisplayName());
///is.itemID == Items.lead.itemID is.getItem() == Items.fireworks
            if (is != null &&  true) {
                System.out.println( is.getDisplayName() );
                //Leash.remove();
            }
        };*/

        CraftingManager.getInstance().getRecipeList().add(new RecipeTFCFireworks());

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
        GameRegistry.addRecipe(new ItemStack(fireworksFlask, 1, 0),
                " C ",
                "BAB",
                " D ", 'A', TFCItems.reeds, 'B', Items.paper, 'C', Items.string, 'D', TFCItems.stick);

        GameRegistry.addRecipe(new ItemStack(fireworksFlask, 1, 0),
                " C ",
                "BAB",
                " D ", 'A', TFCItems.reeds, 'B', Items.paper, 'C', TFCItems.woolYarn, 'D', TFCItems.stick);

        GameRegistry.addRecipe(new ItemStack(fireworksFlask, 1, 1),
                "YXY",
                "XZX",
                "YXY", 'X', Items.gunpowder, 'Y', TFCItems.coal, 'Z', Items.redstone);

    }
}
