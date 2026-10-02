/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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

class CSVRecordTest {

    private enum EnumFixture {
        UNKNOWN_COLUMN
    }

    /** This enum overrides toString() but it's the names that matter. */
    public enum EnumHeader {
        FIRST("first"), SECOND("second"), THIRD("third");

        private final String number;

        EnumHeader(final String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return number;
        }
    }

    // Header column names are the enum constant names (FIRST, SECOND, THIRD), not toString() values.
    private static final String FIRST_COLUMN  = EnumHeader.FIRST.name();
    private static final String SECOND_COLUMN = EnumHeader.SECOND.name();
    private static final String THIRD_COLUMN  = EnumHeader.THIRD.name();

    // The three test record values shared across most tests.
    private static final String VALUE_A = "A";
    private static final String VALUE_B = "B";
    private static final String VALUE_C = "C";

    private Map<String, Integer> headerMap;
    private CSVRecord record;           // parsed without a header mapping
    private CSVRecord recordWithHeader; // parsed with EnumHeader header mapping
    private String[] values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new String[] { VALUE_A, VALUE_B, VALUE_C };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(rowData))) {
            record = parser.iterator().next();
        }
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
            headerMap = parser.getHeaderMap();
        }
    }

    @Test
    void testCSVRecordNULLValues() throws IOException {
        // A record constructed with a null values array should behave as empty (size 0).
        try (CSVParser parser = CSVParser.parse("A,B\r\nONE,TWO", CSVFormat.DEFAULT.withHeader())) {
            final CSVRecord csvRecord = new CSVRecord(parser, null, null, 0L, 0L, 0L);
            assertEquals(0, csvRecord.size());
            assertThrows(IllegalArgumentException.class, () -> csvRecord.get("B"));
        }
    }

    @Test
    void testDuplicateHeaderGet() throws IOException {
        // When a header name appears more than once, get() returns the value for the last-mapped column.
        final String csv = "A,A,B,B\n1,2,5,6\n";
        final CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().get();

        try (CSVParser parser = CSVParser.parse(csv, format)) {
            final CSVRecord record = parser.nextRecord();

            assertAll("duplicate header: get() returns the last occurrence",
                () -> assertEquals("2", record.get("A")),
                () -> assertEquals("6", record.get("B"))
            );
        }
    }

    @Test
    void testDuplicateHeaderToMap() throws IOException {
        // When a header name appears more than once, toMap() keeps the value of the last-mapped column.
        final String csv = "A,A,B,B\n1,2,5,6\n";
        final CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().get();

        try (CSVParser parser = CSVParser.parse(csv, format)) {
            final CSVRecord record = parser.nextRecord();
            final Map<String, String> map = record.toMap();

            assertAll("duplicate header: toMap() keeps the last occurrence",
                () -> assertEquals("2", map.get("A")),
                () -> assertEquals("6", map.get("B"))
            );
        }
    }

    @Test
    void testGetInt() {
        assertEquals(values[0], record.get(0));
        assertEquals(values[1], record.get(1));
        assertEquals(values[2], record.get(2));
    }

    @Test
    void testGetNullEnum() {
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get((Enum<?>) null));
    }

    @Test
    void testGetString() {
        assertEquals(values[0], recordWithHeader.get(FIRST_COLUMN));
        assertEquals(values[1], recordWithHeader.get(SECOND_COLUMN));
        assertEquals(values[2], recordWithHeader.get(THIRD_COLUMN));
    }

    @Test
    void testGetStringInconsistentRecord() {
        // Adding a column to the shared header map that has no corresponding value in the record
        // makes the record inconsistent; get() must then throw IllegalArgumentException.
        headerMap.put("fourth", Integer.valueOf(4));
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get("fourth"));
    }

    @Test
    void testGetStringNoHeader() {
        // A record parsed without a header mapping cannot look up columns by name.
        assertThrows(IllegalStateException.class, () -> record.get("first"));
    }

    @Test
    void testGetUnmappedEnum() {
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get(EnumFixture.UNKNOWN_COLUMN));
    }

    @Test
    void testGetUnmappedName() {
        assertThrows(IllegalArgumentException.class, () -> assertNull(recordWithHeader.get("fourth")));
    }

    @Test
    void testGetUnmappedNegativeInt() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recordWithHeader.get(Integer.MIN_VALUE));
    }

    @Test
    void testGetUnmappedPositiveInt() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recordWithHeader.get(Integer.MAX_VALUE));
    }

    @Test
    void testGetWithEnum() {
        // Enum-based get() delegates to the enum's name(), not its overridden toString().
        assertEquals(recordWithHeader.get("FIRST"), recordWithHeader.get(EnumHeader.FIRST));
        assertEquals(recordWithHeader.get("SECOND"), recordWithHeader.get(EnumHeader.SECOND));
        // An enum whose name is absent from the header map throws IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get(EnumFixture.UNKNOWN_COLUMN));
    }

    @Test
    void testIsConsistent() {
        assertTrue(record.isConsistent());
        assertTrue(recordWithHeader.isConsistent());
        // getHeaderMap() returns a defensive copy, so mutating it does not affect the live record.
        final Map<String, Integer> mapCopy = recordWithHeader.getParser().getHeaderMap();
        mapCopy.put("fourth", Integer.valueOf(4));
        assertTrue(recordWithHeader.isConsistent());
    }

    @Test
    void testIsInconsistent() throws IOException {
        final String[] headers = { "first", "second", "third" };
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(rowData))) {
            // getHeaderMapRaw() returns the live map; adding a column with no matching value makes the record inconsistent.
            final Map<String, Integer> liveHeaderMap = parser.getHeaderMapRaw();
            final CSVRecord record1 = parser.iterator().next();
            liveHeaderMap.put("fourth", Integer.valueOf(4));
            assertFalse(record1.isConsistent());
        }
    }

    @Test
    void testIsMapped() {
        assertFalse(record.isMapped("first"),              "record without header: nothing is mapped");
        assertTrue(recordWithHeader.isMapped(FIRST_COLUMN), "record with header: known column is mapped");
        assertFalse(recordWithHeader.isMapped("fourth"),   "record with header: unknown column is not mapped");
    }

    @Test
    void testIsSetInt() {
        assertFalse(record.isSet(-1));   // negative index
        assertTrue(record.isSet(0));     // first valid index
        assertTrue(record.isSet(2));     // last valid index
        assertFalse(record.isSet(3));    // one past the end
        assertTrue(recordWithHeader.isSet(1));
        assertFalse(recordWithHeader.isSet(1000));
    }

    @Test
    void testIsSetString() {
        assertFalse(record.isSet("first"),                     "record without header: nothing is set by name");
        assertTrue(recordWithHeader.isSet(FIRST_COLUMN),       "known column is set");
        assertFalse(recordWithHeader.isSet("DOES NOT EXIST"),  "unknown column is not set");
    }

    @Test
    void testIterator() {
        int i = 0;
        for (final String value : record) {
            assertEquals(values[i], value);
            i++;
        }
    }

    @Test
    void testPutInMap() {
        final Map<String, String> map = new ConcurrentHashMap<>();
        this.recordWithHeader.putIn(map);
        validateMap(map, false);
        // putIn() returns the same map instance, enabling fluent assignment.
        final TreeMap<String, String> map2 = recordWithHeader.putIn(new TreeMap<>());
        validateMap(map2, false);
    }

    @Test
    void testRemoveAndAddColumns() throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new StringBuilder(), CSVFormat.DEFAULT)) {
            final Map<String, String> map = recordWithHeader.toMap();
            map.remove("OldColumn");
            map.put("ZColumn", "NewValue");
            final ArrayList<String> list = new ArrayList<>(map.values());
            list.sort(null);
            printer.printRecord(list);
            assertEquals("A,B,C,NewValue" + CSVFormat.DEFAULT.getRecordSeparator(), printer.getOut().toString());
        }
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        // Parse a record preceded by a comment line to verify the comment survives serialization.
        final CSVRecord shortRec;
        try (CSVParser parser = CSVParser.parse("A,B\n#my comment\nOne,Two", CSVFormat.DEFAULT.withHeader().withCommentMarker('#'))) {
            shortRec = parser.iterator().next();
        }

        // Serialize the record to a byte array.
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(shortRec);
        }

        // Deserialize and verify the round-tripped record.
        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            final Object object = ois.readObject();
            assertInstanceOf(CSVRecord.class, object);
            final CSVRecord rec = (CSVRecord) object;

            // Core field values survive serialization.
            assertEquals(1L, rec.getRecordNumber());
            assertEquals("One", rec.get(0));
            assertEquals("Two", rec.get(1));
            assertEquals(2, rec.size());
            assertEquals(shortRec.getCharacterPosition(), rec.getCharacterPosition());
            assertEquals("my comment", rec.getComment());

            // The parser reference is transient and is lost after serialization.
            assertNull(rec.getParser());
            // Without a parser there is no header map, so all header-based operations are unavailable.
            assertTrue(rec.isConsistent());
            assertFalse(rec.isMapped("A"));
            assertFalse(rec.isSet("A"));
            assertEquals(0, rec.toMap().size());
            assertThrows(IllegalStateException.class, () -> rec.get("A"));
        }
    }

    @Test
    void testStream() {
        final AtomicInteger i = new AtomicInteger();
        record.stream().forEach(value -> {
            assertEquals(values[i.get()], value);
            i.incrementAndGet();
        });
    }

    @Test
    void testToListAdd() {
        final String[] expected = values.clone();
        final List<String> list = record.toList();
        list.add("Last");
        assertEquals("Last", list.get(list.size() - 1));
        assertEquals(list.size(), values.length + 1);
        // toList() returns a copy: the original values array must be unchanged.
        assertArrayEquals(expected, values);
    }

    @Test
    void testToListFor() {
        int i = 0;
        for (final String value : record.toList()) {
            assertEquals(values[i], value);
            i++;
        }
    }

    @Test
    void testToListForEach() {
        final AtomicInteger i = new AtomicInteger();
        record.toList().forEach(e -> {
            assertEquals(values[i.getAndIncrement()], e);
        });
    }

    @Test
    void testToListSet() {
        final String[] expected = values.clone();
        final List<String> list = record.toList();
        list.set(list.size() - 1, "Last");
        assertEquals("Last", list.get(list.size() - 1));
        assertEquals(list.size(), values.length);
        // toList() returns a copy: the original values array must be unchanged.
        assertArrayEquals(expected, values);
    }

    @Test
    void testToMap() {
        final Map<String, String> map = this.recordWithHeader.toMap();
        validateMap(map, true);
    }

    @Test
    void testToMapWithNoHeader() throws Exception {
        try (CSVParser parser = CSVParser.parse("a,b", CSVFormat.newFormat(','))) {
            final CSVRecord shortRec = parser.iterator().next();
            final Map<String, String> map = shortRec.toMap();
            assertNotNull(map, "Map is not null.");
            assertTrue(map.isEmpty(), "Map is empty.");
        }
    }

    @Test
    void testToMapWithShortRecord() throws Exception {
        // toMap() must not throw even when the record has fewer values than header columns.
        try (CSVParser parser = CSVParser.parse("a,b", CSVFormat.DEFAULT.withHeader("A", "B", "C"))) {
            final CSVRecord shortRec = parser.iterator().next();
            shortRec.toMap();
        }
    }

    @Test
    void testToString() {
        assertNotNull(recordWithHeader.toString());
        assertTrue(recordWithHeader.toString().contains("comment="));
        assertTrue(recordWithHeader.toString().contains("recordNumber="));
        assertTrue(recordWithHeader.toString().contains("values="));
    }

    private void validateMap(final Map<String, String> map, final boolean allowsNulls) {
        assertTrue(map.containsKey(FIRST_COLUMN));
        assertTrue(map.containsKey(SECOND_COLUMN));
        assertTrue(map.containsKey(THIRD_COLUMN));
        assertFalse(map.containsKey("fourth"));
        if (allowsNulls) {
            assertFalse(map.containsKey(null));
        }
        assertEquals(VALUE_A, map.get(FIRST_COLUMN));
        assertEquals(VALUE_B, map.get(SECOND_COLUMN));
        assertEquals(VALUE_C, map.get(THIRD_COLUMN));
        assertNull(map.get("fourth"));
    }
}
