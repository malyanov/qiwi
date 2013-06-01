package com.qiwi.dao;

import com.qiwi.models.Country;
import com.qiwi.models.Provider;
import com.qiwi.models.TranslatableObj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Malyanov Dmitry
 */
public class DataDaoImpl implements DataDao{
    private List<TranslatableObj> languages, currencies;
    private HashMap<Integer, TranslatableObj> strings;
    private List<Country> countries;
    private List<Provider> providers;
    public DataDaoImpl(String url){
        try{
            Document doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);
            languages=loadTranslatableObjs(doc, "//languages/item");
            currencies=loadTranslatableObjs(doc, "//currencies/item");
            List<TranslatableObj> list=loadTranslatableObjs(doc, "//strings/item");
            strings=new HashMap<Integer, TranslatableObj>();
            for(TranslatableObj to : list){
                strings.put(to.getId(), to);
            }
            countries=loadCountries(doc, "//countries/item");
            providers=loadProviders(doc, "//providers/item");
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private HashMap<Integer, String> getNames(Element el){
        NodeList namesList=el.getElementsByTagName("name");
        HashMap<Integer, String> names=new HashMap<Integer, String>();
        for(int j=0; j<namesList.getLength(); j++){
            el=(Element)namesList.item(j);
            int langId=0;
            if(!el.getAttribute("lang").isEmpty()){
                langId=Integer.parseInt(el.getAttribute("lang"));
            }
            names.put(langId, el.getTextContent());
        }
        return names;
    }
    private NodeList getNodeList(Document doc, String xpathQuery) throws XPathExpressionException{
        XPathExpression expr=XPathFactory.newInstance().newXPath().compile(xpathQuery);
        return (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
    }
    private List<TranslatableObj> loadTranslatableObjs(Document doc, String xpathQuery) throws XPathExpressionException{
        NodeList nl=getNodeList(doc, xpathQuery);
        List<TranslatableObj> result=new ArrayList<TranslatableObj>();
        for(int i=0; i<nl.getLength(); i++){
            Element el=(Element)nl.item(i);
            int id=Integer.parseInt(el.getAttribute("id"));
            result.add(new TranslatableObj(id, getNames(el)));
        }
        return result;
    }
    private List<Country> loadCountries(Document doc, String xpathQuery) throws XPathExpressionException{
        NodeList nl=getNodeList(doc, xpathQuery);
        List<Country> result=new ArrayList<Country>();
        for(int i=0; i<nl.getLength(); i++){
            Element el=(Element)nl.item(i);
            int id=Integer.parseInt(el.getAttribute("id"));
            int languageId=Integer.parseInt(el.getAttribute("language"));
            int currencyId=Integer.parseInt(el.getAttribute("currency"));
            result.add(new Country(id, getNames(el), languageId, currencyId));
        }
        return result;
    }
    private List<Provider> loadProviders(Document doc, String xpathQuery) throws XPathExpressionException{
        NodeList nl=getNodeList(doc, xpathQuery);
        List<Provider> result=new ArrayList<Provider>();
        for(int i=0; i<nl.getLength(); i++){
            Element el=(Element)nl.item(i);
            int id=Integer.parseInt(el.getAttribute("id"));
            NodeList commisionsList=((Element)el.getElementsByTagName("commissions").item(0)).getElementsByTagName("item");
            HashMap<Integer, Integer> commissions=new HashMap<Integer, Integer>();
            for(int j=0; j<commisionsList.getLength(); j++){
                Element cel=(Element)commisionsList.item(j);
                int currencyId=Integer.parseInt(cel.getAttribute("currency"));
                commissions.put(currencyId, Integer.parseInt(cel.getTextContent()));
            }
            result.add(new Provider(id, commissions, getNames(el)));
        }
        return result;
    }

    @Override
    public List<TranslatableObj> getLanguages(){
        return languages;
    }
    @Override
    public List<Provider> getProviders(int currencyId){
        List<Provider> result=new ArrayList<Provider>();
        for(Provider p : providers){
            if(p.hasCurrency(currencyId)){
                result.add(p);
            }
        }
        return result;
    }
    @Override
    public List<Country> getCountries(){
        return countries;
    }
    @Override
    public HashMap<Integer, TranslatableObj> getStrings(){
        return strings;
    }

    @Override
    public Provider getProviderById(int id){
        for(Provider p : providers){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }
    @Override
    public Country getCountryById(int id){
        for(Country c : countries){
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }
    @Override
    public Country getCountryByLangId(int langId){
        for(Country c : countries){
            if(c.getLangId()==langId){
                return c;
            }
        }
        return null;
    }
    @Override
    public TranslatableObj getCurrencyById(int id){
        for(TranslatableObj c : currencies){
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }
}
