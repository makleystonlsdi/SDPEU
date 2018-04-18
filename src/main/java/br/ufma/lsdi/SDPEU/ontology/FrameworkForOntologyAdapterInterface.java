package br.ufma.lsdi.SDPEU.ontology;

import android.content.Context;

import java.io.InputStream;
import java.util.List;

import br.ufma.lsdi.SDPEU.exception.LoadKnowledgeException;
import br.ufma.lsdi.SDPEU.model.smartobject.SmartObject;

/**
 * Created by Makleyston on 22/11/17.
 * Laboratório de Sistemas Distribuídos e Inteligentes - LSDi
 */

public abstract class FrameworkForOntologyAdapterInterface {

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Load knowledge in ontology
     * @throws LoadKnowledgeException
     * @param path - String
     */
    public abstract void loadKnowledge(String path) throws LoadKnowledgeException;

    /**
     * Load knowledge in ontology
     * @throws LoadKnowledgeException
     * @param ontStream
     */
    public abstract void loadKnowledge(InputStream ontStream) throws LoadKnowledgeException;

    /**
     * Execute queries
     * @param query - String
     * @return List<List<String>>
     */
    public abstract List<List> queryExecute(String query);

    /**
     * Execute ask
     * @param ask
     * @return
     */
    public abstract List<List> askExecute(String ask);

    /***
     * Method intended to search for an individual in the knowledge base
     * @param individualName
     * @return An instance of SmartObject with semantic values
     */
    public abstract SmartObject findIndividualName(String individualName);

    public abstract SmartObject smartObjectRead(SmartObject smartObject, String serviceName, Double[] values);

}
