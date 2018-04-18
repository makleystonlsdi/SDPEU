package br.ufma.lsdi.SDPEU.model.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makleyston on 27/01/18.
 */

public abstract class Modality {
    private String label;
    private List<Event> Event = new ArrayList<>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Event> getEvent() {
        return Event;
    }

    public void setEvent(List<Event> event) {
        this.Event = event;
    }

    public void addEvent(Event evt){
        this.Event.add(evt);
    }

}
