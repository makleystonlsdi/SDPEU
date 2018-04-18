package br.ufma.lsdi.SDPEU.model.smartobject;

import java.util.HashMap;

/**
 * Created by makleyston on 10/02/18.
 */

public class Notification {
    private String notificationName;
    private HashMap<String, String> notificationParamName = new HashMap<>();
    private String unitOfMeasure;

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public HashMap<String, String> getNotificationParamName() {
        return notificationParamName;
    }

    public void setNotificationParamName(HashMap<String, String> notificationParamName) {
        this.notificationParamName = notificationParamName;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
