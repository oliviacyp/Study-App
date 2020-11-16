package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private Schedule schedule;
    private ArrayList<String> testEvents;
    private Event e1 = new Event("event", "00:00:00");
    private Event e2 = new Event("event2", "11:11:11");

    @BeforeEach
    public void setup() {
        schedule = new Schedule("Test Schedule");
        testEvents = new ArrayList<>();
        schedule.schedule(e1);
    }

//    @Test
//    public void testSchedule() {
//        schedule.schedule(e2);
//        assertEquals(schedule.get(0),"event at 00:00:00");
//        assertEquals(schedule.get(1),"event2 at 11:11:11");
//        assertEquals(testEvents.get(0),e1);
//        assertEquals(testEvents.get(1), e2);
//    }

    @Test
    public void testIsSameTime() {
        assertEquals(schedule.isSameTime("00:00:00"),0);
        assertEquals(schedule.isSameTime("10:00:00"),-1);
    }

    @Test
    public void testGetName() {
        assertEquals(schedule.getName(),"Test Schedule");
    }

    @Test
    public void testGet() {
        assertEquals(schedule.get(0),"event at 00:00:00");
    }

    @Test
    public void testGetEvent() {
        assertTrue(schedule.getEvent(0).equals("event"));
    }

    @Test
    public void testLength() {
        assertEquals(schedule.length(), 1);
        schedule.schedule(e2);
        assertEquals(schedule.length(), 2);
    }
}
