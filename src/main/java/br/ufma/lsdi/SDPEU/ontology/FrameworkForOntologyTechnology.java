package br.ufma.lsdi.SDPEU.ontology;

import android.content.Context;

import java.io.InputStream;
import java.util.List;

import br.ufma.lsdi.SDPEU.exception.LoadKnowledgeException;
import br.ufma.lsdi.SDPEU.model.smartobject.SmartObject;

/**
 * Created by makleyston on 20/02/18.
 */

public class FrameworkForOntologyTechnology extends FrameworkForOntologyAdapterInterface {

    private static FrameworkForOntologyTechnology instance = null;

    private Context context;

    private FrameworkForOntologyTechnology(Context context){
        this.context = context;
    }

    public static FrameworkForOntologyTechnology getInstance(Context context){
        if(instance == null){
            instance = new FrameworkForOntologyTechnology(context);
            return instance;
        }else{
            return instance;
        }
    }

    FrameworkForOntologyAdapterInterface frameworkForOntologyAdapterInterface = null;

    public void setFrameworkForOntologyAdapterTechology(FrameworkForOntologyAdapterInterface frameworkForOntologyAdapterTechology){
        this.frameworkForOntologyAdapterInterface = frameworkForOntologyAdapterTechology;
        this.frameworkForOntologyAdapterInterface.setContext(this.context);
    }

    @Override
    public void loadKnowledge(String path) throws LoadKnowledgeException {
        this.frameworkForOntologyAdapterInterface.loadKnowledge(path);
    }

    @Override
    public void loadKnowledge(InputStream ontStream) throws LoadKnowledgeException {
        this.frameworkForOntologyAdapterInterface.loadKnowledge(ontStream);
    }

    @Override
    public List<List> queryExecute(String query) {
        return this.frameworkForOntologyAdapterInterface.queryExecute(query);
    }

    @Override
    public List<List> askExecute(String ask) {
        return this.frameworkForOntologyAdapterInterface.askExecute(ask);
    }

    @Override
    public SmartObject findIndividualName(String individualName) {
        return this.frameworkForOntologyAdapterInterface.findIndividualName(individualName);
    }

    @Override
    public SmartObject smartObjectRead(SmartObject smartObject, String serviceName, Double[] values) {
        return this.smartObjectRead(smartObject, serviceName, values);
    }
}
