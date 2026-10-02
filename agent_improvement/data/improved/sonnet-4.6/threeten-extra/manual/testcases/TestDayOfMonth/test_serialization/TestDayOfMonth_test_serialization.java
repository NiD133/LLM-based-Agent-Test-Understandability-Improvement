package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for DayOfMonth focusing on serialization round-trip and clock-based factory methods.
 */
public class TestDayOfMonth_test_serialization {

    private static final DayOfMonth TEST = DayOfMonth.of(12);

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    // Verify that DayOfMonth.now() returns the current day in the default time-zone.
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    // Verify that DayOfMonth.now(ZoneId) returns the current day in the given time-zone.
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    // Verify that DayOfMonth survives a full Java serialization round-trip unchanged.
    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        DayOfMonth original = DayOfMonth.of(1);

        // Serialize to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }

        // Deserialize and confirm value equality
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertEquals(original, ois.readObject());
        }
    }
}
