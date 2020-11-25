package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private Schedule schedule;
    private final Event e1 = new Event("event", "00:00:00");
    private final Event e2 = new Event("event2", "11:11:11");

    @BeforeEach
    public void setup() {
        schedule = new Schedule("Test Schedule");
        schedule.schedule(e1);
    }

    @Test
    public void testSchedule() {
        schedule.schedule(e2);
        assertEquals(schedule.getEvents().get("00:00:00"),e1);
        assertEquals(schedule.getEvents().get("11:11:11"),e2);
        assertEquals(schedule.getEvent("00:00:00"),"event");
        assertEquals(schedule.getEvent("11:11:11"),"event2");
        assertEquals(schedule.length(), 2);
    }

    @Test
    public void testRemoveEventInSchedule() {
        schedule.removeEvent("00:00:00");
        assertFalse(schedule.getEvents().containsKey("00:00:00"));
        assertNull(schedule.getEvents().get("00:00:00"));
        assertEquals(schedule.length(), 0);
    }

    @Test
    public void testRemoveEventNotInSchedule() {
        schedule.removeEvent("11:11:11");
        assertFalse(schedule.getEvents().containsKey("11:11:11"));
        assertNull(schedule.getEvents().get("11:11:11"));
        assertEquals(schedule.getEvents().get("00:00:00"),e1);
        assertEquals(schedule.getEvent("00:00:00"),"event");
        assertEquals(schedule.length(), 1);
    }

    @Test
    public void testRemoveEventInScheduleTwice() {
        schedule.removeEvent("00:00:00");
        assertFalse(schedule.getEvents().containsKey("00:00:00"));
        assertNull(schedule.getEvents().get("00:00:00"));
        assertEquals(schedule.length(), 0);
        schedule.removeEvent("00:00:00");
        assertFalse(schedule.getEvents().containsKey("00:00:00"));
        assertNull(schedule.getEvents().get("00:00:00"));
        assertEquals(schedule.length(), 0);
    }

    @Test
    public void testRemoveMultipleEventsInSchedule() {
        schedule.schedule(e2);
        schedule.removeEvent("00:00:00");
        schedule.removeEvent("11:11:11");
        assertFalse(schedule.getEvents().containsKey("00:00:00"));
        assertFalse(schedule.getEvents().containsKey("11:11:11"));
        assertNull(schedule.getEvents().get("00:00:00"));
        assertNull(schedule.getEvents().get("11:11:11"));
        assertEquals(schedule.length(), 0);
    }

    @Test
    public void testRemoveOneEventInSchedule() {
        schedule.schedule(e2);
        schedule.removeEvent("00:00:00");
        assertFalse(schedule.getEvents().containsKey("00:00:00"));
        assertNull(schedule.getEvents().get("00:00:00"));
        assertEquals(schedule.getEvents().get("11:11:11"),e2);
        assertEquals(schedule.getEvent("11:11:11"),"event2");
        assertEquals(schedule.length(), 1);
    }

    @Test
    public void testGetEvent() {
        assertEquals(schedule.getEvent("00:00:00"),"event");
    }

    @Test
    public void testGetNullEvent() {
        assertNull(schedule.getEvent("10:00:00"));
    }

    @Test
    public void testGetMultipleEvents() {
        schedule.schedule(e2);
        assertEquals(schedule.getEvent("00:00:00"),"event");
        assertEquals(schedule.getEvent("11:11:11"),"event2");
    }

    @Test
    public void testGetEvents() {
        assertEquals(schedule.getEvents(),schedule.getEvents());
    }

    @Test
    public void testGetName() {
        assertEquals(schedule.getName(),"Test Schedule");
    }

    @Test
    public void testLength() {
        assertEquals(schedule.length(), 1);
        schedule.schedule(e2);
        assertEquals(schedule.length(), 2);
    }
}