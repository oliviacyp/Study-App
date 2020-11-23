package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

//Represents a schedule including events and and their corresponding times.
public class Schedule extends Observable implements Writable {

    private final String name;
    private final Map<String,Event> schedule;
    private final Map<String,Event> events;

    public Schedule(String name) {
        this.name = name;
        schedule = new LinkedHashMap<>();
        events = new LinkedHashMap<>();
    }

    //MODIFIES: this
    //EFFECTS: adds given event at given time to the schedule, and adds given event to events
    public void schedule(Event event) {
        schedule.put(event.getTime(), event);
        events.put(event.getTime(), event);
    }

    //MODIFIES: this
    //EFFECTS: removes the event mapped to the given time from schedule
    public boolean removeEvent(String timeKey) {
        if (schedule.size() > 0
                && (schedule.get(timeKey) != null || schedule.get(timeKey) != null)) {
            schedule.remove(timeKey);
            events.remove(timeKey);
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: returns the name of the schedule
    public String getName() {
        return name;
    }

    //EFFECTS: returns the schedule
    public Map<String, Event> getSchedule() {
        return schedule;
    }

    //EFFECTS: returns the list of events
    public Map<String, Event> getEvents() {
        return events;
    }

    //EFFECTS: returns the event mapped to the given time in schedule
    public String getEvent(String key) {
        if (events.get(key) != null) {
            return getEvents().get(key).getName();
        }
        return null;
    }

    //EFFECTS: returns the the size of schedule
    public int length() {
        return schedule.size();
    }

    //EFFECTS: returns the list of events
    public Map<String, Event> getEventList() {
        return Collections.unmodifiableMap(events);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("events", eventsToJson());
        return json;
    }

    // EFFECTS: returns lists in this schedule as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();
        Set<String> keys = events.keySet();
        for (String k : keys) {
            Event e = events.get(k);
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}