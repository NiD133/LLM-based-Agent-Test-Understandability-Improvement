package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_deserializationSingleton {

    /**
     * Verifies that deserializing a serialized Minutes.ZERO returns the canonical singleton
     * instance, not a new object. This relies on the readResolve() mechanism in Minutes.
     */
    @Test
    public void test_deserializationSingleton() throws Exception {
        Minutes original = Minutes.ZERO;

        // Serialize Minutes.ZERO to a byte array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream serializer = new ObjectOutputStream(buffer)) {
            serializer.writeObject(original);
        }

        // Deserialize and confirm the same singleton instance is returned
        try (ObjectInputStream deserializer = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            Minutes deserialized = (Minutes) deserializer.readObject();
            assertSame(original, deserialized);
        }
    }
}
