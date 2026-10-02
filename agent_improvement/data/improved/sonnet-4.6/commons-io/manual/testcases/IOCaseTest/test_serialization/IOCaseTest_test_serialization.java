package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_serialization {

    /**
     * Serializes {@code value} to a byte array and deserializes it back, simulating
     * a full Java serialization round-trip.
     */
    private IOCase serialize(final IOCase value) throws Exception {
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buf)) {
            out.writeObject(value);
            out.flush();
        }
        final ByteArrayInputStream bufin = new ByteArrayInputStream(buf.toByteArray());
        final ObjectInputStream in = new ObjectInputStream(bufin);
        return (IOCase) in.readObject();
    }

    /**
     * Verifies that each IOCase constant resolves back to the canonical singleton
     * after a serialization round-trip. IOCase.readResolve() guarantees this by
     * looking up the constant by name on deserialization, so assertSame (reference
     * equality) is the correct assertion here.
     */
    @Test
    void test_serialization() throws Exception {
        assertSame(IOCase.SENSITIVE, serialize(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, serialize(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM, serialize(IOCase.SYSTEM));
    }
}
