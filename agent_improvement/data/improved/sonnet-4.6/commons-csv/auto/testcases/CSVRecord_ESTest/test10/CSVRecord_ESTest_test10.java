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
public class CSVRecord_ESTest_test10 extends CSVRecord_ESTest_scaffolding {

    /**
     * Tests that isConsistent() returns false when the record has more values than
     * unique header names. When duplicate header names are used, the header map only
     * stores one entry per name (last-wins), so headerMap.size() < values.length,
     * making the record inconsistent.
     */
    @Test(timeout = 4000)
    public void test10_isConsistentReturnsFalseWhenValuesExceedUniqueHeaderCount() throws Throwable {
        // Arrange: build a CSVFormat with two headers that share the same name
        String duplicateHeaderName = "*;Ax}g<";
        String[] headersWithDuplicateName = new String[] { duplicateHeaderName, duplicateHeaderName };

        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        formatBuilder.setHeader(headersWithDuplicateName);
        CSVFormat formatWithDuplicateHeaders = formatBuilder.get();

        // Parse a single-field input; the parser's header map will have only 1 entry
        // (duplicate header name collapses to one mapping)
        CSVParser parser = CSVParser.parse(duplicateHeaderName, formatWithDuplicateHeaders);

        // Build a record with 2 values using the same duplicate-name array
        long sentinel = -1013L;
        String comment = duplicateHeaderName;
        CSVRecord record = new CSVRecord(parser, headersWithDuplicateName, comment, sentinel, sentinel, sentinel);

        // Act
        boolean consistent = record.isConsistent();

        // Assert: positions are stored as provided
        assertEquals(sentinel, record.getBytePosition());
        assertEquals(sentinel, record.getCharacterPosition());
        assertEquals(sentinel, record.getRecordNumber());

        // Assert: record has 2 values but only 1 unique header key → not consistent
        assertEquals(2, record.size());
        assertFalse(consistent);
    }
}
