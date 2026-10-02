package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_deserializationSingleton {

    // Weeks.ZERO is a singleton; readResolve() ensures the deserialized instance
    // is the same object reference, not a new copy.
    @Test
    public void test_deserializationSingleton() throws Exception {
        Weeks original = Weeks.ZERO;

        byte[] serialized = serialize(original);
        Weeks deserialized = deserialize(serialized);

        assertSame(original, deserialized);
    }

    private byte[] serialize(Object obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        return baos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private <T> T deserialize(byte[] bytes) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (T) ois.readObject();
        }
    }
}
