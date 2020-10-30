package persistence;

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
        addLists(sch, jsonObject);
        return sch;
    }

    // MODIFIES: sch
    // EFFECTS: parses lists from JSON object and adds them to schedule
    private void addLists(Schedule sch, JSONObject jsonObject) {
        JSONArray events = jsonObject.getJSONArray("events");
        JSONArray times = jsonObject.getJSONArray("times");
        for (int i = 0; i < events.length(); i++) {
            JSONObject nextEvent = (JSONObject) events.get(i);
            JSONObject nextTime = (JSONObject) times.get(i);
            String event = nextEvent.getString("event");
            String time = nextTime.getString("time");
            sch.schedule(event, time);
        }
    }
}