package com.svvarg.fireworkstfcaddon;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.InventoryCraftingDummy;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import com.bioxx.tfc.api.TFCItems;
import static com.svvarg.fireworkstfcaddon.FireworksTFCAddon.tfcfireworks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FireworkTFCRecipeHandler extends ShapelessRecipeHandler
{
    public class CachedFireworkRecipe extends CachedShapelessRecipe
    {
        LinkedList<Object> itemList = new LinkedList<Object>();

        public Object[] baseIngredients;
        public Object extraIngred;
        public int recipeType;

        public CachedFireworkRecipe(Object[] base, Object extra, int type) {
            super(new ItemStack(Items.fireworks));
            this.baseIngredients = base;
            this.extraIngred = extra;
            this.recipeType = type;

            cycle();
        }

        public void cycle() {
            itemList.clear();
            for (Object obj : baseIngredients)
                itemList.add(obj);
            int extras = (cycleticks / 40) % (10 - itemList.size());
            for (int i = 0; i < extras; i++)
                itemList.add(extraIngred);
            setIngredients(itemList);

            List<PositionedStack> ingreds = getIngredients();
            for (int i = 0; i < 9; i++)
                inventoryCrafting.setInventorySlotContents(i, i < ingreds.size() ? ingreds.get(i).item : null);

            if (!recipeTFCFireworks.matches(inventoryCrafting, null))
                throw new RuntimeException("Invalid Recipe?");
            setResult(recipeTFCFireworks.getCraftingResult(null));
        }
    }

    private InventoryCrafting inventoryCrafting = new InventoryCraftingDummy();
    private RecipeTFCFireworks recipeTFCFireworks = new RecipeTFCFireworks();

    public ArrayList<CachedFireworkRecipe> mfireworks = new ArrayList<CachedFireworkRecipe>();

    public FireworkTFCRecipeHandler() {
        super();
        stackorder = new int[][]{
                {0, 0},
                {1, 0},
                {2, 0},
                {0, 1},
                {1, 1},
                {2, 1},
                {0, 2},
                {1, 2},
                {2, 2}};
        loadAllFireworks();
    }

    private void loadAllFireworks() {
        ItemStack gold = new ItemStack(TFCItems.smallOreChunk, 1, 1);
        Item goldNugget = gold.getItem();
        
        //charges
        Item[] shapes = new Item[]{null, Items.fire_charge,  goldNugget/*Items.gold_nugget*/, Items.feather,  TFCItems.wroughtIronUnfinishedHelmet /*Items.skull*/};
        Item[] effects = new Item[]{null, TFCItems.gemDiamond, Items.redstone /* glowstone_dust*/};
        for (Item shape : shapes)
            for (Item effect : effects)
                genRecipe(Items.gunpowder, shape, effect, TFCItems.dye , TFCItems.dye , 0);
        
        Item Capsule = (new ItemStack(tfcfireworks, 1, 0)).getItem();
        Item Charge = (new ItemStack(tfcfireworks, 1, 1)).getItem();
        //fireworks
        genRecipe(Items.gunpowder, Capsule/* Items.paper*/,Charge/* Items.firework_charge*/, 2);
        genRecipe(Items.gunpowder, Items.gunpowder, Capsule/* Items.paper*/,Charge/* Items.firework_charge*/, 2);
        genRecipe(Items.gunpowder, Items.gunpowder, Items.gunpowder, Capsule/* Items.paper*/, Charge/* Items.firework_charge*/, 2);

        //setup a valid charge to use for the recolour recipe
        for (int i = 0; i < 9; i++)
            inventoryCrafting.setInventorySlotContents(i, null);
        inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.gunpowder));
        inventoryCrafting.setInventorySlotContents(1, new ItemStack(TFCItems.dye));
        recipeTFCFireworks.matches(inventoryCrafting, null);
        ItemStack charge = recipeTFCFireworks.getCraftingResult(null);
        genRecipe(charge,TFCItems.dye ,TFCItems.dye , 1);
    }

    private void genRecipe(Object... params) {
        int numIngreds = 0;
        for (int i = 0; i < params.length - 2; i++)
            if (params[i] != null)
                numIngreds++;

        for (int i = 0; i < params.length - 1; i++)
            if (params[i] instanceof Item)
                params[i] = new ItemStack((Item) params[i], 1, Short.MAX_VALUE);

        Object[] ingreds = new Object[numIngreds];
        for (int i = 0, j = 0; i < params.length - 2; i++)
            if (params[i] != null)
                ingreds[j++] = params[i];

        mfireworks.add(new CachedFireworkRecipe(ingreds, params[params.length - 2], (Integer) params[params.length - 1]));
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (CachedFireworkRecipe recipe : mfireworks) {
            if (recipe.result.item.getItem() == result.getItem()) {
                recipe.cycle();
                arecipes.add(recipe);
            }
        }
        //show random recolouring recipes as well
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting") && getClass() == FireworkTFCRecipeHandler.class) {
            arecipes.addAll(mfireworks);
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (CachedFireworkRecipe recipe : mfireworks) {
            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.cycle();
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void onUpdate() {
        if (!NEIClientUtils.shiftKey()) {
            cycleticks++;
            if (cycleticks % 20 == 0)
                for (CachedRecipe crecipe : arecipes)
                    ((CachedFireworkRecipe) crecipe).cycle();
        }
    }

    @Override
    public String getRecipeName() {
        return NEIClientUtils.translate("recipe.firework");
    }
/*
    @Override
    public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe) {
        currenttip = super.handleTooltip(gui, currenttip, recipe);
        Point mousepos = GuiDraw.getMousePosition();
        Point relMouse = new Point(mousepos.x -gui.guiLeft, mousepos.y - gui.guiTop);
        Point recipepos = gui.getRecipePosition(recipe);
        if (currenttip.isEmpty() && GuiContainerManager.getStackMouseOver(gui) == null &&
                new Rectangle(recipepos.x, recipepos.y, 166, 55).contains(relMouse))
            currenttip.add(NEIClientUtils.translate(
                    "recipe.fireworkTFC.tooltip" + ((CachedFireworkRecipe) arecipes.get(recipe)).recipeType));
        return currenttip;

    }*/
}
