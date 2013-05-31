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
public class Provider extends TranslatableObj{
    private HashMap<Integer, Integer> commissions;
    public Provider(int id, HashMap<Integer, Integer> commissions, HashMap<Integer, String> names){
        super(id,names);
        this.commissions=commissions;
    }
    public HashMap<Integer, Integer> getCommissions(){
        return commissions;
    }
    public boolean hasCurrency(int currencyId){
        return commissions.keySet().contains(currencyId);
    }
}
