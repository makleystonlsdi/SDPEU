package br.ufma.lsdi.SDPEU.model.smartobject;

import java.util.HashMap;

/**
 * Created by makleyston on 06/02/18.
 */

public class Functionality {
    private HashMap<String, ControlFunctionality> ControlFunctionality = new HashMap<>();
    private HashMap<String, NotificationFunctionality> NotificationFunctionality = new HashMap();
    private HashMap<String, QueryFunctionality> QueryFunctionality = new HashMap();

    public HashMap<String, ControlFunctionality> getControlFunctionality() {
        return ControlFunctionality;
    }

    public void setControlFunctionality(HashMap<String, ControlFunctionality> contollfunctionality) {
        this.ControlFunctionality = contollfunctionality;
    }

    public HashMap<String, NotificationFunctionality> getNotificationFunctionality() {
        return NotificationFunctionality;
    }

    public void setNotificationFunctionality(HashMap<String, NotificationFunctionality> notificationFunctionality) {
        this.NotificationFunctionality = notificationFunctionality;
    }

    public HashMap<String, QueryFunctionality> getQueryFunctionality() {
        return QueryFunctionality;
    }

    public void setQueryFunctionality(HashMap<String, QueryFunctionality> queryFunctionality) {
        this.QueryFunctionality = queryFunctionality;
    }
}
