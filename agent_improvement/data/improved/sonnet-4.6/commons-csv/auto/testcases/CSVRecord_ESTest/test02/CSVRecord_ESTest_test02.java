package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test02 extends CSVRecord_ESTest_scaffolding {

    // Used as both the multi-character delimiter and the header column name
    private static final String DELIMITER_AND_HEADER = "*;Ax}g<";

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Build a format that uses the same string as delimiter and as two duplicate header names.
        // Parsing that string as input splits it into two empty fields, one per header slot.
        String[] headers = { DELIMITER_AND_HEADER, DELIMITER_AND_HEADER };
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(headers)
                .setDelimiter(DELIMITER_AND_HEADER)
                .get();

        CSVParser parser = CSVParser.parse(DELIMITER_AND_HEADER, format);
        CSVRecord record = parser.nextRecord();

        boolean columnIsSet = record.isSet(DELIMITER_AND_HEADER);

        assertEquals(1L, record.getRecordNumber());
        assertTrue(columnIsSet);
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
    }
}
