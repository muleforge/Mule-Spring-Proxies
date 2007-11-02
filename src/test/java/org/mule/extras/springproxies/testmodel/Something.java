package org.mule.extras.springproxies.testmodel;

public class Something {
    private String value;

    public Something(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
