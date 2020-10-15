package model;

import java.util.ArrayList;

// Represents a schedule including event times and names
public class Scheduler {

    private static final ArrayList<Event> eventSched = new ArrayList<>();
    private static final ArrayList<String> timeSched = new ArrayList<>();

    //MODIFIES: this
    //EFFECTS: adds created Events to the list eventSched
    public static void addToEvents(Event e) {
        eventSched.add(e);
    }

    //MODIFIES: this
    //EFFECTS: adds created Events to the list TimeSched
    public static void addToTimes(String t) {
        timeSched.add(t);
    }
}