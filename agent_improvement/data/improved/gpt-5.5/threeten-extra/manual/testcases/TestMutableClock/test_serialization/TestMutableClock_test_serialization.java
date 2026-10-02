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

    @Test
    public void test_serialization() throws Exception {
        MutableClock original = MutableClock.epochUTC();

        MutableClock deserialized = serializeAndDeserialize(original);

        assertEquals(original.instant(), deserialized.instant());
        assertEquals(original.getZone(), deserialized.getZone());
        assertNotEquals(deserialized, original);

        original.add(Duration.ofSeconds(1));

        assertNotEquals(deserialized.instant(), original.instant());
    }

    private static MutableClock serializeAndDeserialize(MutableClock test) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            return (MutableClock) ois.readObject();
        }
    }
}
