package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchedulerTest {

    //TODO: test scheduler and printSchedule? & test multiples

    ArrayList<Event> testEventSched = new ArrayList<>();
    ArrayList<String> testTimeSched = new ArrayList<>();
    Event testEvent;
    Event testEvent2;
    Event testEvent3;


    @BeforeEach
    void setup() {
        ArrayList<Event> testEventSched = new ArrayList<>();
        ArrayList<String> testTimeSched = new ArrayList<>();
        Event testEvent = new Event("testEvent");
        Event testEvent2 = new Event("testEvent2");
        Event testEvent3 = new Event("testEvent3");
    }

    @Test
    void testAddToEvents() {
        testEventSched.add(testEvent);
        assertEquals(testEventSched.size(), 1);
        assertEquals(testEventSched.get(0), testEvent);
    }

    @Test
    void testAddToTimes() {
        testTimeSched.add("12:00:00");
        assertEquals(testTimeSched.size(), 1);
        assertEquals(testTimeSched.get(0), "12:00:00");
    }

    @Test
    void testPrintSchedule() {
        testEventSched.add(testEvent);
        testTimeSched.add("12:00:00");
//        assertEquals(Scheduler.printSchedule(), "testEvent at 12:00:00");

    }

    @Test
    void testHowMany() {
        testEventSched.add(testEvent);
        testEventSched.add(testEvent2);
        testEventSched.add(testEvent3);
//        assertEquals(Scheduler.howMany(), "You have 3 events today.");

    }

    @Test
    void testWhatNow() {
        testEventSched.add(testEvent);
        testTimeSched.add("12:00:00");
//        assertEquals(Scheduler.whatNow("12:00:00"), "testEvent is scheduled for this time.");
    }
}