package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that serializing then deserializing an {@link IOCase} constant
 * yields the very same singleton instance (thanks to {@code readResolve}).
 */
public class IOCaseTest_test_serialization {

    /**
     * Serializes the given constant to a byte array and reads it back.
     *
     * @param original the IOCase constant to round-trip.
     * @return the deserialized IOCase.
     */
    private IOCase serializeAndDeserialize(final IOCase original) throws Exception {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
            out.flush();
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            return (IOCase) in.readObject();
        }
    }

    @Test
    void test_serialization() throws Exception {
        assertSame(IOCase.SENSITIVE, serializeAndDeserialize(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, serializeAndDeserialize(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM, serializeAndDeserialize(IOCase.SYSTEM));
    }
}
