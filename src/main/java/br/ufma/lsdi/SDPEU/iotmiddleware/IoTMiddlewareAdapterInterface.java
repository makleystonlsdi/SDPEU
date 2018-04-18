package br.ufma.lsdi.SDPEU.iotmiddleware;

import android.content.Context;

import br.ufma.lsdi.SDPEU.exception.ReceiveDataException;
import br.ufma.lsdi.SDPEU.exception.SendCommandException;
import br.ufma.lsdi.SDPEU.exception.StartStopMiddlewareException;
import br.ufma.lsdi.SDPEU.model.smartobject.SmartObject;

/**
 * Created by makleyston on 10/11/17.
 */

public abstract class IoTMiddlewareAdapterInterface {

    private IoTMiddlewareListener ioTMiddlewareListener = new IoTMiddlewareListenerInstance();

    private Context context;

    /**
     * Send command for actuation on physical ambiente
     * @param mac
     * @param service
     * @param message
     * @return Boolean - True = Sended and False = Not sended
     */
    public abstract void sendCommandForSmartObject(String mac, String service, String message) throws SendCommandException;

    /**
     * Adjusts all services of a Smart Object
     * @param smartObject
     * @return
     */
    public abstract void sendCommandForSmartObject(SmartObject smartObject) throws SendCommandException;

    /**
     * Receive data from a Smart Object
     * @return ArrayList<SmartObject>
     */
    public abstract void receiveDataFromSmartObjects(IoTMiddlewareListener ioTMiddlewareListener) throws ReceiveDataException;

    /**
     * Receive sensors data of smartphone
     */
    //public abstract void enableDeviceReadability(Boolean b);

    /**
     * Notifies when a smart object is disconnected
     */
    public abstract void smartObjectNotificationDisconnected();

    /**
     * Notifies when a smart object is found
     */
    public abstract void smartObjectNotificationDiscovered();

    /**
     * Starts running the middleware
     * @return Boolean
     */
    public abstract void startIotMiddleware() throws StartStopMiddlewareException;

    /**
     * Stops the middleware running
     * @return Boolean
     */
    public abstract void stopIotMiddleware() throws StartStopMiddlewareException;

    public void setListener(IoTMiddlewareListener ioTMiddlewareListener){
        this.ioTMiddlewareListener = ioTMiddlewareListener;
    }

    /***
     *
     * @return IoTMiddlewareListener
     */
    public IoTMiddlewareListener getListener(){
        return this.ioTMiddlewareListener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
