package br.ufma.lsdi.SDPEU;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.eclipse.moquette.server.Server;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.ufma.lsdi.SDPEU.cep.handler.EventHandler;
import br.ufma.lsdi.SDPEU.exception.ReceiveDataException;
import br.ufma.lsdi.SDPEU.exception.StartStopMiddlewareException;
import br.ufma.lsdi.SDPEU.iotmiddleware.IoTMiddlewareAdapterInterface;
import br.ufma.lsdi.SDPEU.iotmiddleware.IoTMiddlewareListener;
import br.ufma.lsdi.SDPEU.iotmiddleware.IoTMiddlewareTechnology;
import br.ufma.lsdi.SDPEU.model.epg.EPG;
import br.ufma.lsdi.SDPEU.model.PortableDevice;
import br.ufma.lsdi.SDPEU.model.event.sensor.AccelerometerSensor;
import br.ufma.lsdi.SDPEU.model.smartobject.ControlFunctionality;
import br.ufma.lsdi.SDPEU.model.smartobject.SmartObject;
import br.ufma.lsdi.SDPEU.model.smartobject.StateValue;
import br.ufma.lsdi.SDPEU.mqtt.MQTTInterface;
import br.ufma.lsdi.SDPEU.ontology.FrameworkForOntologyAdapterInterface;
import br.ufma.lsdi.SDPEU.ontology.FrameworkForOntologyTechnology;

import static java.lang.Thread.sleep;

/**
 * Created by makleyston on 20/02/18.
 */

public class SDPEU implements SDPEUInterface{

    private Context context = null;
    private final String TAG = getClass().getName();
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;
    private Server broker;
    private MQTTInterface mqttInterface = null;
    private IoTMiddlewareTechnology ioTMiddlewareTechnology = null;
    private FrameworkForOntologyTechnology frameworkForOntologyTechnology = null;
    private Gson gson = new Gson();
    private EPG paramsIDTVApplication = null;
    private SmartObjectListManager smartObjectListManager = new SmartObjectListManager();
    private Util util = null;
    private EventHandler eventHandler;

    private long portable_device_warning_time = 5000;

    Thread threadAlive;
    final Boolean[] start = new Boolean[1]; //used in alive

    public static SDPEU instance = null;

    private SDPEU(){};

    public static SDPEU getInstance(Context context){
        if(instance == null){
            instance = new SDPEU(context);
        }
        return instance;
    }

