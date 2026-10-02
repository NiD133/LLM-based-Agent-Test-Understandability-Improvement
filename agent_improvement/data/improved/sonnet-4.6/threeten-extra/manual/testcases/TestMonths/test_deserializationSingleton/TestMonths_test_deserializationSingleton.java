package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestMonths_test_deserializationSingleton {

    /**
     * Verifies that Java serialization round-trip preserves singleton identity for
     * {@code Months.ZERO}. {@code Months} implements {@code readResolve()} so that
     * deserialization returns the pre-existing singleton rather than a new instance,
     * which is why {@code assertSame} (reference equality) is the correct assertion.
     */
    @Test
    public void test_deserializationSingleton() throws Exception {
        Months original = Months.ZERO;

        // Serialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }

        // Deserialize and confirm the same singleton instance is returned
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertSame(original, ois.readObject());
        }
    }
}
