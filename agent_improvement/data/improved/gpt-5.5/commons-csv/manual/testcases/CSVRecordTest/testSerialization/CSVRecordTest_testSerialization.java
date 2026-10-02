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

    private static final String CSV_WITH_HEADER_AND_COMMENT = "A,B\n#my comment\nOne,Two";
    private static final CSVFormat FORMAT_WITH_HEADER_AND_COMMENTS = CSVFormat.DEFAULT.withHeader().withCommentMarker('#');

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        final CSVRecord originalRecord = parseFirstRecord();

        final Object deserializedObject = serializeAndDeserialize(originalRecord);

        assertInstanceOf(CSVRecord.class, deserializedObject);
        final CSVRecord deserializedRecord = (CSVRecord) deserializedObject;
        assertEquals(1L, deserializedRecord.getRecordNumber());
        assertEquals("One", deserializedRecord.get(0));
        assertEquals("Two", deserializedRecord.get(1));
        assertEquals(2, deserializedRecord.size());
        assertEquals(originalRecord.getCharacterPosition(), deserializedRecord.getCharacterPosition());
        assertEquals("my comment", deserializedRecord.getComment());
        assertNull(deserializedRecord.getParser());
        assertTrue(deserializedRecord.isConsistent());
        assertFalse(deserializedRecord.isMapped("A"));
        assertFalse(deserializedRecord.isSet("A"));
        assertEquals(0, deserializedRecord.toMap().size());
        assertThrows(IllegalStateException.class, () -> deserializedRecord.get("A"));
    }

    private CSVRecord parseFirstRecord() throws IOException {
        try (CSVParser parser = CSVParser.parse(CSV_WITH_HEADER_AND_COMMENT, FORMAT_WITH_HEADER_AND_COMMENTS)) {
            return parser.iterator().next();
        }
    }

    private Object serializeAndDeserialize(final CSVRecord record) throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject(record);
        }

        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            return objectInputStream.readObject();
        }
    }
}
