package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestYears_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Years originalYears = Years.ZERO;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(originalYears);
        }

        byte[] serializedYears = outputStream.toByteArray();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(serializedYears))) {
            assertSame(originalYears, objectInputStream.readObject());
        }
    }
}
