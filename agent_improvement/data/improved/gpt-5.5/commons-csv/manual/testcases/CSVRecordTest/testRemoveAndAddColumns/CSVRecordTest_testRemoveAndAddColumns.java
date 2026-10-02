package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testRemoveAndAddColumns {

    private enum EnumHeader {
        FIRST, SECOND, THIRD
    }

    private static final String REMOVED_COLUMN = "OldColumn";
    private static final String ADDED_COLUMN = "ZColumn";
    private static final String ADDED_VALUE = "NewValue";

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

    @Test
    void testRemoveAndAddColumns() throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new StringBuilder(), CSVFormat.DEFAULT)) {
            final Map<String, String> valuesByHeader = recordWithHeader.toMap();

            valuesByHeader.remove(REMOVED_COLUMN);
            valuesByHeader.put(ADDED_COLUMN, ADDED_VALUE);

            final ArrayList<String> sortedValues = new ArrayList<>(valuesByHeader.values());
            sortedValues.sort(null);
            printer.printRecord(sortedValues);

            assertEquals("A,B,C,NewValue" + CSVFormat.DEFAULT.getRecordSeparator(), printer.getOut().toString());
        }
    }
}
