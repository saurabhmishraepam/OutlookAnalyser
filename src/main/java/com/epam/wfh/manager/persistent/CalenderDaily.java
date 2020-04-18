package com.epam.wfh.manager.persistent;

import com.microsoft.graph.models.extensions.DateTimeTimeZone;
import com.microsoft.graph.models.extensions.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalenderDaily {

    private static Map<DateTimeTimeZone, List<Event>> dailyCalenderRecords=new HashMap<>();

    public static void addEvent(DateTimeTimeZone start, Event currentEvent){
        if(dailyCalenderRecords.containsKey(start)){
          if(  dailyCalenderRecords.get(start).stream().noneMatch(
                    e->e.start!=currentEvent.start
                            && e.organizer!=currentEvent.organizer
                            && e.subject!=currentEvent.subject

            )){
              Connections.addConnection(start, currentEvent.organizer);
              dailyCalenderRecords.get(start).add(currentEvent);
          }
        }else{
            List<Event> events=new ArrayList<>();
            events.add(currentEvent);
            dailyCalenderRecords.put(start, events);
        }
    }

public static void displayCalender(){

        dailyCalenderRecords.entrySet().forEach(e -> {

            dailyCalenderRecords.get(e).forEach(t -> System.out.println(e+"  T "+t));

        });
}
}
