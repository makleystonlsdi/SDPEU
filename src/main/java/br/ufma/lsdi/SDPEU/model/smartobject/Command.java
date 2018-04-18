package br.ufma.lsdi.SDPEU.model.smartobject;

/**
 * Created by makleyston on 10/02/18.
 */

public class Command {
    private String realCommandName;
    private String commandParamName;

    public String getCommandParamName() {
        return commandParamName;
    }

    public void setCommandParamName(String commandParamName) {
        this.commandParamName = commandParamName;
    }

    public String getRealCommandName() {
        return realCommandName;
    }

    public void setRealCommandName(String realCommandName) {
        this.realCommandName = realCommandName;
    }
}
