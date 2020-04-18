package com.epam.wfh.manager.persistent;

import com.microsoft.graph.models.extensions.DateTimeTimeZone;
import com.microsoft.graph.models.extensions.Event;
import com.microsoft.graph.models.extensions.Recipient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connections {


    private static Map<DateTimeTimeZone, List<Recipient>> dailyCalenderRecords=new HashMap<>();

    public static void addConnection(DateTimeTimeZone time , Recipient with){

        if(dailyCalenderRecords.containsKey(time)){

            if(dailyCalenderRecords.get(time).stream().noneMatch(
                    e ->e.emailAddress!=with.emailAddress
            )){
                dailyCalenderRecords.get(time).add(with);

            }
        }else{
            List<Recipient> names=new ArrayList<>();
            names.add(with);
            dailyCalenderRecords.put(time,names);

        }

    }

    public static void dispalyConnections() {

        dailyCalenderRecords.entrySet().forEach(
                e -> dailyCalenderRecords.get(e).forEach(t -> System.out.println(e + "  with >> " + t.emailAddress))
        );


    }



}
