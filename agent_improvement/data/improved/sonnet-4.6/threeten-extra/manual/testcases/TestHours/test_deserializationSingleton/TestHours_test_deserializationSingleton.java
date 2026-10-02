package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestHours_test_deserializationSingleton {

    // Verifies that Hours.ZERO preserves singleton identity through serialization.
    // Hours.readResolve() ensures the deserialized instance is the canonical ZERO constant,
    // so assertSame (reference equality) is the correct assertion here.
    @Test
    public void test_deserializationSingleton() throws Exception {
        Hours original = Hours.ZERO;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
        }

        Object deserialized;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            deserialized = ois.readObject();
        }

        assertSame(original, deserialized);
    }
}
