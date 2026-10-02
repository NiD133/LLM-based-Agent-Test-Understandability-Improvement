package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that deserializing a {@link Hours} constant (a singleton such as
 * {@link Hours#ZERO}) yields the very same instance rather than a distinct copy.
 */
public class TestHours_test_deserializationSingleton {

    @Test
    public void deserializingZeroReturnsTheSameSingletonInstance() throws Exception {
        Hours original = Hours.ZERO;

        // Serialize the singleton to a byte array.
        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(serializedBytes)) {
            out.writeObject(original);
        }

        // Deserialize and confirm we get back the identical singleton (==), not just an equal copy.
        try (ObjectInputStream in =
                new ObjectInputStream(new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            Object deserialized = in.readObject();
            assertSame(original, deserialized);
        }
    }
}
