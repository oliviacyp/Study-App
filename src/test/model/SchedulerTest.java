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

}