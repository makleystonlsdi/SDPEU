package br.ufma.lsdi.SDPEU.cep.handler;

import android.content.Context;
import android.util.Log;


import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import br.ufma.lsdi.SDPEU.cep.EventListener;
import br.ufma.lsdi.SDPEU.cep.subscribe.MovementSubscriber;
import br.ufma.lsdi.SDPEU.cep.subscribe.StatementSubscriber;
import br.ufma.lsdi.SDPEU.model.event.Event;
import br.ufma.lsdi.SDPEU.model.event.Modality;
import br.ufma.lsdi.SDPEU.model.event.Pointing;
import br.ufma.lsdi.SDPEU.model.event.sensor.AccelerometerSensor;
import br.ufma.lsdi.SDPEU.model.event.sensor.Sensor;

/**
 * Created by makleyston on 29/01/18.
 */

public class EventHandler{

    public static EventHandler instance = null;
    private Context context = null;

    public static EventHandler getInstance(Context context){
        if(instance == null){
            instance = new EventHandler();
        }
        instance.setContext(context);
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private EventHandler(){}

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement movementStatement;
    final private String TAG = "EventHandler";

    //private StatementSubscriber movementSubscriber = new MovementSubscriber();

    public void handle(Sensor evt){
        if(evt instanceof AccelerometerSensor) {
            AccelerometerSensor evta = new AccelerometerSensor();
            evta.setX(((AccelerometerSensor) evt).getX());
            evta.setY(((AccelerometerSensor) evt).getY());
            evta.setZ(((AccelerometerSensor) evt).getZ());
            epService.getEPRuntime().sendEvent(evta);
        }
    }

    public void initService() {
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("AcceleromenterSensor", AccelerometerSensor.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(cepConfig);
        Log.i(TAG, ">> "+TAG+" inicializado");
    }

    public void removeAllStatement(){
        epService.getEPAdministrator().destroyAllStatements();
    }

    public void addCEPEvent(Modality modality, EventListener eventListener){
        for (Event e: modality.getEvent()) {
            if(e instanceof Pointing) {
                Pointing pointing = (Pointing) e;
                MovementSubscriber movementSubscriber = new MovementSubscriber(context, pointing, eventListener);
                createAccelerometerCheckExpression(movementSubscriber);
            }
        }
    }

    private void createAccelerometerCheckExpression(StatementSubscriber movementSubscriber) {
        if(movementSubscriber == null){
            Log.i(TAG, ">> movementSubscriber é nulo");
        }
        movementStatement = epService.getEPAdministrator().createEPL(movementSubscriber.getStatement());
        movementStatement.setSubscriber(movementSubscriber);
    }
}
