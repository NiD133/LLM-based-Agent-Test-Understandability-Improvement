package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock} survives Java serialization while keeping
 * its documented "no shared updates" contract: a deserialized clock is an
 * independent copy, not a live view of the original.
 */
public class TestMutableClock_test_serialization {

    @Test
    public void test_serialization() throws Exception {
        MutableClock original = MutableClock.epochUTC();

        MutableClock deserialized = serializeAndDeserialize(original);

        // The copy starts out with the same observable state as the original.
        assertEquals(original.instant(), deserialized.instant());
        assertEquals(original.getZone(), deserialized.getZone());

        // The copy is a distinct, independent clock: it is not "equal" to the
        // original because the two do not share updates.
        assertNotEquals(deserialized, original);

        // Advancing the original must not affect the deserialized copy.
        original.add(Duration.ofSeconds(1));
        assertNotEquals(deserialized.instant(), original.instant());
    }

    /**
     * Writes the given clock to an in-memory byte stream and reads it back,
     * returning the reconstructed clock.
     */
    private static MutableClock serializeAndDeserialize(MutableClock clock) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(clock);
        }
        try (ObjectInputStream in =
                new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            return (MutableClock) in.readObject();
        }
    }
}
