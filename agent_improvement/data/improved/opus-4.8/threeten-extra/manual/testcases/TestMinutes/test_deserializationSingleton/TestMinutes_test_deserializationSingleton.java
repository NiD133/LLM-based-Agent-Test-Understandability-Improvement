package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that serializing and then deserializing the {@link Minutes#ZERO}
 * singleton yields the exact same instance, rather than a separate copy.
 * <p>
 * {@code Minutes} declares a {@code readResolve} method, so deserialization
 * should resolve back to the canonical {@code ZERO} singleton.
 */
public class TestMinutes_test_deserializationSingleton {

    @Test
    public void deserializingZeroSingleton_returnsSameInstance() throws Exception {
        Minutes original = Minutes.ZERO;

        // Serialize the singleton to a byte array.
        byte[] serializedBytes = serialize(original);

        // Deserialize the bytes back into a Minutes instance.
        Object deserialized = deserialize(serializedBytes);

        // The round-tripped object must be the very same singleton instance.
        assertSame(original, deserialized);
    }

    private static byte[] serialize(Minutes value) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(value);
        }
        return bytes.toByteArray();
    }

    private static Object deserialize(byte[] serializedBytes) throws Exception {
        try (ObjectInputStream in =
                new ObjectInputStream(new ByteArrayInputStream(serializedBytes))) {
            return in.readObject();
        }
    }
}
