package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestMonths_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Months zeroMonths = Months.ZERO;
        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();

        try (ObjectOutputStream objectOutput = new ObjectOutputStream(serializedBytes)) {
            objectOutput.writeObject(zeroMonths);
        }

        try (ObjectInputStream objectInput =
                new ObjectInputStream(new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            assertSame(zeroMonths, objectInput.readObject());
        }
    }
}
