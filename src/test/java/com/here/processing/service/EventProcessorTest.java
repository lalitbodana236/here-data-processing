package com.here.processing.service;

import com.here.processing.dto.Event;
import com.here.processing.dto.Stats;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EventProcessorTest {
    @Test
    void testBasic() {
        List<Event> events = List.of(
                new Event("A", 1, 10),
                new Event("A", 2, 20),
                new Event("A", 1, 30) // duplicate
        );
        
        var result = EventProcessor.aggregate(events.stream(), false);
        
        assertEquals(2, result.get("A").getCount());
        assertEquals(15.0, result.get("A").getAverage());
    }
    
    @Test
    void shouldReturnEmptyForEmptyStream() {
        var result = EventProcessor.aggregate(Stream.empty(), false);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testParallelExecution() {
        List<Event> events = IntStream.range(0, 1000)
                                     .mapToObj(i -> new Event("A", i, 1.0))
                                     .toList();
        
        var result = EventProcessor.aggregate(events.stream(), true);
        
        assertEquals(1000, result.get("A").getCount());
    }
    
    
    @Test
    void shouldHandleMixedScenario() {
        var events = List.of(
                new Event("A", 1, 10),
                new Event("A", 1, 20), // duplicate
                new Event("A", 2, -5), // invalid
                new Event("B", 3, 30),
                new Event("B", 4, 40)
        );
        
        var result = EventProcessor.aggregate(events.stream(), false);
        
        assertEquals(1, result.get("A").getCount());
        assertEquals(2, result.get("B").getCount());
    }
    
}