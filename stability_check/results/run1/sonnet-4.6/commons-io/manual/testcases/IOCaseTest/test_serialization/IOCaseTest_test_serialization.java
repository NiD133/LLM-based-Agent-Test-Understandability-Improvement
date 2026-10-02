package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_serialization {

    /**
     * Serializes {@code value} to a byte array and deserializes it back.
     * IOCase implements {@code readResolve()}, so the deserialized object
     * must be the same canonical enum instance, not a new copy.
     */
    private IOCase serializeAndDeserialize(final IOCase value) throws Exception {
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buf)) {
            out.writeObject(value);
            out.flush();
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()))) {
            return (IOCase) in.readObject();
        }
    }

    /**
     * Verifies that Java serialization preserves enum identity for every IOCase constant.
     *
     * assertSame (reference equality) is intentional: because IOCase.readResolve()
     * returns the existing enum singleton, the deserialized object must be the
     * exact same instance, not merely an equal one.
     */
    @Test
    void test_serialization() throws Exception {
        assertSame(IOCase.SENSITIVE,   serializeAndDeserialize(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, serializeAndDeserialize(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM,      serializeAndDeserialize(IOCase.SYSTEM));
    }
}