    private SDPEU(Context context) {
        this.context = context;

        this.mqttInterface = MQTTInterface.getInstance(context);
        Log.d(TAG, ">> MQTTInterface successfully instantiated!");

        this.ioTMiddlewareTechnology = IoTMiddlewareTechnology.getInstance(context);
        Log.d(TAG, ">> IoTMiddlewareTechnology successfully instantiated!");

        this.frameworkForOntologyTechnology = FrameworkForOntologyTechnology.getInstance(context);
        Log.d(TAG, ">> FrameworkForOntologyTechnology successfully instantiated!");

        this.broker = new Server();
        Log.d(TAG, ">> MicroBroker successfully instantiated!");

        this.util = Util.getInstance(context);

        this.sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    @Override
    public EPG getParamsIDTVApplication() {
        return paramsIDTVApplication;
    }

    @Override
    public void setFrameworkForOntologyAdapterTechnology(FrameworkForOntologyAdapterInterface frameworkForOntologyAdapterTechnology) {
        this.frameworkForOntologyTechnology.setFrameworkForOntologyAdapterTechology(frameworkForOntologyAdapterTechnology);
    }

    @Override
    public void setIoTMiddlewareTechnology(IoTMiddlewareAdapterInterface ioTMiddlewareAdapterInterface) {
        this.ioTMiddlewareTechnology.setIoTMiddlewareAdapter(ioTMiddlewareAdapterInterface);
    }

    @Override
    public IoTMiddlewareTechnology getIoTMiddlewareTechnology() {
        return ioTMiddlewareTechnology;
    }

    @Override
    public FrameworkForOntologyTechnology getFrameworkForOntologyTechnology() {
        return this.frameworkForOntologyTechnology;
    }

    @Override
    public String getMyIp() {
        return util.getIP(context);
    }

    @Override
    public void setIPOfMasterDevice(String ip) {
        if (!ip.trim().equals("")) {
            editor.putString(context.getResources().getString(R.string.ip_master), ip);
            editor.commit();
            Log.d(TAG, " setIPOfMasterDevice >> IP inserted successfully! " + ip);
        } else {
            Log.d(TAG, " setIPOfMasterDevice >> Invalid IP!");
        }
    }

    @Override
    public String getIPOfMasterDevice() {
        String ip = sharedPreferences.getString(context.getString(R.string.ip_master), "");
        Log.d(TAG, "getIPOfMasterDevice >> " + ip);
        return ip;
    }

    @Override
    public Boolean startSDPEU() {
        String ip_master = getIPOfMasterDevice();
        try {
            if (ip_master.equals("")) {
                broker.startServer();
                Log.d(TAG, ">> Micro Broker initialized successfully!");

                this.ioTMiddlewareTechnology.startIotMiddleware();
                Log.d(TAG, ">> IoTMiddlewareTechnology initialized successfully!");

                final IoTMiddlewareListener ioTInterfaceListener = new IoTMiddlewareListener() {
                    @Override
                    public void onReceiveDataFromSmartObjects(String id, String serviceName, Double[] values) {
                        smartObjectListManager.smartObjectRead(id, serviceName, values);
                    }

                    @Override
                    public void onReceiveSensorsDataOfSmartphoneItself(br.ufma.lsdi.SDPEU.model.event.sensor.Sensor sensor) {
                        if (sensor instanceof AccelerometerSensor) {
                            AccelerometerSensor acc = new AccelerometerSensor();
                            acc.setX(((AccelerometerSensor) sensor).getX());
                            acc.setY(((AccelerometerSensor) sensor).getY());
                            acc.setZ(((AccelerometerSensor) sensor).getZ());
                            eventHandler.handle(acc);
                        }
                    }

                    @Override
                    public void onSmartObjectDisconnected(String id) {
                        smartObjectListManager.smartObjectDisconnected(id);
                    }

                    @Override
                    public void onSmartObjectDiscovered(String id, List<String> services) {
                        smartObjectListManager.smartObjectDiscovered(id, services);
                    }
                };

                this.ioTMiddlewareTechnology.receiveDataFromSmartObjects(ioTInterfaceListener);
                Log.d(TAG, ">> Receiving data from Smart Objects");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (StartStopMiddlewareException e) {
            e.printStackTrace();
            return false;
        } catch (ReceiveDataException e) {
            e.printStackTrace();
            return false;
        }

        final String m = ip_master;
        MqttCallback mqttCallback = new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String top, MqttMessage message) throws Exception {
                Log.d(TAG, "Message received >> Topic: " + top);
                if (top == null) return;

                String topic = top.split("/")[1];
                switch (topic) {
                    case MQTTInterface.PUBLISH_SMART_OBJECTS:
                        Log.i(TAG, ">> The TV application requests all Smart Objects");
                        for (SmartObject so :
                                smartObjectListManager.getSmartObjectList()) {
                            String obj = gson.toJson(so);
                            mqttInterface.publish(mqttInterface.getFormattedTopic(MQTTInterface.SMART_OBJECT_POST, so), obj);
                            Log.d(TAG, "Smart Object: " + so.getType() + ", id: " + so.getId() + " was published in MicroBroker");
                        }
                        break;
                    case MQTTInterface.RECEIVED_TV_APPLICATION_PARAMETERS:
                        paramsIDTVApplication = gson.fromJson(message.toString(), EPG.class);
                        Log.d(TAG, ">> Data from the TV application successfully received!");
                        break;
                    case MQTTInterface.RECEIVED_DATA_OF_SMART_OBJECT:
                        SmartObject smartObject = null;
                        try {
                            smartObject = gson.fromJson(message.toString(), SmartObject.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }

                        Collection<ControlFunctionality> controlFunctionalities = smartObject.getFunctionality().getControlFunctionality().values();
                        for (ControlFunctionality control : controlFunctionalities) {
                            HashMap<String, StateValue> commands = control.getCommand();
                            for (Map.Entry<String, StateValue> entry : commands.entrySet()) {
                                String key = entry.getKey();
                                StateValue stateValue = entry.getValue();

                                if (stateValue.getRealStateValue().equals(".")) continue;

                                for (SmartObject so :
                                        smartObjectListManager.getSmartObjectList()) {
                                    if ((so.getId().trim().equals(smartObject.getId().trim()))
                                            && (so.getType().trim().equals(smartObject.getType().trim()))) {
                                        Log.d(TAG, ">> Operation command received from the TV application: " + so.getType() + " will be triggered!");
                                        ioTMiddlewareTechnology.sendCommandForSmartObject(smartObject.getId(), so.getServiceName(), stateValue.getRealStateValue());
                                    }
                                }
                            }
                        }
                        break;
                    case MQTTInterface.TEST:
                        Log.d(TAG, "Test >> " + message.toString());
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        };

        try {
            if (ip_master.equals("")) { //é pq eu sou o master
                mqttInterface.subscribe(MQTTInterface.DEFAULT_TOPICS_MASTER, mqttCallback);
                Log.d(TAG, ">> Micro Broker connected!");
            } else { //é pq o master é outro
                mqttInterface.subscribe(MQTTInterface.DEFAULT_TOPICS_SLAVE, mqttCallback);
                Log.d(TAG, ">> Connected to master Micro Broker!");
            }
            PortableDevice portableDevice = new PortableDevice(this.context);
            mqttInterface.publish(MQTTInterface.GET_PARAMS_IDTV_APPLICATION, gson.toJson(portableDevice.getId()));
            aliveDevice();
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        start[0] = true;

        Log.d(TAG, ">> Receiving data from internal sensors.");
        return true;
    }

    private void aliveDevice() {
        threadAlive = new Thread(new Runnable() {
            @Override
            public void run() {
                PortableDevice device = new PortableDevice(context);
                String j = gson.toJson(device);
                while (start[0]) {
                    try {
                        mqttInterface.publish(MQTTInterface.PORTABLE_DEVICE_ALIVE + "/" + device.getEnvironment(), j);
                        sleep(portable_device_warning_time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadAlive.start();
    }

    @Override
    public Boolean stopSDPEU() {
        String ip_master = sharedPreferences.getString(context.getString(R.string.ip_master), "");
        if (ip_master.equals("")) {
            broker.stopServer();
        } else {
            mqttInterface.unsubscribe(mqttInterface.DEFAULT_TOPICS_SLAVE);
        }
        Log.d(TAG, ">> Micro Broker closed successfully!");
        if (threadAlive != null) {
            threadAlive.interrupt();
        }

        try {
            ioTMiddlewareTechnology.stopIotMiddleware();
            Log.d(TAG, ">> IoTMiddlewareTechnology closed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        start[0] = false;

        Log.d(TAG, ">> SDPEU closed successfully!");

        return true;
    }

    private class SmartObjectListManager {

        //**Smart Objects presentes no ambiente
        private List<SmartObject> smartObjectList = new ArrayList<>();

        public void smartObjectDiscovered(String id, List<String> services) {
            for (SmartObject so :
                    smartObjectList) {
                if (so.getId().equals(id.trim())) return;
            }
            for (String s : services) {
                SmartObject smartObject = frameworkForOntologyTechnology.findIndividualName(s);
                smartObject.setId(id);
                smartObject.setServiceName(s);
                smartObjectList.add(smartObject);
                mqttInterface.publish(mqttInterface.getFormattedTopic(MQTTInterface.SMART_OBJECT_DISCOVERY, smartObject), smartObject.toString());
                Log.i(TAG, ">> The Smart Object with ID " + smartObject.getId() + " and Type: " + smartObject.getType() + " has been successfully stored!");
            }
        }

        public void smartObjectDisconnected(String id) {
            Iterator<SmartObject> it = smartObjectList.iterator();
            while (it.hasNext()) {
                SmartObject so = it.next();
                if (so.getId().trim().equals(id.trim())) {
                    Log.i(TAG, "Item removed.");
                    it.remove();
                    mqttInterface.publish(mqttInterface.getFormattedTopic(MQTTInterface.SMART_OBJECT_DISCONNECT, so), id);
                }
            }
            Log.i(TAG, ">> Successfully removed!");
        }

        public List<SmartObject> getSmartObjectList() {
            return this.smartObjectList;
        }

        public void smartObjectRead(String id, String serviceName, Double[] values) {
            try {
                SmartObject smartObjectRead = frameworkForOntologyTechnology.findIndividualName(serviceName);
                for (SmartObject so :
                        this.smartObjectList) {
                    if ((so.getId().equals(id) && (so.getServiceName().equals(serviceName)) && (so.getType().equals(smartObjectRead.getType())))) {
                        SmartObject soRead = frameworkForOntologyTechnology.smartObjectRead(so, serviceName, values);
                        String obj = "";
                        obj = gson.toJson(soRead);
                        Log.i(TAG, " Smart Object Json >> " + obj);
                        mqttInterface.publish(mqttInterface.getFormattedTopic(MQTTInterface.SMART_OBJECT_POST, soRead), obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
