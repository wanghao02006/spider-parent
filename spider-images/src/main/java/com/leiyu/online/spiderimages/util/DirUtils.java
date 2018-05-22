package com.leiyu.online.spiderimages.util;

import java.io.File;

public class DirUtils {

    public static final String BASEURL = "/media/images/";

    public static boolean mkdir(String url){
        File dir =  new File(BASEURL + url);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return true;
    }
}
