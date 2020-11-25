package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event event;

    @BeforeEach
    public void setup() {
        event = new Event("event", "00:00:00");
    }

    @Test
    public void testConstructor() {
        event = new Event("event", "00:00:00");
        assertEquals(event.getName(),"event");
        assertEquals(event.getTime(),"00:00:00");
    }

    @Test
    public void testGetName() {
        assertEquals(event.getName(),"event");
    }

    @Test
    public void testGetTime() {
        assertEquals(event.getTime(),"00:00:00");
    }
}