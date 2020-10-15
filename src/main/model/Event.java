package model;

// Represents an event with a name
public class Event {

    private String name;

    public Event(String name) {
        this.name = name;
    }

    //EFFECTS: returns the Event name
    public String getName() {
        return name;
    }
}



