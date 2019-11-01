/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svvarg.fireworkstfcaddon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

/**
 *
 * @author Swarg
 */
public class ItemTFCFireworks extends Item{
    private static final String[] NAME = {"fireworksCapsule","powderCharge"};
    
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    
    public ItemTFCFireworks() {
        setUnlocalizedName(FireworksTFCAddon.MODID+"_");/*was used getUnlocalizedName overwritten there */
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMisc);        
    }
    
    @Override /* tell what is metod overwriten from out code
    and then is method is delete this shom me it by error in there*/
    public String getUnlocalizedName(ItemStack par1ItemStack){
        int metadata = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0,15);
        return super.getUnlocalizedName()+"."+NAME[metadata];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister){
        icons = new IIcon[NAME.length];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = par1IconRegister.registerIcon( FireworksTFCAddon.MODID+":"+NAME[i]);            
        }
    }
    
    @Override
    public IIcon getIconFromDamage(int par1){
        return icons[par1];
    }
    
    @SuppressWarnings({"unchecked","rawtypes"})
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item par1,CreativeTabs par2CreativeTabs, List par3List){
        for (int x = 0; x < NAME.length; x++) {
            par3List.add(new ItemStack(this,1,x));            
        }
    }
    
    
}
