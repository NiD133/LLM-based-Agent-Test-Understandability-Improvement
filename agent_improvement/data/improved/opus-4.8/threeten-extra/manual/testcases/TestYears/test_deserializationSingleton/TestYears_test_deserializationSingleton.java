package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestYears_test_deserializationSingleton {

    /**
     * {@code Years} declares constants such as {@link Years#ZERO} and uses
     * {@code readResolve} to preserve them across serialization. This test
     * confirms that serializing and then deserializing the singleton yields
     * the very same instance (reference equality), not just an equal copy.
     */
    @Test
    public void test_deserializationSingleton() throws Exception {
        Years original = Years.ZERO;

        // Serialize the singleton to a byte array.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Deserialize it back and verify it resolves to the same singleton instance.
        try (ObjectInputStream in =
                new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            Object deserialized = in.readObject();
            assertSame(original, deserialized);
        }
    }
}
