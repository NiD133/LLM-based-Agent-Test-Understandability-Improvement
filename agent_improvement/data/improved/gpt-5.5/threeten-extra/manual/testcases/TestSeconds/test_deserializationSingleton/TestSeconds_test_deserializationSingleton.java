package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Seconds expectedSingleton = Seconds.ZERO;
        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();

        try (ObjectOutputStream output = new ObjectOutputStream(serializedBytes)) {
            output.writeObject(expectedSingleton);
        }

        try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            assertSame(expectedSingleton, input.readObject());
        }
    }
}
