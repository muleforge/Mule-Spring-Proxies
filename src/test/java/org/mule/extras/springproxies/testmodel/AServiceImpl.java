package org.mule.extras.springproxies.testmodel;

public class AServiceImpl implements AService {

    public Integer methodInteger(Integer i) {
        return i;
    }

    public String methodStr(String str) {
        return str + "_1";
    }

    public String methodStr(String str1, String str2) {
        return str1 + "_1_" + str2 + "_2";
    }

    public String methodStr(String str1, String str2, String str3) {
        return str1 + "_1_" + str2 + "_2_" + str3 + "_3";
    }

    public String methodStr2(String str) {
        return str + "_2";
    }

    public String methodStr(Integer i) {
        return i + "_1_iarg";
    }

    public Anything aMethod(Something s) {
        return new Anything(123456.999, "Result from AServiceImpl, recived: '" + s.getValue() + "'");
    }
}
