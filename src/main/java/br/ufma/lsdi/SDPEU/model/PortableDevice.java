package br.ufma.lsdi.SDPEU.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import br.ufma.lsdi.SDPEU.R;

/**
 * Created by makleyston on 26/01/18.
 */

public class PortableDevice {

    private transient SharedPreferences sharedPref;

    private String Id;
    private String Type;
    private String Environment;
    private Person Person;
    private List<String> Events = new ArrayList<>();

    public PortableDevice(Context context){
        sharedPref = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        Id = sharedPref.getString("mac","");
        Type = sharedPref.getString("type","");
        Environment = sharedPref.getString("environment","");
        Person = new Person(context);
    }

    public List<String> getEvents() {
        return Events;
    }

    public void setEvents(List<String> events) {
        this.Events = events;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getEnvironment() {
        return Environment;
    }

    public void setEnvironment(String environment) {
        this.Environment = environment;
    }

    public Person getPerson() {
        return Person;
    }

    public void setPerson(Person person) {
        this.Person = person;
    }
}
