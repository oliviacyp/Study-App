package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {


    private Event testEvent;

    @BeforeEach
    void runBefore() {
        testEvent = new Event("testEvent");
    }

    @Test
    void testGetName() {
        assertEquals("testEvent", testEvent.getName());
    }

}