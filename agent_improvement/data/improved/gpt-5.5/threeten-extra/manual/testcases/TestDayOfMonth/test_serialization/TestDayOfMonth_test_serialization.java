package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_serialization {

    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        DayOfMonth originalDay = DayOfMonth.of(1);

        byte[] serializedDay = serialize(originalDay);

        assertEquals(originalDay, deserialize(serializedDay));
    }

    private byte[] serialize(DayOfMonth day) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(day);
        }
        return baos.toByteArray();
    }

    private Object deserialize(byte[] serializedDay) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedDay))) {
            return ois.readObject();
        }
    }
}
