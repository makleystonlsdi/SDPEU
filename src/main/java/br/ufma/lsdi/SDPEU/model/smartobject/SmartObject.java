package br.ufma.lsdi.SDPEU.model.smartobject;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by makleyston on 22/01/18.
 */

public class SmartObject implements Cloneable{
    private String Id;
    private String Controllable;
    private String Type;
    private String Environment;
    private Functionality Functionality = new Functionality();
    private HashMap<String, StateValue> State = new HashMap<>();
    private transient String serviceName;

    public HashMap<String, StateValue> getState() {
        return State;
    }

    public void setState(HashMap<String, StateValue> state) {
        this.State = state;
    }

    public void addState(String state, StateValue value){
        this.State.put(state, value);
    }

    public StateValue getStateValue(String state){
        if(this.State.containsKey(state)){
            return this.State.get(state);
        }
        return null;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getControllable() {
        return Controllable;
    }

    public void setControllable(String controllable) {
        this.Controllable = controllable;
    }

    public String getEnvironment() {
        return Environment;
    }

    public void setEnvironment(String environment) {
        this.Environment = environment;
    }

    public Functionality getFunctionality() {
        return Functionality;
    }

    public void setFunctionality(Functionality functionality) {
        this.Functionality = functionality;
    }

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public SmartObject getClone(){
        try {
            return (SmartObject)this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
