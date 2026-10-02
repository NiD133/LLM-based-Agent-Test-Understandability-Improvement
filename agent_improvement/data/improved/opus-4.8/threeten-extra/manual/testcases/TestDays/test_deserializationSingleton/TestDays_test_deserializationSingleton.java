package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestDays_test_deserializationSingleton {

    /**
     * Serializing the {@link Days#ZERO} singleton and reading it back should
     * return the very same instance, because {@code Days} resolves singletons
     * during deserialization (via {@code readResolve}).
     */
    @Test
    public void test_deserializationSingleton() throws Exception {
        Days original = Days.ZERO;

        // Serialize the singleton to a byte array.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Deserialize it and verify the same singleton instance is returned.
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            Object deserialized = in.readObject();
            assertSame(original, deserialized);
        }
    }
}
