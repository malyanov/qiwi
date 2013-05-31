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
public class TranslatableObj{
    private int id;
    private HashMap<Integer, String> names;
    public TranslatableObj(int id, HashMap<Integer, String> namesMap){
        this.id=id;
        this.names=namesMap;
    }
    public String getName(Integer langId){
        return names.get(langId);
    }
    public int getId(){
        return id;
    }
}
