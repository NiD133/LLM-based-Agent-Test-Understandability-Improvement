package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_serialization {

    @Test
    public void test_serialization() throws Exception {
        UtcInstant original = UtcInstant.ofModifiedJulianDay(2, 3);

        // Serialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }

        // Deserialize and verify round-trip equality
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            UtcInstant deserialized = (UtcInstant) ois.readObject();
            assertEquals(original, deserialized);
        }
    }
}
