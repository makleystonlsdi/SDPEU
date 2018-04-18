package br.ufma.lsdi.SDPEU.model.event;

import br.ufma.lsdi.SDPEU.model.event.sensor.Sensor;

/**
 * Created by makleyston on 28/01/18.
 */

public class EventType extends Event{

    protected Sensor Sensor;

    public Sensor getSensor() {
        return Sensor;
    }

    public void setSensor(Sensor sensor) {
        this.Sensor = sensor;
    }
}
