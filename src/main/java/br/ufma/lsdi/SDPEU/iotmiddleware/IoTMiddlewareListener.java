package br.ufma.lsdi.SDPEU.iotmiddleware;

import java.util.List;

import br.ufma.lsdi.SDPEU.model.event.sensor.Sensor;

/**
 * Created by Makleyston on 23/11/17.
 * Laboratório de Sistemas Distribuídos e Inteligentes - LSDi
 */

public interface IoTMiddlewareListener {

    /**
     * Called when a smart object reads a value of its sensors
     * @param id - String
     * @param serviceName - String
     * @param values - SensorData
     */
    void onReceiveDataFromSmartObjects(String id, String serviceName, Double[] values);

    /**
     * Called when this smartphone reads a value of its sensors
     */
    void onReceiveSensorsDataOfSmartphoneItself(Sensor sensor);

    /**
     * Called when a smart object is disconnected
     * @param id - String
     */
    void onSmartObjectDisconnected(String id);


    /**
     * Called when a smart object is discovered
     * @param id
     * @param services
     */
    void onSmartObjectDiscovered(String id, List<String> services);
}
