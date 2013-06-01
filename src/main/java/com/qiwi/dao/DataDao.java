/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qiwi.dao;

import com.qiwi.models.Country;
import com.qiwi.models.Provider;
import com.qiwi.models.TranslatableObj;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author MalyanovDmitry
 */
public interface DataDao{
    public List<TranslatableObj> getLanguages();
    public List<Provider> getProviders(int currencyId);
    public List<Country> getCountries();
    public HashMap<Integer, TranslatableObj> getStrings();
    public Provider getProviderById(int id);
    public Country getCountryById(int id);
    public Country getCountryByLangId(int langId);
    public TranslatableObj getCurrencyById(int id);
}
