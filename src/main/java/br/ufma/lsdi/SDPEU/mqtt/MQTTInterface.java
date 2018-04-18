package br.ufma.lsdi.SDPEU.mqtt;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import br.ufma.lsdi.SDPEU.R;
import br.ufma.lsdi.SDPEU.model.smartobject.SmartObject;

/**
 * Created by makleyston on 10/11/17.
 * Laboratório de Sistemas Distribuídos e Inteligentes - LSDi
 */

public class MQTTInterface {

    //Pattern Singleton
    public static MQTTInterface instance = null;

    MqttClient client;

    //Topics
    public final static String SMART_OBJECT_DISCOVERY           = "smart_object_discovery";
    public final static String SMART_OBJECT_POST                = "smart_object_read";
    public final static String RECEIVED_DATA_OF_SMART_OBJECT    = "smart_object";
    public final static String SMART_OBJECT_DISCONNECT          = "smart_object_disconnected";
    public final static String QUERY_RESULT                     = "query_result";
    public final static String QUERY                            = "query";
    public final static String PORTABLE_DEVICE_POST             = "portable_device_receiver";
    public final static String TEST                             = "test";
    public final static String PORTABLE_DEVICE_ALIVE            = "alive_portable_device";
    public final static String GET_PARAMS_IDTV_APPLICATION      = "get_system_params";
    public final static String RECEIVED_TV_APPLICATION_PARAMETERS = "result_system_params";
    public final static String PUBLISH_SMART_OBJECTS            = "smart_object_request";
    public final static String ALL                              = "#";

    public final static String[] DEFAULT_TOPICS_MASTER = {RECEIVED_DATA_OF_SMART_OBJECT, QUERY, RECEIVED_TV_APPLICATION_PARAMETERS, TEST, PUBLISH_SMART_OBJECTS};
    public final static String[] DEFAULT_TOPICS_SLAVE = {RECEIVED_TV_APPLICATION_PARAMETERS, TEST, PUBLISH_SMART_OBJECTS};

    private SharedPreferences sharedPref;

    private String host = "";
    private final String TAG = "MQTT**";

    private String tmpDir = System.getProperty("java.io.tmpdir");
    private MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

    private Context context = null;

    private Gson gson = new Gson();

    private String formatTopic(String topic){
        String newTopic = topic;
        if(!topic.substring(0, 0).equals("/")){
            newTopic = "/"+topic;
        }
        if(topic.substring(topic.length()-1, topic.length()-1).equals("/")){
            newTopic = newTopic.substring(0, newTopic.length()-2);
        }
        return newTopic;
    }

    private MQTTInterface(){}

    public static MQTTInterface getInstance(Context context) {
        if(instance == null)
            instance = new MQTTInterface();
        if (context != null) {
            instance.setContext(context);
        }
        return instance;
    }

    private void setContext(Context context){
        this.context = context;
        sharedPref = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * Publish message to the default host
     * @param topic
     * @param message
     */
    public void publish(String topic, String message){
        //Log.i(TAG, ">> Debug: t >> "+topic+", m >> "+message);
        publish(topic, this.host, message);
    }

    /**
     * Publish message to the specific topic and host
     * @param topic
     * @param host
     * @param message
     */
    public void publish(String topic, String host,  String message){

        this.host = sharedPref.getString("ip_master","localhost");
        topic = formatTopic(topic);
        MqttClient client;
        //message = gson.toJson(message);
        //message = message.toLowerCase();

        try {
            client = new MqttClient("tcp://"+this.host+":1883", TAG+"publish", this.dataStore);
            client.connect();
            MqttMessage msg = new MqttMessage(message.getBytes());
            msg.setQos(0);
            client.publish(topic, msg);
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subscribe to a specifics topics
     * @param topic
     * @return Boolean
     * @throws MqttException
     * @throws InterruptedException
     */
    public Boolean subscribe(String[] topic, MqttCallback mqttCallback) throws MqttException, InterruptedException {
        this.host = sharedPref.getString("ip_master","localhost");

        int[] qoss = new int[topic.length];
        for (int i = 0; i < topic.length; i++) {
            topic[i] = formatTopic(topic[i]);
            qoss[i] = 0;
        }

        //Generates a random number to compose customer ID
        int randNumber = (new Random()).nextInt(100)+1;
        client = new MqttClient("tcp://"+this.host+":1883", TAG+"subscribe"+randNumber, this.dataStore);

        if(mqttCallback == null) {
            SubscriberClientCallback callback = new SubscriberClientCallback();
            client.setCallback(callback);
        }else{
            client.setCallback(mqttCallback);
        }
        client.connect();
        client.subscribe(topic, qoss);

        //If client connected
        return client.isConnected();
    }

    /***
     * Unsubscribe to a specifics topics
     * @param topics
     * @return
     */
    public Boolean unsubscribe(String[] topics){
        try {
            client.unsubscribe(topics);
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private class SubscriberClientCallback implements MqttCallback {
        private CountDownLatch m_latch = new CountDownLatch(1);

        void waitFinish() throws InterruptedException {
            m_latch.await();
        }

        @Override
        public void connectionLost(Throwable cause) {
            m_latch.countDown();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }

    public String getFormattedTopic(String topic, SmartObject smartObject) {
        return topic + "/" + smartObject.getEnvironment() + "/" + smartObject.getControllable() + "/" + smartObject.getType();
    }
}
