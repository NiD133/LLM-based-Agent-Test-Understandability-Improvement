package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_serialization {

    /**
     * Verifies that a MutableClock can round-trip through Java serialization and
     * that the deserialized copy captures a snapshot of the instant at the time
     * of serialization rather than sharing the live state of the original clock.
     */
    @Test
    public void test_serialization() throws Exception {
        MutableClock original = MutableClock.epochUTC();

        // Serialize the clock to a byte array.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }

        // Deserialize and verify the snapshot matches the original state.
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            MutableClock deserialized = (MutableClock) ois.readObject();

            // The deserialized clock must reflect the instant and zone at serialization time.
            assertEquals(original.instant(), deserialized.instant(),
                    "Deserialized clock should have the same instant as the original at serialization time");
            assertEquals(original.getZone(), deserialized.getZone(),
                    "Deserialized clock should have the same time-zone as the original");

            // Equality in MutableClock is identity-based (shared InstantHolder), so a
            // deserialized instance is never equal to the original even when values match.
            assertNotEquals(original, deserialized,
                    "Deserialized clock must not be equal to the original (no shared state)");

            // Advancing the original clock must not affect the deserialized snapshot,
            // confirming the two instances do not share their mutable instant holder.
            original.add(Duration.ofSeconds(1));
            assertNotEquals(original.instant(), deserialized.instant(),
                    "Deserialized clock should not reflect updates made to the original after serialization");
        }
    }
}
