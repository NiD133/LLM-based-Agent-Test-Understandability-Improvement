package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TaiInstant} can be serialized and then deserialized
 * back into an equal instance (a Java serialization round-trip).
 */
public class TestTaiInstant_test_serialization {

    @Test
    public void test_serialization() throws Exception {
        TaiInstant original = TaiInstant.ofTaiSeconds(2, 3);

        // Serialize the instant into an in-memory byte array.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Deserialize it and confirm the result equals the original instant.
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            assertEquals(original, in.readObject());
        }
    }
}
