package ui;

import model.Event;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class App {

    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Event> eventSched = new ArrayList<>();
    private static final ArrayList<String> timeSched = new ArrayList<>();


    // EFFECTS: runs the scheduler application
    public App() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // code from TellerApp
    private void runApp() {
        boolean contRunning = true;

        welcomeScreen();
        optionsScreen();

        while (contRunning) {
            String command = input.nextLine();
            command = command.toUpperCase();

            if (command.equals("N") || (command.contentEquals("EXIT"))) {
                contRunning = false;
                exitScreen();
            } else {
                processCommands(command);
            }
        }
    }

    //EFFECTS: processes user command
    // code from TellerApp
    public void processCommands(String command) {
        switch (command) {
            case "Y":
                createEvent();
                optionsScreen();
                break;
            case "?":
                helpScreen();
                break;
            case "LIST":
                list();
                break;
            case "VIEW":
                view();
                break;
            case "FIND":
                find();
                break;
            default:
                System.out.println("Please enter a valid command!");
                break;
        }
    }

    // EFFECTS: displays greetings to user
    public static void welcomeScreen() {
        System.out.println("Hello,");
        System.out.println("Create your schedule for today!");
    }

    //MODIFIES: this, eventSched, timeSched
    //EFFECTS: prompts user to input time and name of an event, then prints the event name at the time given
    public static void createEvent() {
        System.out.println("Enter the event name:");
        String name = input.nextLine();
        Event event = new Event(name);
        Runnable runEvent = new TimerTask() {
            @Override
            public void run() {
                System.out.println(name);
            }
        };
//      code learned from https://stackoverflow.com/questions/4927856/how-to-calculate-time-difference-in-java
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println("What time is it right now? (HH:MM:SS)");
        LocalTime timeNow = LocalTime.parse(input.nextLine(), formatter);

        System.out.println("What time will the event be? (HH:MM:SS)");
        String scheduledTime = input.nextLine();
        LocalTime eventTime = LocalTime.parse(scheduledTime, formatter);

        long timeDiff = java.time.Duration.between(timeNow, eventTime).getSeconds();

//      code learned from https://mkyong.com/java/java-scheduledexecutorservice-examples/
        ScheduledExecutorService eventAlarm = Executors.newScheduledThreadPool(1);
        eventAlarm.schedule(runEvent, timeDiff, TimeUnit.SECONDS);

        eventSched.add(event);
        timeSched.add(scheduledTime);

        System.out.println("Okay, your event has been set!");
    }

    //EFFECTS: displays menu of options to user
    public static void optionsScreen() {
        System.out.println("Enter Y to schedule an event, or");
        System.out.println("Enter ? for more options.");
    }

    //EFFECTS: displays command options to user
    public static void helpScreen() {
        System.out.println("ENTER:");
        System.out.println("LIST to see how many events are scheduled for today.");
        System.out.println("VIEW to view today's schedule.");
        System.out.println("FIND to find the event scheduled at any time.");
        System.out.println("EXIT to exit.");
    }

    public void exitScreen() {
        System.out.println("Your schedule for today is set!");
        view();
        System.out.println("Have a nice day!");
    }

    public void list() {
        System.out.println("You have " + eventSched.size() + " events today.");
    }

    public void view() {
        System.out.println("~-~-~-Schedule-~-~-~");
        for (int i = 0; i < eventSched.size(); i++) {
            Event e = eventSched.get(i);
            if (e != null) {
                System.out.print(e.getName() + " at " + timeSched.get(i) + "\n");
            } else {
                System.out.println("You have no events scheduled for today!");
            }
        }
    }

    public void find() {
        System.out.println("Please enter a time (HH:MM:SS).");
        for (int i = 0; i < timeSched.size(); i++) {
            if (input.nextLine().equals(timeSched.get(i))) {
                System.out.println(eventSched.get(i).getName() + " is scheduled for this time.");
            } else {
                System.out.println("Nothing is scheduled at this time.");
            }
        }
    }
}