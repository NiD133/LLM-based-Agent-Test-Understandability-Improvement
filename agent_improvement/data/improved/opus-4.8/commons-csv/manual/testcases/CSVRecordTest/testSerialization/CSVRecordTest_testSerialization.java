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
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testSerialization {

    private Map<String, Integer> headerMap;

    private CSVRecord record;

    private CSVRecord recordWithHeader;

    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { "A", "B", "C" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(CSVRecordTest.EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    /**
     * Serializes the given record to a byte array and reads it back, returning the
     * deserialized copy. This mirrors how a record would survive a write/read round trip.
     */
    private CSVRecord serializeAndDeserialize(final CSVRecord original) throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream serializedBytes = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOut = new ObjectOutputStream(serializedBytes)) {
            objectOut.writeObject(original);
        }
        try (ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(serializedBytes.toByteArray()))) {
            final Object deserialized = objectIn.readObject();
            assertInstanceOf(CSVRecord.class, deserialized);
            return (CSVRecord) deserialized;
        }
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        // Parse a record from input that has a header row ("A,B"), a comment, and one data row.
        final CSVRecord originalRecord;
        try (CSVParser parser = CSVParser.parse("A,B\n#my comment\nOne,Two", CSVFormat.DEFAULT.withHeader().withCommentMarker('#'))) {
            originalRecord = parser.iterator().next();
        }

        // Round trip the record through Java serialization.
        final CSVRecord deserializedRecord = serializeAndDeserialize(originalRecord);

        // The record values and positional metadata survive serialization.
        assertEquals(1L, deserializedRecord.getRecordNumber());
        assertEquals("One", deserializedRecord.get(0));
        assertEquals("Two", deserializedRecord.get(1));
        assertEquals(2, deserializedRecord.size());
        assertEquals(originalRecord.getCharacterPosition(), deserializedRecord.getCharacterPosition());
        assertEquals("my comment", deserializedRecord.getComment());

        // The parser is transient, so it is not part of the serialized state.
        assertNull(deserializedRecord.getParser());

        // Without the parser, the header mapping is gone: lookups by name are unavailable.
        assertTrue(deserializedRecord.isConsistent());
        assertFalse(deserializedRecord.isMapped("A"));
        assertFalse(deserializedRecord.isSet("A"));
        assertEquals(0, deserializedRecord.toMap().size());

        // Accessing a value by header name now fails because no header mapping exists.
        assertThrows(IllegalStateException.class, () -> deserializedRecord.get("A"));
    }
}
