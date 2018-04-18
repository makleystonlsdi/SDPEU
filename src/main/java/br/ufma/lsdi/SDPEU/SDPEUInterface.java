package br.ufma.lsdi.SDPEU;

import br.ufma.lsdi.SDPEU.iotmiddleware.IoTMiddlewareAdapterInterface;
import br.ufma.lsdi.SDPEU.iotmiddleware.IoTMiddlewareTechnology;
import br.ufma.lsdi.SDPEU.model.epg.EPG;
import br.ufma.lsdi.SDPEU.ontology.FrameworkForOntologyAdapterInterface;
import br.ufma.lsdi.SDPEU.ontology.FrameworkForOntologyTechnology;

/**
 * Created by Makleyston on 17/04/18.
 * Laboratório de Sistemas Distribuídos e Inteligentes - LSDi
 */

public interface SDPEUInterface {

    /***
     * Method for initializing the SDPEU component.
     * This method will initialize all other components related to it,
     * such as micro broker, IoT middleware, ontology framework and engine CEP.
     * @return TRUE for successful boot and FALSE for boot failure.
     */
    public Boolean startSDPEU();

    /***
     * Method for terminating the SEU and all components in which it relates
     * @return TRUE for success on closing, and FALSE for closing failure.
     */
    public Boolean stopSDPEU();

    /***
     * Method that requests the TV application data on it.
     * @return Returns an object of type EPG
     */
    public EPG getParamsIDTVApplication();

    /***
     * Method that inserts an implementation of a framework for ontologies in SDPEU.
     * @param frameworkForOntologyAdapterTechnology
     */
    public void setFrameworkForOntologyAdapterTechnology(FrameworkForOntologyAdapterInterface frameworkForOntologyAdapterTechnology);

    /***
     * Method that inserts an implementation of an IoT middleware into the SDPEU.
     * @param ioTMiddlewareAdapterInterface
     */
    public void setIoTMiddlewareTechnology(IoTMiddlewareAdapterInterface ioTMiddlewareAdapterInterface);

    /***
     *
     * @return Returns the IoTMiddlewareTechnology
     */
    public IoTMiddlewareTechnology getIoTMiddlewareTechnology();

    /***
     *
     * @return Returns the FrameworkForOntologyTechnology
     */
    public FrameworkForOntologyTechnology getFrameworkForOntologyTechnology();

    /***
     * Method for identifying and returning the IP address of the device that uses it
     * @return A String containing the following IP address formed: xxx.xxx.xxx.xxx
     */
    public String getMyIp();

    /***
     * Method to allow insertion of the IP address of the master device
     * @param ip
     */
    public void setIPOfMasterDevice(String ip);

    /***
     * Method for identifying and returning the IP address of the master device
     * @return A String containing the following IP address formed: xxx.xxx.xxx.xxx
     */
    public String getIPOfMasterDevice();

}
