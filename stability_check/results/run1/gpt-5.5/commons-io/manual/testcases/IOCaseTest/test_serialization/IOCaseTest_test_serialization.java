package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_serialization {

    private IOCase serialize(final IOCase ioCase) throws Exception {
        final ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();
        try (ObjectOutputStream output = new ObjectOutputStream(serializedBytes)) {
            output.writeObject(ioCase);
            output.flush();
        }

        try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            return (IOCase) input.readObject();
        }
    }

    @Test
    void test_serialization() throws Exception {
        assertSame(IOCase.SENSITIVE, serialize(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, serialize(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM, serialize(IOCase.SYSTEM));
    }
}
