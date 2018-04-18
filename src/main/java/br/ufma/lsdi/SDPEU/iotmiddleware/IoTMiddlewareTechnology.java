package br.ufma.lsdi.SDPEU.iotmiddleware;

import android.content.Context;

import br.ufma.lsdi.SDPEU.exception.ReceiveDataException;
import br.ufma.lsdi.SDPEU.exception.SendCommandException;
import br.ufma.lsdi.SDPEU.exception.StartStopMiddlewareException;
import br.ufma.lsdi.SDPEU.model.smartobject.SmartObject;

/**
 * Created by makleyston on 20/02/18.
 */

public class IoTMiddlewareTechnology extends IoTMiddlewareAdapterInterface {

    private static IoTMiddlewareTechnology instace = null;

    private IoTMiddlewareAdapterInterface ioTMiddlewareAdapterInterface = null;

    private IoTMiddlewareTechnology(){}

    public static IoTMiddlewareTechnology getInstance(Context context){
        if(instace == null){
            instace = new IoTMiddlewareTechnology();
            instace.setContext(context);
            return instace;
        }else{
            return instace;
        }
    }

    public void setListener(IoTMiddlewareListener ioTMiddlewareListener){

    }

    public void setIoTMiddlewareAdapter(IoTMiddlewareAdapterInterface ioTMiddlewareAdapter){
        this.ioTMiddlewareAdapterInterface = ioTMiddlewareAdapter;
    }

    @Override
    public void sendCommandForSmartObject(String mac, String service, String message) throws SendCommandException {
        this.ioTMiddlewareAdapterInterface.sendCommandForSmartObject(mac, service, message);
    }

    @Override
    public void sendCommandForSmartObject(SmartObject smartObject) throws SendCommandException {
        this.ioTMiddlewareAdapterInterface.sendCommandForSmartObject(smartObject);
    }

    @Override
    public void receiveDataFromSmartObjects(IoTMiddlewareListener ioTMiddlewareListener) throws ReceiveDataException {
        this.ioTMiddlewareAdapterInterface.receiveDataFromSmartObjects(ioTMiddlewareListener);
    }

    @Override
    public void smartObjectNotificationDisconnected() {
        this.ioTMiddlewareAdapterInterface.smartObjectNotificationDisconnected();
    }

    @Override
    public void smartObjectNotificationDiscovered() {
        this.ioTMiddlewareAdapterInterface.smartObjectNotificationDiscovered();
    }

    @Override
    public void startIotMiddleware() throws StartStopMiddlewareException {
        this.ioTMiddlewareAdapterInterface.startIotMiddleware();
    }

    @Override
    public void stopIotMiddleware() throws StartStopMiddlewareException {
        this.ioTMiddlewareAdapterInterface.stopIotMiddleware();
    }
}
