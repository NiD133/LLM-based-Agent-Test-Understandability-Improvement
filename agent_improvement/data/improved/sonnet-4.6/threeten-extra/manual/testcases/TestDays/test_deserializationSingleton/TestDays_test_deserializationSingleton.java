package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestDays_test_deserializationSingleton {

    // Days.ZERO is a singleton constant. The Days class implements readResolve()
    // so that Java deserialization returns the canonical singleton instance
    // rather than creating a new object. This test verifies that invariant:
    // after a round-trip through serialization, the result must be the exact
    // same object reference as Days.ZERO.
    @Test
    public void test_deserializationSingleton() throws Exception {
        Days original = Days.ZERO;

        // Serialize Days.ZERO to a byte array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(original);
        }

        // Deserialize and confirm that the singleton identity is preserved
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            Days deserialized = (Days) in.readObject();
            assertSame(original, deserialized);
        }
    }
}
