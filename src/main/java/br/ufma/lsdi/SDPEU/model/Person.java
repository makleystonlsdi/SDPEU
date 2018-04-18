package br.ufma.lsdi.SDPEU.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import br.ufma.lsdi.SDPEU.R;

/**
 * Created by makleyston on 23/01/18.
 */

public class Person {

    private transient SharedPreferences sharedPref;

    private String surname;
    private String additionalName;
    private String address;
    private String affiliation;
    private String alumniOf;
    private String award;
    private String birthDate;
    private String birthPlace;
    private String brand;
    private String children;
    private String colleague;
    private String contactPoint;
    private String deathDate;
    private String deathPlace;
    private String duns;
    private String email;
    private String familyName;
    private String faxNumber;
    private String follows;
    private String funder;
    private String gender;
    private String givenName;
    private String globalLocationNumber;
    private String hasOccupation;
    private String hasOfferCatalog;
    private String hasPOS;
    private String height;
    private String homeLocation;
    private String honorificPrefix;
    private String honorificSuffix;
    private String isicV4;
    private String jobTitle;
    private String knows;
    private String makesOffer;
    private String memberOf;
    private String naics;
    private String nationality;
    private String netWorth;
    private String owns;
    private String parent;
    private String performerIn;
    private String publishingPrinciples;
    private String relatedTo;
    private String seeks;
    private String sibling;
    private String sponsor;
    private String spouse;
    private String taxID;
    private String telephone;
    private String vatID;
    private String weight;
    private String workLocation;
    private String worksFor;

    private transient List<String> attrs = new ArrayList<>();

    public Person(Context context){
         sharedPref = context.getSharedPreferences(
                 context.getString(R.string.app_name), Context.MODE_PRIVATE);

        surname = sharedPref.getString("surname","");
        additionalName = sharedPref.getString("additionalName","");
        address = sharedPref.getString("address","");
        affiliation = sharedPref.getString("affiliation","");
        alumniOf = sharedPref.getString("alumniOf","");
        award = sharedPref.getString("award","");
        birthDate = sharedPref.getString("birthDate","");
        birthPlace = sharedPref.getString("birthPlace","");
        brand = sharedPref.getString("brand","");
        children = sharedPref.getString("children","");
        colleague = sharedPref.getString("colleague","");
        contactPoint = sharedPref.getString("contactPoint","");
        deathDate = sharedPref.getString("deathDate","");
        deathPlace = sharedPref.getString("deathPlace","");
        duns = sharedPref.getString("duns","");
        email = sharedPref.getString("email","");
        familyName = sharedPref.getString("familyName","");
        faxNumber = sharedPref.getString("faxNumber","");
        follows = sharedPref.getString("follows","");
        funder = sharedPref.getString("funder","");
        gender = sharedPref.getString("gender","");
        givenName = sharedPref.getString("givenName","");
        globalLocationNumber = sharedPref.getString("globalLocationNumber","");
        hasOccupation = sharedPref.getString("hasOccupation","");
        hasOfferCatalog = sharedPref.getString("hasOfferCatalog","");
        hasPOS = sharedPref.getString("hasPOS","");
        height = sharedPref.getString("height","");
        homeLocation = sharedPref.getString("homeLocation","");
        honorificPrefix = sharedPref.getString("honorificPrefix","");
        honorificSuffix = sharedPref.getString("honorificSuffix","");
        isicV4 = sharedPref.getString("isicV4","");
        jobTitle = sharedPref.getString("jobTitle","");
        knows = sharedPref.getString("knows","");
        makesOffer = sharedPref.getString("makesOffer","");
        memberOf = sharedPref.getString("memberOf","");
        naics = sharedPref.getString("naics","");
        nationality = sharedPref.getString("nationality","");
        netWorth = sharedPref.getString("netWorth","");
        owns = sharedPref.getString("owns","");
        parent = sharedPref.getString("parent","");
        performerIn = sharedPref.getString("performerIn","");
        publishingPrinciples = sharedPref.getString("publishingPrinciples","");
        relatedTo = sharedPref.getString("relatedTo","");
        seeks = sharedPref.getString("seeks","");
        sibling = sharedPref.getString("sibling","");
        sponsor = sharedPref.getString("sponsor","");
        spouse = sharedPref.getString("spouse","");
        taxID = sharedPref.getString("taxID","");
        telephone = sharedPref.getString("telephone","");
        vatID = sharedPref.getString("vatID","");
        weight = sharedPref.getString("weight","");
        workLocation = sharedPref.getString("workLocation","");
        worksFor = sharedPref.getString("worksFor","");
    }

    public List<String> getAttrs(){
        attrs.add("surname");
        attrs.add("additionalName");
        attrs.add("address");
        attrs.add("affiliation");
        attrs.add("alumniOf");
        attrs.add("award");
        attrs.add("birthDate");
        attrs.add("birthPlace");
        attrs.add("brand");
        attrs.add("children");
        attrs.add("colleague");
        attrs.add("contactPoint");
        attrs.add("deathDate");
        attrs.add("deathPlace");
        attrs.add("duns");
        attrs.add("email");
        attrs.add("familyName");
        attrs.add("faxNumber");
        attrs.add("follows");
        attrs.add("funder");
        attrs.add("gender");
        attrs.add("givenName");
        attrs.add("globalLocationNumber");
        attrs.add("hasOccupation");
        attrs.add("hasOfferCatalog");
        attrs.add("hasPOS");
        attrs.add("height");
        attrs.add("homeLocation");
        attrs.add("honorificPrefix");
        attrs.add("honorificSuffix");
        attrs.add("isicV4");
        attrs.add("jobTitle");
        attrs.add("knows");
        attrs.add("makesOffer");
        attrs.add("memberOf");
        attrs.add("naics");
        attrs.add("nationality");
        attrs.add("netWorth");
        attrs.add("owns");
        attrs.add("parent");
        attrs.add("performerIn");
        attrs.add("publishingPrinciples");
        attrs.add("relatedTo");
        attrs.add("seeks");
        attrs.add("sibling");
        attrs.add("sponsor");
        attrs.add("spouse");
        attrs.add("taxID");
        attrs.add("telephone");
        attrs.add("vatID");
        attrs.add("weight");
        attrs.add("workLocation");
        attrs.add("worksFor");

        return attrs;
    }
}
