/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svvarg.fireworkstfcaddon;

import codechicken.nei.api.API;
import codechicken.nei.recipe.FireworkRecipeHandler;


/**
 *
 * @author Swarg
 */
public class NEIIntegration {
    public static void Load(){
               
        //API.registerRecipeHandler(new FireworkTFCRecipeHandler());
        API.registerUsageHandler(new FireworkTFCRecipeHandler());

    }
    
    
}
