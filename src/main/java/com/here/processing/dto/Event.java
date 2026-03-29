package com.here.processing.dto;

public record Event(String id,
                    long timestamp, // epoch millis
                    double value) {
}
