package com.svvarg.tfcfireworksaddon;

//import net.minecraft.entity.Entity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.init.Items;
import net.minecraftforge.common.util.EnumHelper;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.crafting.CraftingManager;


@Mod(modid = TFCFireworksAddon.MODID, version = TFCFireworksAddon.VERSION)
public class TFCFireworksAddon {

    public static final String MODID = "svvarg_fireworks_tfc_addon";
    public static final String VERSION = "0.1";

    public static Item shelmet;

    ArmorMaterial sarmor = EnumHelper.addArmorMaterial("sarmor", 20, new int[]{3, 7, 6, 3}, 10);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

//        shelmet = new ItemSArmor(sarmor, 0, "shelmet");
//        GameRegistry.registerItem(shelmet, "SHelmet");        
        
        CraftingManager.getInstance().getRecipeList().add(new RecipeTFCFireworks ());
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
        GameRegistry.addRecipe(new ItemStack(Items.apple),
                "XXX", "XXX", "XXX", 'X', Blocks.leaves);

        ItemStack enchantedSwordItemStack = new ItemStack(Items.stone_sword);
        enchantedSwordItemStack.addEnchantment(Enchantment.sharpness, 1);
        GameRegistry.addShapelessRecipe(enchantedSwordItemStack, Items.flint, Items.stone_sword);

    }
}
