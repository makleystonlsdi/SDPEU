package br.ufma.lsdi.SDPEU.model;

/**
 * Created by makleyston on 23/11/17.
 */

public class SensorData {
    private String dataString;
    private Double[] dataDouble;
    private Integer[] dataInteger;
    private Boolean dataBoolean;

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public Double[] getDataDouble() {
        return dataDouble;
    }

    public void setDataDouble(Double[] dataDouble) {
        this.dataDouble = dataDouble;
    }

    public Integer[] getDataInteger() {
        return dataInteger;
    }

    public void setDataInteger(Integer[] dataInteger) {
        this.dataInteger = dataInteger;
    }

    public Boolean getDataBoolean() {
        return dataBoolean;
    }

    public void setDataBoolean(Boolean dataBoolean) {
        this.dataBoolean = dataBoolean;
    }
}
