package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

/**
 * Tests that the {@link IOCase} enum constants survive a serialization
 * round-trip. Because {@code IOCase} defines {@code readResolve()}, deserializing
 * a constant must return the very same singleton instance, not a copy.
 */
public class IOCaseTest_test_serialization {

    /**
     * Serializes the given {@link IOCase} to bytes and deserializes it back,
     * returning the reconstructed instance.
     */
    private IOCase serializeRoundTrip(final IOCase original) throws Exception {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
            out.flush();
        }
        try (ObjectInputStream in =
                new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            return (IOCase) in.readObject();
        }
    }

    @Test
    void test_serialization() throws Exception {
        assertSame(IOCase.SENSITIVE, serializeRoundTrip(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, serializeRoundTrip(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM, serializeRoundTrip(IOCase.SYSTEM));
    }
}
