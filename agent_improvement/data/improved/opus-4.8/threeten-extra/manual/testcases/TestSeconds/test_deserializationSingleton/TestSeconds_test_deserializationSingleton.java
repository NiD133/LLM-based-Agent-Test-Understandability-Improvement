package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that deserializing the {@link Seconds#ZERO} singleton yields the
 * exact same instance, confirming that {@code readResolve()} preserves identity.
 */
public class TestSeconds_test_deserializationSingleton {

    @Test
    public void deserializing_ZERO_returns_the_same_singleton_instance() throws Exception {
        Seconds original = Seconds.ZERO;

        // Serialize the singleton to a byte array.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Deserialize and confirm it resolves back to the very same instance.
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            Object deserialized = in.readObject();
            assertSame(original, deserialized);
        }
    }
}
