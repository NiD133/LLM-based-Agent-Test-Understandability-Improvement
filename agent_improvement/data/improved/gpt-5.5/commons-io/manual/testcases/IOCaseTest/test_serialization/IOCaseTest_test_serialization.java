package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_serialization {

    private IOCase roundTripThroughSerialization(final IOCase ioCase) throws Exception {
        final ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();
        try (ObjectOutputStream output = new ObjectOutputStream(serializedBytes)) {
            output.writeObject(ioCase);
            output.flush();
        }

        final ByteArrayInputStream inputBytes = new ByteArrayInputStream(serializedBytes.toByteArray());
        final ObjectInputStream input = new ObjectInputStream(inputBytes);
        return (IOCase) input.readObject();
    }

    @Test
    void test_serialization() throws Exception {
        assertSame(IOCase.SENSITIVE, roundTripThroughSerialization(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, roundTripThroughSerialization(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM, roundTripThroughSerialization(IOCase.SYSTEM));
    }
}
