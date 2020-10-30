package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private Schedule schedule;
    private ArrayList<String> testSched;
    private ArrayList<String> testEvents;
    private ArrayList<String> testTimes;
    private String event = "event";
    private String event2 = "event2";
    private String time = "00:00:00";
    private String time2 = "11:11:11";

    @BeforeEach
    public void setup() {
        schedule = new Schedule("Test Schedule");
        testSched = new ArrayList<>();
        testEvents = new ArrayList<>();
        testTimes = new ArrayList<>();
        schedule.schedule(event,time);
    }

    @Test
    public void testSchedule() {
        schedule.schedule(event2,time2);
        assertEquals(testSched.get(0),"event at 00:00:00");
        assertEquals(testSched.get(1),"event2 at 11:11:11");
        assertEquals(testEvents.get(0),event);
        assertEquals(testEvents.get(1),event2);
        assertEquals(testTimes.get(0),time);
        assertEquals(testTimes.get(1),time2);

    }

    @Test
    public void testIsSameTime() {
        assertTrue(schedule.isSameTime("00:00:00"));
        assertFalse(schedule.isSameTime("10:00:00"));
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
        assertEquals(schedule.getEvent(0),event);
    }

    @Test
    public void testLength() {
        assertEquals(schedule.length(), 1);
        schedule.schedule(event2,time2);
        assertEquals(schedule.length(), 2);
    }
}
