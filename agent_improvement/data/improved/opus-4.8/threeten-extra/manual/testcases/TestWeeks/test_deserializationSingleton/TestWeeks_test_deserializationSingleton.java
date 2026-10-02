package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that deserializing the {@link Weeks#ZERO} singleton restores the
 * same instance rather than creating a distinct equal copy. This relies on the
 * {@code readResolve} method in {@link Weeks} re-canonicalising to the cached
 * singleton.
 */
public class TestWeeks_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Weeks original = Weeks.ZERO;

        // Serialize the singleton to a byte array.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Deserialize and confirm we get back the exact same singleton instance.
        try (ObjectInputStream in =
                new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            assertSame(original, in.readObject());
        }
    }
}
