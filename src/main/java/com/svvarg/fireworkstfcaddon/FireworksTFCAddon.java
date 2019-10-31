package com.svvarg.fireworkstfcaddon;

//import net.minecraft.entity.Entity;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

@Mod(modid = FireworksTFCAddon.MODID, version = FireworksTFCAddon.VERSION)
public class FireworksTFCAddon {

    public static final String MODID = "svvarg_fireworks_tfcaddon";
    public static final String VERSION = "0.1";

    public static Item tfcfireworks;
    

    public static Item shelmet;

//    ArmorMaterial sarmor = EnumHelper.addArmorMaterial("sarmor", 20, new int[]{3, 7, 6, 3}, 10);
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        tfcfireworks = new ItemFireworks();
        GameRegistry.registerItem(tfcfireworks, "fireworks");
        

//        shelmet = new ItemSArmor(sarmor, 0, "shelmet");
//        GameRegistry.registerItem(shelmet, "SHelmet");        

        //CraftingManager.getInstance().getRecipeList().add(new RecipeTFCFireworks());

        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++) {
           if ( recipes.get(i).getClass().getSimpleName().equals("RecipeFireworks") ){
              //CraftingManager.getInstance().getRecipeList().set(i, new RecipeTFCFireworks() ); 
              recipes.set(i, new RecipeTFCFireworks());
              break;                      
           }
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        
        // Caplule
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
                'X', Items.gunpowder, 'Y', new ItemStack(TFCItems.coal,1,1), 
                'Z', new ItemStack(TFCItems.powder, 1, 2));//Charcoal & Graphite powder

    }
}
