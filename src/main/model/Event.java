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

    //EFFECTS: returns the time of the event
    public String getTime() {
        return time;
    }

    @Override
    //EFFECTS: creates a JSONObject and puts the fields of each event
    //with the corresponding keys
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("names", name);
        json.put("times", time);
        return json;
    }
}