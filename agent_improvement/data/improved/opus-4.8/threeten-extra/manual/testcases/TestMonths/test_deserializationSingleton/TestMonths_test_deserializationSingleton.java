package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that deserializing the {@link Months#ZERO} singleton returns the
 * exact same instance, rather than a distinct equal copy.
 * <p>
 * {@code Months} declares a {@code readResolve} method, so serializing and then
 * deserializing a singleton constant must round-trip back to that same constant.
 */
public class TestMonths_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Months original = Months.ZERO;

        // Serialize the singleton to an in-memory byte buffer.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Deserialize and confirm we get back the very same singleton instance.
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            Object deserialized = in.readObject();
            assertSame(original, deserialized);
        }
    }
}
