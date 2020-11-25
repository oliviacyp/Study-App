package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

/**
* Represents a schedule of events and and their corresponding times.
*/

public class Schedule extends Observable implements Writable {

    private final String name;
    private final Map<String,Event> events;

    public Schedule(String name) {
        this.name = name;
        events = new LinkedHashMap<>();
    }

    //MODIFIES: this
    //EFFECTS: adds given event at given time to events
    public void schedule(Event event) {
        events.put(event.getTime(), event);
    }

    //MODIFIES: this
    //EFFECTS: removes the event mapped to the given time from events in the schedule
    public boolean removeEvent(String timeKey) {
        if (events.size() > 0 && events.get(timeKey) != null) {
            events.remove(timeKey);
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: returns the event mapped to the given time
    public String getEvent(String key) {
        if (events.get(key) != null) {
            return getEvents().get(key).getName();
        }
        return null;
    }

    //EFFECTS: returns the list of events
    public Map<String, Event> getEvents() {
        return events;
    }


    //EFFECTS: returns the name of the schedule
    public String getName() {
        return name;
    }

    //EFFECTS: returns the the size of events
    public int length() {
        return events.size();
    }

    //EFFECTS: returns the list of events
    public Map<String, Event> getEventList() {
        return Collections.unmodifiableMap(events);
    }

    @Override
    //EFFECTS: creates a JSONObject and puts the fields of the schedule
    //with the corresponding keys
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("events", eventsToJson());
        return json;
    }

    // EFFECTS: returns events in this schedule as a JSON array
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