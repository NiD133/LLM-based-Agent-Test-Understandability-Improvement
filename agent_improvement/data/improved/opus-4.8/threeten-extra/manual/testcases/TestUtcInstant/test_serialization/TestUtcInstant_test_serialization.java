package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link UtcInstant} can be serialized and deserialized,
 * yielding an instant equal to the original.
 */
public class TestUtcInstant_test_serialization {

    @Test
    public void test_serialization() throws Exception {
        UtcInstant original = UtcInstant.ofModifiedJulianDay(2, 3);

        // Serialize the instant to a byte array.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }

        // Deserialize it back and confirm it equals the original instant.
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            UtcInstant deserialized = (UtcInstant) in.readObject();
            assertEquals(original, deserialized);
        }
    }
}
