package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_serialization {

    @Test
    public void test_serialization() throws Exception {
        TaiInstant test = TaiInstant.ofTaiSeconds(2, 3);

        ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutput = new ObjectOutputStream(serializedBytes)) {
            objectOutput.writeObject(test);
        }

        ByteArrayInputStream inputBytes = new ByteArrayInputStream(serializedBytes.toByteArray());
        try (ObjectInputStream objectInput = new ObjectInputStream(inputBytes)) {
            assertEquals(test, objectInput.readObject());
        }
    }
}
