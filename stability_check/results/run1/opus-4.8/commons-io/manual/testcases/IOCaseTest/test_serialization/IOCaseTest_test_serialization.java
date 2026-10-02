package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that serializing and deserializing an {@link IOCase} constant yields
 * back the exact same singleton instance.
 * <p>
 * {@code IOCase} declares a {@code readResolve} method so that deserialization
 * resolves to the canonical enum constant rather than creating a copy. This test
 * confirms that contract for every {@code IOCase} value.
 * </p>
 */
public class IOCaseTest_test_serialization {

    /**
     * Serializes the given value to a byte array and immediately deserializes it,
     * returning the reconstructed instance (a full serialization round-trip).
     */
    private IOCase serializeAndDeserialize(final IOCase value) throws Exception {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(value);
            out.flush();
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            return (IOCase) in.readObject();
        }
    }

    @Test
    void test_serialization() throws Exception {
        // A serialization round-trip must resolve back to the original singleton.
        assertSame(IOCase.SENSITIVE, serializeAndDeserialize(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, serializeAndDeserialize(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM, serializeAndDeserialize(IOCase.SYSTEM));
    }
}
