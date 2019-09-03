package com.sajagjain.sajagjain.majorproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 16-12-2017.
 */

public class Singleton {
    private static List<String> list=new ArrayList<>();

    public static Singleton getInstanceSingleton(){
        return new Singleton();
    }

    public static List<String> getList() {
        return list;
    }

    public static void setList(List<String> list) {
        Singleton.list = list;
    }
}
