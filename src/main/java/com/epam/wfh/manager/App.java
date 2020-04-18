package com.epam.wfh.manager;

import com.epam.wfh.manager.persistent.CalenderDaily;
import com.epam.wfh.manager.persistent.Connections;
import com.microsoft.graph.models.extensions.DateTimeTimeZone;
import com.microsoft.graph.models.extensions.Event;
import com.microsoft.graph.models.extensions.User;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
// Load OAuth settings
        final Properties oAuthProperties = new Properties();
       /* try {
            oAuthProperties.load(App.class.getResourceAsStream("oAuth.properties"));
        } catch (IOException e) {
            System.out.println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
            return;
        }*/

        final String appId = "";
        final String[] appScopes = "User.Read,Calendars.Read".split(",");
        Authentication.initialize(appId);
        final String accessToken = Authentication.getUserAccessToken(appScopes);


        User user = Graph.getUser(accessToken);
        System.out.println("Welcome " + user.displayName);
        System.out.println();
        Scanner input = new Scanner(System.in);

        int choice = -1;

        while (choice != 0) {
            System.out.println("Please choose one of the following options:");
            System.out.println("0. Exit");
            System.out.println("1. Display access token");
            System.out.println("2. List calendar events");

            try {
                choice = input.nextInt();
            } catch (InputMismatchException ex) {
                // Skip over non-integer input
                input.nextLine();
            }

            // Process user choice
            switch(choice) {
                case 1:
                    System.out.println("Access token: " + accessToken);
                    break;
                case 2:
                    listCalendarEvents(accessToken);
                    break;
                case 3:
                   // CalenderDaily.displayCalender();
                    Connections.dispalyConnections();
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }

        input.close();
    }


    private static void listCalendarEvents(String accessToken) {
        // Get the user's events
        List<Event> events = Graph.getEvents(accessToken);

        System.out.println("Events:");
        for (Event event : events) {
            System.out.println("Subject: " + event.subject);
            System.out.println("  Organizer: " + event.organizer.emailAddress.name);
            System.out.println(event.start);
            System.out.println(">>> "+event.isCancelled);

            System.out.println("  Start: " + formatDateTimeTimeZone(event.start));
            System.out.println("  End: " + formatDateTimeTimeZone(event.end));
            CalenderDaily.addEvent(event.start, event);
        }

        System.out.println();
    }
    private static String formatDateTimeTimeZone(DateTimeTimeZone date) {
        LocalDateTime dateTime = LocalDateTime.parse(date.dateTime);

        return dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) + " (" + date.timeZone + ")";
    }
}
