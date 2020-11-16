package persistence;

import model.Event;
import model.Schedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    private Event testEvent = new Event("Test Event","00:00:00");
    private Event testEvent2 = new Event("Test Event2","11:11:11");

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

//    @Test
//    void testWriterGeneralSchedule() {
//        try {
//            Schedule sch = new Schedule("Test Schedule");
//            sch.schedule(testEvent);
//            sch.schedule(testEvent2);
//            JsonWriter writer = new JsonWriter("./data/testReaderGeneralSchedule.json");
//            writer.open();
//            writer.write(sch);
//            writer.close();
//            JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
//            sch = reader.read();
//            assertEquals("Test Schedule", sch.getName());
//            List<Event> events = sch.getEventList();
//            assertEquals(2, events.size());
//            assertEquals("Test Event", events.get(0));
//            assertEquals("Test Event2", events.get(1));
//
//        } catch (IOException e) {
//            fail("Exception should not have been thrown.");
//        }
//    }
}