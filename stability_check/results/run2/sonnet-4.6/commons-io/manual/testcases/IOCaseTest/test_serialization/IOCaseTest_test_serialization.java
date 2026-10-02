package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that IOCase enum constants survive Java serialization as the same singleton instances.
 * IOCase.readResolve() is responsible for mapping the deserialized name back to the canonical enum constant,
 * so assertSame (reference equality) is used intentionally — not just value equality.
 */
public class IOCaseTest_test_serialization {

    /**
     * Serializes an IOCase constant to bytes, then deserializes and returns it.
     * Because IOCase implements readResolve(), the returned object must be the
     * exact same JVM instance (i.e. the enum singleton), not a fresh copy.
     */
    private IOCase roundTripSerialize(final IOCase value) throws Exception {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(value);
            out.flush();
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            return (IOCase) in.readObject();
        }
    }

    @Test
    void test_serialization() throws Exception {
        // Each constant must deserialize back to the exact same singleton instance (not a copy).
        assertSame(IOCase.SENSITIVE,   roundTripSerialize(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, roundTripSerialize(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM,      roundTripSerialize(IOCase.SYSTEM));
    }
}
