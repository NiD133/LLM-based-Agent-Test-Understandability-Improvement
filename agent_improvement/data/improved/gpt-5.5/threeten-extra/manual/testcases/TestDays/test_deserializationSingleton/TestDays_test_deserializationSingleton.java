package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestDays_test_deserializationSingleton {

    @Test
    public void test_deserializationSingleton() throws Exception {
        Days serializedSingleton = Days.ZERO;

        Object deserializedSingleton = deserialize(serialize(serializedSingleton));

        assertSame(serializedSingleton, deserializedSingleton);
    }

    private byte[] serialize(Days days) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(days);
        }
        return baos.toByteArray();
    }

    private Object deserialize(byte[] serialized) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serialized))) {
            return ois.readObject();
        }
    }
}
