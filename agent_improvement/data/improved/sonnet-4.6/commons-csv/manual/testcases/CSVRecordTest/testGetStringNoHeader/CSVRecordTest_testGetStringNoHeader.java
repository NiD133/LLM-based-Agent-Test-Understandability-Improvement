package org.apache.commons.csv;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVRecordTest_testGetStringNoHeader {

    private CSVRecord record;

    @BeforeEach
    public void setUp() throws Exception {
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader("A,B,C"))) {
            record = parser.iterator().next();
        }
    }

    @Test
    void testGetStringNoHeader() {
        assertThrows(IllegalStateException.class, () -> record.get("first"));
    }
}
