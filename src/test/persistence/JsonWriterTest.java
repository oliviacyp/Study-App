package persistence;

import model.Schedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    private String testEvent = "Test Event";
    private String testEvent2 = "Test Event2";
    private String testTime = "00:00:00";
    private String testTime2 = "11:11:11";


    @Test
    void testWriterInvalidFile() {
        try {
            Schedule sch = new Schedule("Test Schedule");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySchedule() {
        try {
            Schedule sch = new Schedule("Test Schedule");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(sch);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            sch = reader.read();
            assertEquals("Test Schedule", sch.getName());
            assertEquals(0, sch.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSchedule() {
        try {
            Schedule sch = new Schedule("Test Schedule");
            sch.schedule(testEvent, testTime);
            sch.schedule(testEvent2,testTime2);
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralSchedule.json");
            writer.open();
            writer.write(sch);
            writer.close();
            JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
            sch = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}