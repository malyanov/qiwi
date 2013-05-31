/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qiwi.pages;

import com.qiwi.AppCache;
import com.qiwi.models.Country;
import com.qiwi.models.Provider;
import java.util.ResourceBundle;
import org.apache.click.ActionResult;
import org.apache.click.Control;
import org.apache.click.Page;
import org.apache.click.ajax.DefaultAjaxBehavior;
import org.apache.click.control.ActionLink;
import org.json.simple.JSONObject;

/**
 *
 * @author MalyanovDmitry
 */
public class IndexPage extends Page{
    private static AppCache cache;
    private ActionLink formLink=new ActionLink("formLink");
    private int langId=1, countryId=1;
    public IndexPage(){
        if(cache==null){
            ResourceBundle settings=ResourceBundle.getBundle("settings");
            cache=new AppCache(settings.getString("data_url"));
        }
        //check request params
        String param=getContext().getRequestParameter("lang_id");
        if(param!=null){
            getContext().setSessionAttribute("lang_id", Integer.parseInt(param));
        }
        param=getContext().getRequestParameter("country_id");
        if(param!=null){
            getContext().setSessionAttribute("country_id", Integer.parseInt(param));
        }
        //check session
        Object obj=getContext().getSessionAttribute("lang_id");
        if(obj!=null){
            langId=(Integer)obj;
        }else{
            getContext().setSessionAttribute("lang_id", langId);
        }
        obj=getContext().getSessionAttribute("country_id");
        if(obj!=null){
            countryId=(Integer)obj;
        }else{
            getContext().setSessionAttribute("country_id", countryId);
        }
        formLink.addBehavior(new DefaultAjaxBehavior(){
            @Override
            public ActionResult onAction(Control source){
                JSONObject result=new JSONObject();
                int providerId=Integer.parseInt(getContext().getRequestParameter("provider_id"));
                Provider p=cache.getProviderById(providerId);
                Country c=cache.getCountryById(countryId);
                if(p!=null&&p.hasCurrency(c.getCurrencyId())){
                    result.put("commission", p.getCommissions().get(c.getCurrencyId()));
                }else{
                    result.put("error", "invalid parameters");
                }
                return new ActionResult(result.toJSONString(), ActionResult.JSON);
            }
        });
        addModel("langId", langId);
        addModel("countryId", countryId);
        addModel("countries", cache.getCountries());
        addModel("languages", cache.getLanguages());
        addModel("providers", cache.getProviders(countryId));
        addControl(formLink);
    }
    @Override
    public String getTemplate(){
        return "index.htm";
    }
}
