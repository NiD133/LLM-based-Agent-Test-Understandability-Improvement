package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_deserializationSingleton {

    // Verifies that Seconds.ZERO is a singleton: round-tripping it through
    // Java serialization must return the exact same instance, not a new object.
    @Test
    public void test_deserializationSingleton() throws Exception {
        Seconds original = Seconds.ZERO;

        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(serializedBytes)) {
            out.writeObject(original);
        }

        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            Seconds deserialized = (Seconds) in.readObject();
            assertSame(original, deserialized);
        }
    }
}
