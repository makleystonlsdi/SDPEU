package br.ufma.lsdi.SDPEU.model.event;

/**
 * Created by makleyston on 27/01/18.
 */

public abstract class Event {

    protected String label;
    protected Time Time = new Time();

    public String getLabel() {
        return label;
    }

    public void setLabel(String lable) {
        this.label = lable;
    }

    public Time getTime() {
        return Time;
    }

    public void setTime(Time time) {
        this.Time = time;
    }
}
