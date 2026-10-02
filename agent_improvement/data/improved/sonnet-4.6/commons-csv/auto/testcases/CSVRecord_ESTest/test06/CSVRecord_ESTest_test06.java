package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test06 extends CSVRecord_ESTest_scaffolding {

    /**
     * When two header columns share the same name, the header map retains only
     * the last index (index 1 for a two-element duplicate header array).
     * Parsing a single-token CSV value produces a record with size 1, so
     * isSet(duplicateHeaderName) returns false because the mapped index (1) is
     * out of bounds for that single-value record.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Build a format whose header has two columns with identical names.
        // The header map will map that name to index 1 (the last occurrence).
        String duplicateHeaderName = "*;Ax}g<";
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] headerWithDuplicates = new String[2];
        headerWithDuplicates[0] = duplicateHeaderName;
        headerWithDuplicates[1] = duplicateHeaderName;
        formatBuilder.setHeader(headerWithDuplicates);
        CSVFormat format = formatBuilder.get();

        // Parse a CSV string whose sole token matches the header name.
        // The default format has no delimiter within the value, so the record
        // contains exactly one value at index 0.
        CSVParser parser = CSVParser.parse(duplicateHeaderName, format);
        CSVRecord record = parser.nextRecord();

        // isSet returns false because the header map resolves the duplicate
        // name to index 1, which is beyond the single-value record's bounds.
        boolean columnIsSet = record.isSet(duplicateHeaderName);

        assertEquals(1L, record.getRecordNumber());
        assertFalse(columnIsSet);
        assertEquals(1, record.size());
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
    }
}
