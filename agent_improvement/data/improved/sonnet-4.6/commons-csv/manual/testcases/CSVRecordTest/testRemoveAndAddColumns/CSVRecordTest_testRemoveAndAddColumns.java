package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.csv.CSVRecordTest.EnumHeader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testRemoveAndAddColumns {

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = {"A", "B", "C"};
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    /**
     * Verifies that a record's map representation can be mutated (column removed and added)
     * and that the resulting values, when sorted, are printed correctly as CSV.
     *
     * The record has headers FIRST=A, SECOND=B, THIRD=C.
     * Removing a non-existent key ("OldColumn") is a no-op.
     * Adding "ZColumn"="NewValue" yields four entries.
     * After sorting the values alphabetically: A, B, C, NewValue.
     */
    @Test
    void testRemoveAndAddColumns() throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new StringBuilder(), CSVFormat.DEFAULT)) {
            // Get a mutable copy of the header→value map
            final Map<String, String> map = recordWithHeader.toMap();

            // Remove a column that does not exist (no-op) and add a new column
            map.remove("OldColumn");
            map.put("ZColumn", "NewValue");

            // Sort values so the output order is deterministic
            final ArrayList<String> sortedValues = new ArrayList<>(map.values());
            sortedValues.sort(null);

            printer.printRecord(sortedValues);

            assertEquals("A,B,C,NewValue" + CSVFormat.DEFAULT.getRecordSeparator(), printer.getOut().toString());
        }
    }
}
