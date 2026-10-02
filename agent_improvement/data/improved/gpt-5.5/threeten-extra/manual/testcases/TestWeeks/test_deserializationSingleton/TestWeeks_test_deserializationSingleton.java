package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Weeks zeroWeeks = Weeks.ZERO;

        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(serializedBytes)) {
            outputStream.writeObject(zeroWeeks);
        }

        ByteArrayInputStream inputBytes = new ByteArrayInputStream(serializedBytes.toByteArray());
        try (ObjectInputStream inputStream = new ObjectInputStream(inputBytes)) {
            assertSame(zeroWeeks, inputStream.readObject());
        }
    }
}
