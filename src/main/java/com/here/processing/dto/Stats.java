package com.here.processing.dto;

public class Stats {
    private long count = 0;
    private long minTimestamp = Long.MAX_VALUE;
    private long maxTimestamp = Long.MIN_VALUE;
    private double sum = 0;
    
    public void add(Event e) {
        count++;
        minTimestamp = Math.min(minTimestamp, e.timestamp());
        maxTimestamp = Math.max(maxTimestamp, e.timestamp());
        sum += e.value();
    }
    
    public long getCount() {
        return count;
    }
    
    public long getMinTimestamp() {
        return count == 0 ? 0 : minTimestamp;
    }
    
    public long getMaxTimestamp() {
        return count == 0 ? 0 : maxTimestamp;
    }
    
    public double getAverage() {
        return count == 0 ? 0 : sum / count;
    }
}
