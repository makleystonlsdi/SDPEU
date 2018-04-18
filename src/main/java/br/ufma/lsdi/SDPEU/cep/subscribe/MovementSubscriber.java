package br.ufma.lsdi.SDPEU.cep.subscribe;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import br.ufma.lsdi.SDPEU.cep.EventListener;
import br.ufma.lsdi.SDPEU.model.event.Pointing;
import br.ufma.lsdi.SDPEU.model.event.sensor.AccelerometerSensor;

import static java.lang.Thread.sleep;

/**
 * Created by makleyston on 29/01/18.
 */

public class MovementSubscriber implements StatementSubscriber  {

    private double x;
    private double y;
    private double z;
    final private double interval = 3;
    private String label = "&";
    private Context context;
    private Toast toast;
    final private Boolean[] flag = new Boolean[1];
    private String TAG = "Movement**";
    private EventListener eventListener;

    public MovementSubscriber(Context context, Pointing pointing, EventListener eventListener) {
        this.x = ((AccelerometerSensor) pointing.getSensor()).getX();
        this.y = ((AccelerometerSensor) pointing.getSensor()).getY();
        this.z = ((AccelerometerSensor) pointing.getSensor()).getZ();
        this.label = pointing.getLabel();
        this.context = context;
        this.flag[0] = false;
        this.eventListener = eventListener;
    }

    @Override
    public String getStatement() {
        String str = " select * from AcceleromenterSensor(x between "+(x-interval)+" and "+(x+interval)
                +", y between "+(y - interval)+" and "+(y + interval)
                +", z between "+(z - interval)+" and "+(z + interval)+") ";
        return str;
    }

    public void update(AccelerometerSensor eventMap){

        if(!flag[0]) {
            Log.i(TAG, ">> Movement: "+label);
            eventListener.onEvent(label);
            flag[0] = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(1000);
                        flag[0] = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        flag[0] = false;
                    }
                }
            }).start();
        }
    }
}
