/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qiwi.pages;

import com.qiwi.dao.DataDao;
import com.qiwi.dao.DataDaoImpl;
import com.qiwi.models.Country;
import com.qiwi.models.Provider;
import com.qiwi.models.TranslatableObj;
import java.util.List;
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
    private static DataDao dao;
    private ActionLink formLink=new ActionLink("formLink");
    private int langId=1, countryId=1;
    public IndexPage(){
        if(dao==null){
            ResourceBundle settings=ResourceBundle.getBundle("settings");
            dao=new DataDaoImpl(getContext().getServletContext().getRealPath("/")+settings.getString("data_file"));
        }
        //checking user browser locale
        List<TranslatableObj> languages=dao.getLanguages();
        if(getContext().getSessionAttribute("lang_id")==null){//first user enter
            String locLang=getContext().getRequest().getLocale().toString();
            if(locLang.equals("ru")||locLang.equals("ru_RU")){
                langId=dao.getLanguages().get(0).getId();
                getContext().setSessionAttribute("lang_id", langId);
                getContext().setSessionAttribute("country_id", dao.getCountryByLangId(langId).getId());
            }else{
                langId=(languages.size()>0)?languages.get(1).getId():languages.get(0).getId();
                getContext().setSessionAttribute("lang_id", langId);
                getContext().setSessionAttribute("country_id", dao.getCountryByLangId(langId).getId());
            }
        }
        //check request params//
        String param=getContext().getRequestParameter("lang_id");
        if(param!=null){
            getContext().setSessionAttribute("lang_id", Integer.parseInt(param));
        }
        param=getContext().getRequestParameter("country_id");
        if(param!=null){
            getContext().setSessionAttribute("country_id", Integer.parseInt(param));
        }
        //get params from session
        langId=(Integer)getContext().getSessionAttribute("lang_id");
        countryId=(Integer)getContext().getSessionAttribute("country_id");

        formLink.addBehavior(new DefaultAjaxBehavior(){
            @Override
            public ActionResult onAction(Control source){
                JSONObject result=new JSONObject();
                int providerId=Integer.parseInt(getContext().getRequestParameter("provider_id"));
                Provider p=dao.getProviderById(providerId);
                Country c=dao.getCountryById(countryId);
                if(p!=null&&p.hasCurrency(c.getCurrencyId())){
                    result.put("commission", p.getCommissions().get(c.getCurrencyId()));
                    result.put("currency", dao.getCurrencyById(c.getCurrencyId()).getName(langId));
                }else{
                    result.put("error", "invalid parameters");
                }
                return new ActionResult(result.toJSONString(), ActionResult.JSON);
            }
        });
        Country country=dao.getCountryById(countryId);
        addModel("langId", langId);
        addModel("countryId", countryId);
        addModel("strings", dao.getStrings());
        addModel("countries", dao.getCountries());
        addModel("languages", languages);
        addModel("providers", dao.getProviders(country.getCurrencyId()));
        addControl(formLink);
    }
    @Override
    public String getTemplate(){
        return "index.htm";
    }
}
