package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestYears_test_deserializationSingleton {

    // Years.ZERO is a singleton; readResolve() must return the canonical instance
    // so that deserialized objects are == to the constant, not merely equal.
    @Test
    public void test_deserializationSingleton() throws Exception {
        Years originalSingleton = Years.ZERO;

        // Serialize Years.ZERO to a byte array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream serializer = new ObjectOutputStream(buffer)) {
            serializer.writeObject(originalSingleton);
        }

        // Deserialize and verify the result is the exact same singleton instance
        try (ObjectInputStream deserializer = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            Years deserializedInstance = (Years) deserializer.readObject();
            assertSame(originalSingleton, deserializedInstance);
        }
    }
}
