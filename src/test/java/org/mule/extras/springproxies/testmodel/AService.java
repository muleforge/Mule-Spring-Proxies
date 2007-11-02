package org.mule.extras.springproxies.testmodel;

public interface AService {
    public Integer methodInteger(Integer i);

    public String methodStr(String str);

    public String methodStr(String str1, String str2);

    public String methodStr(String str1, String str2, String str3);

    public String methodStr2(String str);

    public Anything aMethod(Something s);
}
