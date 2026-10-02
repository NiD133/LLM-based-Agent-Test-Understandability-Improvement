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

    private static final String DUPLICATE_HEADER_AND_DELIMITER = "*;Ax}g<";

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();

        String[] duplicateHeaders = new String[2];
        duplicateHeaders[0] = DUPLICATE_HEADER_AND_DELIMITER;
        duplicateHeaders[1] = DUPLICATE_HEADER_AND_DELIMITER;

        CSVFormat.Builder builderWithHeaders = formatBuilder.setHeader(duplicateHeaders);
        CSVFormat.Builder builderWithDelimiter = builderWithHeaders.setDelimiter(DUPLICATE_HEADER_AND_DELIMITER);
        CSVFormat format = builderWithDelimiter.get();

        CSVParser parser = CSVParser.parse(DUPLICATE_HEADER_AND_DELIMITER, format);
        CSVRecord record = parser.nextRecord();

        boolean duplicateHeaderIsSet = record.isSet(DUPLICATE_HEADER_AND_DELIMITER);

        assertEquals(1L, record.getRecordNumber());
        assertTrue(duplicateHeaderIsSet);
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
    }
}
