package br.ufma.lsdi.SDPEU.model.smartobject;

import java.util.HashMap;

/**
 * Created by makleyston on 06/02/18.
 */

public class ControlFunctionality {

    //tipo do comando e comando
    private HashMap<String, StateValue> Command = new HashMap<>();

    public HashMap<String, StateValue> getCommand() {
        return Command;
    }

    public void setCommand(HashMap<String, StateValue> command) {
        this.Command = command;
    }
}
