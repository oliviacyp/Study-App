package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {


    private Event testEvent;
    private String name;

    @BeforeEach
    void runBefore() {
        testEvent = new Event("testEvent");
    }

    @Test
    void testDisplayName() {
//        assertEquals("testName", testEvent.displayName());
    }

    @Test
    void testGetName() {
        assertEquals("testName", testEvent.getName());
    }

}