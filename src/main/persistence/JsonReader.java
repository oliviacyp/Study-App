package persistence;

import model.Event;
import model.Schedule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads schedule from JSON data stored in file
// From JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads schedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses schedule from JSON object and returns it
    private Schedule parseSchedule(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Schedule sch = new Schedule(name);
        addEvents(sch, jsonObject);
        return sch;
    }

    // MODIFIES: wr
    // EFFECTS: parses events from JSON object and adds them to schedule
    private void addEvents(Schedule sch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(sch, nextEvent);
        }
    }

    // MODIFIES: sch
    // EFFECTS: parses event from JSON object and adds it to schedule
    private void addEvent(Schedule sch, JSONObject jsonObject) {
        String name = jsonObject.getString("names");
        String time = jsonObject.getString("times");
        Event event = new Event(name, time);
        sch.schedule(event);
    }
}