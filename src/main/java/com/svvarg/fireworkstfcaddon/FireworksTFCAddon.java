package com.svvarg.fireworkstfcaddon;

import com.bioxx.tfc.api.TFCItems;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

@Mod(modid = FireworksTFCAddon.MODID, version = FireworksTFCAddon.VERSION)
public class FireworksTFCAddon {

    public static final String MODID = "svvarg_fireworks_tfcaddon";
    public static final String VERSION = "0.1";
    //public static final String CLIENT_PROXY_CLASS = "com.svvarg.fireworkstfcaddon.ClientProxy";

    public static Item tfcfireworks;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        tfcfireworks = new ItemTFCFireworks();
        GameRegistry.registerItem(tfcfireworks, "fireworks");

        //remove old vanilla recipes firework
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getClass().getSimpleName().equals("RecipeFireworks")) {
                recipes.remove(i);
                break;
            }
        }
        //add new
        CraftingManager.getInstance().getRecipeList().add(new RecipeTFCFireworks());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        GameRegistry.addRecipe(new ItemStack(tfcfireworks, 1, 0),
                " C ",
                "BAB",
                " D ", 'A', TFCItems.reeds, 'B', Items.paper, 'C', Items.string, 'D', TFCItems.stick);

        GameRegistry.addRecipe(new ItemStack(tfcfireworks, 1, 0),
                " C ",
                "BAB",
                " D ", 'A', TFCItems.reeds, 'B', Items.paper, 'C', TFCItems.woolYarn, 'D', TFCItems.stick);

        //gunpowder Charge 
        GameRegistry.addRecipe(new ItemStack(tfcfireworks, 1, 1),
                "YXY",
                "XZX",
                "YXY",
                'X', Items.gunpowder, 'Y', new ItemStack(TFCItems.coal, 1, 1),
                'Z', new ItemStack(TFCItems.powder, 1, 2));//Charcoal & Graphite powder
    }
    @SideOnly(Side.CLIENT)   
    @EventHandler    
    public void postInit(FMLInitializationEvent event) {

        //Way to display fireworks crafts at NEI
        if (Loader.isModLoaded("NotEnoughItems")) {
            NEIIntegration.Load();
        }
    }
}
