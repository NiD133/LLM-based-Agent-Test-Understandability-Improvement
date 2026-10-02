package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Minutes zeroMinutes = Minutes.ZERO;
        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();

        try (ObjectOutputStream objectOutput = new ObjectOutputStream(serializedBytes)) {
            objectOutput.writeObject(zeroMinutes);
        }

        try (ObjectInputStream objectInput = new ObjectInputStream(
                new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            assertSame(zeroMinutes, objectInput.readObject());
        }
    }
}
