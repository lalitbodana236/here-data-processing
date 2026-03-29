package com.here.processing.service;

import com.here.processing.dto.Event;
import com.here.processing.dto.EventKey;
import com.here.processing.dto.Stats;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class EventProcessor {
    
    public static Map<String, Stats> aggregate(Stream<Event> events, boolean parallel) {
        Stream<Event> stream = parallel ? events.parallel() : events;
        
        ConcurrentHashMap<String, Stats> result = new ConcurrentHashMap<>();
        Set<EventKey> seen = ConcurrentHashMap.newKeySet();
        
        stream
                .filter(EventProcessor::isValid)
                .forEach(event -> {
                    if (isDuplicate(event, seen)) return;
                    
                    result.compute(event.id(), (key, stats) -> {
                        if (stats == null) stats = new Stats();
                        stats.add(event);
                        return stats;
                    });
                });

        
        return result;
    }
    
    private static boolean isDuplicate(Event event, Set<EventKey> seen) {
        return !seen.add(new EventKey(event.id(), event.timestamp()));
    }
    
    private static boolean isValid(Event e) {
        return e != null
                       && e.id() != null
                       && !Double.isNaN(e.value())
                       && e.value() >= 0;
    }
}
