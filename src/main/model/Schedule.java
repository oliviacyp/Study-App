package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Represents a schedule including events and and their corresponding times
public class Schedule implements Writable {

    public String name;
    public ArrayList<String> schedule;
    public ArrayList<String> events;
    public ArrayList<String> times;

    public Schedule(String name) {
        this.name = name;
        schedule = new ArrayList<>();
        events = new ArrayList<>();
        times = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds given event at given time to the schedule, and adds given time to list of times
    public void schedule(String event, String time) {
        String scheduling = event + " at " + time;
        schedule.add(scheduling);
        events.add(event);
        times.add(time);
    }

    //REQUIRES: string is in the format "HH:MM:SS"
    //EFFECTS: produces true if the time given is in times
    public boolean isSameTime(String time) {
        for (int i = 0; i < times.size(); i++) {
            if (time.equals(times.get(i))) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns the name of the schedule
    public String getName() {
        return name;
    }

    //EFFECTS: returns the ith element in schedule
    public String get(int i) {
        return schedule.get(i);
    }

    //EFFECTS: returns the ith element in events
    public String getEvent(int i) {
        return events.get(i);
    }

    //EFFECTS: returns the the size of schedule
    public int length() {
        return schedule.size();
    }

    //EFFECTS: returns the list of events
    public List<String> getEventList() {
        return Collections.unmodifiableList(events);
    }

    //EFFECTS: returns the list of events
    public List<String> getTimeList() {
        return Collections.unmodifiableList(times);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("events", listsToJson(events));
        json.put("times", listsToJson(times));
        return json;
    }

     // EFFECTS: returns lists in this schedule as a JSON array
    private JSONArray listsToJson(ArrayList<String> list) {
        JSONArray jsonArray = new JSONArray();
        for (String l : list) {
            jsonArray.put(toJsonObject(l));
        }
        return jsonArray;
    }

     //EFFECTS: returns strings as JSON objects
    public JSONObject toJsonObject(String l) {
        JSONObject json = new JSONObject();
        if (l.contains(":")) {
            json.put("time", l);
        } else {
            json.put("event",l);
        }
        return json;
    }
}