package me.matt.grandtheftauto.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateUtil {

    private SimpleDateFormat sdf = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss] ");

    public String getTimeStamp() {
        return sdf.format(new Date().getTime());
    }

}
