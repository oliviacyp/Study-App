package ui;

import model.Event;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Scanner;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class App {
    private static final String JSON_STORE = "./data/schedule.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final Scanner input = new Scanner(System.in);
    private static Schedule schedule;


    // EFFECTS: runs the scheduler application
    public App() throws FileNotFoundException {
        schedule = new Schedule("Schedule1");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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

            if (command.contentEquals("EXIT")) {
                contRunning = false;
                exitScreen();
            } else {
                chooseCommands(command);
            }
        }
    }

    public void chooseCommands(String command) {
        if (!processCommands(command) && !processMoreCommands(command)) {
            System.out.println("Please enter a valid command!");
        }
    }

    //EFFECTS: processes user command
    // code from TellerApp
    public boolean processCommands(String command) {
        switch (command) {
            case "S":
                createEvent();
                optionsScreen();
                return true;
            case "?":
                helpScreen();
                return true;
            case "LIST":
                list();
                return true;
            case "VIEW":
                view();
                return true;
            default:
                return false;
        }
    }

    //EFFECTS: processes more user commands
    public boolean processMoreCommands(String command) {
        switch (command) {
            case "FIND":
                find();
                return true;
            case "SAVE":
                save();
                return true;
            case "LOAD":
                load();
                return true;
            default:
                return false;
        }
    }

    // EFFECTS: displays greetings to user
    public static void welcomeScreen() {
        System.out.println("Hello,");
        System.out.println("Create your schedule for today!");
    }

    //MODIFIES: this, eventSched, timeSched
    //EFFECTS: prompts user to input time and name of an event,
    // then prints the event name at the time given
    public static void createEvent() {
        System.out.println("Enter the event name:");
        String name = input.nextLine();
        Runnable runEvent = new TimerTask() {
            @Override
            public void run() {
                System.out.println(name);
            }
        };
//      code learned from:
//      https://stackoverflow.com/questions/4927856/how-to-calculate-time-difference-in-java
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println("What time is it right now? (HH:MM:SS)");
        LocalTime timeNow = LocalTime.parse(input.nextLine(), formatter);

        System.out.println("What time will the event be? (HH:MM:SS)");
        String scheduledTime = input.nextLine();
        LocalTime eventTime = LocalTime.parse(scheduledTime, formatter);

        long timeDiff = findTimeDiff(timeNow, eventTime);

//      code learned from:
//      https://mkyong.com/java/java-scheduledexecutorservice-examples/
        ScheduledExecutorService eventAlarm = Executors.newScheduledThreadPool(1);
        eventAlarm.schedule(runEvent, timeDiff, TimeUnit.SECONDS);

        Event e = new Event(name, scheduledTime);
        schedule.schedule(e);

        System.out.println("Okay, your event has been set!");
    }

    //EFFECTS: finds time difference between current time and event time
    public static long findTimeDiff(Temporal timeNow, Temporal eventTime) {
        long timeDiff;
        long findingDiff = Duration.between(timeNow, eventTime).getSeconds();
        if (findingDiff < 0) {
            timeDiff = findingDiff + 3600;
        } else {
            timeDiff = findingDiff;
        }
        return timeDiff;
    }

    //EFFECTS: displays menu of options to user
    public static void optionsScreen() {
        System.out.println("Enter S to schedule an event, or");
        System.out.println("Enter ? for more options.");
    }

    //EFFECTS: displays command options to user
    public static void helpScreen() {
        System.out.println("ENTER:");
        System.out.println("LIST to see how many events are scheduled for today.");
        System.out.println("VIEW to view today's schedule.");
        System.out.println("FIND to find the event scheduled at any time.");
        System.out.println("SAVE to save the schedule you've made.");
        System.out.println("LOAD to load previously saved schedules.");
        System.out.println("EXIT to exit.");
    }

    //EFFECTS: displays final schedule and closes program
    public void exitScreen() {
        System.out.println("Your schedule for today is set!");
        System.out.println(" ");
        view();
        System.out.println(" ");
        System.out.println("Have a nice day!");
    }

    //EFFECTS: displays number of events scheduled
    public void list() {
        System.out.println("You have " + schedule.length() + " events today.");
    }

    //EFFECTS: displays the full schedule
    public void view() {
        if (schedule.getEvents() != null) {
            Set<String> keys = schedule.getEvents().keySet();
            System.out.println("~-~-~-Schedule-~-~-~");
            for (String k : keys) {
                if (k != null) {
                    System.out.println((schedule.getEvent(k) + " at " + k));
                    System.out.println("--------------------");
                }
            }
        } else {
            System.out.println("You have no events scheduled for today!");
        }
    }

    //EFFECTS: returns the event scheduled at the given time,
    // informs user if nothing is scheduled there
    public void find() {
        System.out.println("Please enter a time (HH:MM:SS).");
        String foundEvent = schedule.getEvent(input.nextLine());
        if (foundEvent != null) {
            System.out.println(foundEvent + " is scheduled for this time.");
        } else {
            System.out.println("There is nothing scheduled for this time.");
        }
    }

    // EFFECTS: saves the schedule to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            System.out.println("Saved " + schedule.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads schedule from file
    private void load() {
        try {
            schedule = jsonReader.read();
            System.out.println("Loaded " + schedule.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}