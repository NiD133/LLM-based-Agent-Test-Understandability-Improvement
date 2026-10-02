package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

public class CSVRecordTest_testSerialization {

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        // Parse a CSV with a header row and an inline comment to produce the record under test
        final CSVRecord original;
        try (CSVParser parser = CSVParser.parse("A,B\n#my comment\nOne,Two",
                CSVFormat.DEFAULT.withHeader().withCommentMarker('#'))) {
            original = parser.iterator().next();
        }

        // Serialize the record to a byte array
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(original);
        }

        // Deserialize the record and verify that all serializable state is correctly restored
        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            final Object object = ois.readObject();
            assertInstanceOf(CSVRecord.class, object);
            final CSVRecord deserialized = (CSVRecord) object;

            // Record values, position metadata, and the attached comment survive the round-trip
            assertEquals(1L, deserialized.getRecordNumber());
            assertEquals("One", deserialized.get(0));
            assertEquals("Two", deserialized.get(1));
            assertEquals(2, deserialized.size());
            assertEquals(original.getCharacterPosition(), deserialized.getCharacterPosition());
            assertEquals("my comment", deserialized.getComment());

            // The parser field is transient; it is intentionally not serialized
            assertNull(deserialized.getParser());

            // Without a parser the header map is absent, so all name-based access is unavailable
            assertTrue(deserialized.isConsistent());
            assertFalse(deserialized.isMapped("A"));
            assertFalse(deserialized.isSet("A"));
            assertEquals(0, deserialized.toMap().size());
            assertThrows(IllegalStateException.class, () -> deserialized.get("A"));
        }
    }
}
