package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents an event with a name and a scheduled time.
public class Event implements Writable {

    private final String name;
    private final String time;

    public Event(String name, String time) {
        this.name = name;
        this.time = time;
    }

    //EFFECTS: returns the name of the event
    public String getName() {
        return name;
    }

    //EFFECTS: returns the ith element in events
    public String getTime() {
        return time;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("names", name);
        json.put("times", time);
        return json;
    }
}