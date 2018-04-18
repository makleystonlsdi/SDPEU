package br.ufma.lsdi.SDPEU.iotmiddleware;

import android.util.Log;

import java.util.List;

import br.ufma.lsdi.SDPEU.model.event.sensor.Sensor;

/**
 * Created by makleyston on 21/02/18.
 */

public class IoTMiddlewareListenerInstance implements IoTMiddlewareListener {
    private String TAG = "IoTMiddlewareListenerInstance";

    @Override
    public void onReceiveDataFromSmartObjects(String id, String serviceName, Double[] values) {
        Log.i(TAG, ">> onReceiveDataFromSmartObjects");
    }

    @Override
    public void onReceiveSensorsDataOfSmartphoneItself(Sensor sensor) {
        Log.i(TAG, ">> onReceiveSensorsDataOfSmartphoneItself");
    }

    @Override
    public void onSmartObjectDisconnected(String id) {
        Log.i(TAG, ">> onSmartObjectDisconnected");
    }

    @Override
    public void onSmartObjectDiscovered(String id, List<String> services) {
        Log.i(TAG, ">> onSmartObjectDiscovered");
    }
}
