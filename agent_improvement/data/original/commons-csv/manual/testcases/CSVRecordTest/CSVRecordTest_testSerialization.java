package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    private void validateMap(final Map<String, String> map, final boolean allowsNulls) {
        assertTrue(map.containsKey(EnumHeader.FIRST.name()));
        assertTrue(map.containsKey(EnumHeader.SECOND.name()));
        assertTrue(map.containsKey(EnumHeader.THIRD.name()));
        assertFalse(map.containsKey("fourth"));
        if (allowsNulls) {
            assertFalse(map.containsKey(null));
        }
        assertEquals("A", map.get(EnumHeader.FIRST.name()));
        assertEquals("B", map.get(EnumHeader.SECOND.name()));
        assertEquals("C", map.get(EnumHeader.THIRD.name()));
        assertNull(map.get("fourth"));
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        final CSVRecord shortRec;
        try (CSVParser parser = CSVParser.parse("A,B\n#my comment\nOne,Two", CSVFormat.DEFAULT.withHeader().withCommentMarker('#'))) {
            shortRec = parser.iterator().next();
        }
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(shortRec);
        }
        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            final Object object = ois.readObject();
            assertInstanceOf(CSVRecord.class, object);
            final CSVRecord rec = (CSVRecord) object;
            assertEquals(1L, rec.getRecordNumber());
            assertEquals("One", rec.get(0));
            assertEquals("Two", rec.get(1));
            assertEquals(2, rec.size());
            assertEquals(shortRec.getCharacterPosition(), rec.getCharacterPosition());
            assertEquals("my comment", rec.getComment());
            // The parser is not serialized
            assertNull(rec.getParser());
            // Check all header map functionality is absent
            assertTrue(rec.isConsistent());
            assertFalse(rec.isMapped("A"));
            assertFalse(rec.isSet("A"));
            assertEquals(0, rec.toMap().size());
            // This will throw
            assertThrows(IllegalStateException.class, () -> rec.get("A"));
        }
    }
}
