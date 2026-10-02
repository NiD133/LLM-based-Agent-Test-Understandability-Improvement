package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetNullEnum {

    private CSVRecord recordWithHeader;

    @BeforeEach
    public void setUp() throws Exception {
        final String[] values = {"A", "B", "C"};
        final String rowData = StringUtils.join(values, ',');
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(EnumHeader.class).get().parse(new StringReader(rowData))) {
            recordWithHeader = parser.iterator().next();
        }
    }

    @Test
    @DisplayName("get(Enum) throws IllegalArgumentException when the enum argument is null")
    void testGetNullEnum() {
        assertThrows(IllegalArgumentException.class, () -> recordWithHeader.get((Enum<?>) null));
    }
}
