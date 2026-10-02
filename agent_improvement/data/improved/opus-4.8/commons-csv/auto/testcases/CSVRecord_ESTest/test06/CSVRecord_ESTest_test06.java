package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test06 extends CSVRecord_ESTest_scaffolding {

    /**
     * When a header name is declared more than once, its mapping resolves to the
     * <em>last</em> column index that bears that name. Here the header is declared
     * twice (so the duplicate name maps to index 1), but the parsed input has only a
     * single column (index 0). Because the mapped index 1 falls outside the record's
     * one value, {@link CSVRecord#isSet(String)} reports that the column is not set.
     */
    @Test(timeout = 4000)
    public void isSetReturnsFalseWhenMappedIndexExceedsRecordSize() throws Throwable {
        final String columnName = "*;Ax}g<";

        // Declare the same header name twice; the duplicate maps to the higher index (1).
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(columnName, columnName)
                .get();

        // The input contains a single column, so the record holds exactly one value (index 0).
        CSVParser parser = CSVParser.parse(columnName, format);
        CSVRecord record = parser.nextRecord();

        boolean columnIsSet = record.isSet(columnName);

        assertFalse("Mapped index 1 is out of range for a single-value record", columnIsSet);
        assertEquals(1, record.size());
        assertEquals(1L, record.getRecordNumber());
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
    }
}
