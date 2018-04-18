package br.ufma.lsdi.SDPEU.model.smartobject;

/**
 * Created by makleyston on 06/02/18.
 */

public class StateValue {
    private String realStateValue = ".";
    private String unitOfMeasure = ".";

    public StateValue() {

    }

    public StateValue(String realStateValue, String unit) {
        this.realStateValue = realStateValue;
        this.unitOfMeasure = unit;
    }

    public String getRealStateValue() {
        return realStateValue;
    }

    public void setRealStateValue(String realStateValue) {
        this.realStateValue = realStateValue;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {

        switch (unitOfMeasure) {
            case "percent":
                this.unitOfMeasure = "%";
                break;
            case "sound":
                this.unitOfMeasure = "B(LC)";
                break;
            case "degree_celsius":
                this.unitOfMeasure = "C";
                break;
            default:
                this.unitOfMeasure = unitOfMeasure;
                break;
        }
    }
}
