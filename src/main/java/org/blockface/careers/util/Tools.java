package org.blockface.careers.util;

public class Tools {

    public static boolean randBoolean(double p)
    {
        if(p>1) p=p/100;
        return (Math.random() < p);
    }

}
