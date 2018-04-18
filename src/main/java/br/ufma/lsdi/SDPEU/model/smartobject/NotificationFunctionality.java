package br.ufma.lsdi.SDPEU.model.smartobject;

import java.util.HashMap;

/**
 * Created by makleyston on 06/02/18.
 */

public class NotificationFunctionality {

    private HashMap<String, Notification> Notification = new HashMap<>();

    public HashMap<String, Notification> getNotification() {
        return Notification;
    }

    public void setNotification(HashMap<String, Notification> notification) {
        this.Notification = notification;
    }
}
