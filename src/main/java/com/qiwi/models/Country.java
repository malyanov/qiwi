/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qiwi.models;

import java.util.HashMap;

/**
 *
 * @author Malyanov Dmitry
 */
public class Country extends TranslatableObj{
    private int langId, currencyId;
    public Country(int id, HashMap<Integer, String> names, int langId, int currencyId){
        super(id, names);
        this.langId=langId;
        this.currencyId=currencyId;
    }
    public int getLangId(){
        return langId;
    }
    public int getCurrencyId(){
        return currencyId;
    }
}
