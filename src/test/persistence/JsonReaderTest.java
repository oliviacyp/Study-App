package persistence;

import model.Schedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Schedule sch = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            Schedule sch = reader.read();
            assertEquals("Test Schedule", sch.getName());
            assertEquals(0, sch.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            Schedule sch = reader.read();
            assertEquals("Test Schedule", sch.getName());
            List<String> events = sch.getEventList();
            List<String> times = sch.getTimeList();
            assertEquals(2, events.size());
            assertEquals(2, times.size());
            assertEquals("Test Event", events.get(0));
            assertEquals("Test Event2", events.get(1));
            assertEquals("00:00:00", times.get(0));
            assertEquals("11:11:11", times.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
