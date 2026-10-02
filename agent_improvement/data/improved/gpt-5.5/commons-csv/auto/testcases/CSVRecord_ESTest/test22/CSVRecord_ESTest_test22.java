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
public class CSVRecord_ESTest_test22 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        final String csvInput = "nJ=ULPJYC0~D|7x|2WT";
        final int recordValueCount = 2;
        final int missingValueIndex = 2146;
        final long recordNumber = 0L;
        final long sourcePosition = -1060L;

        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        CSVParser parser = CSVParser.parse(csvInput, mysqlFormat);
        String[] recordValues = new String[recordValueCount];
        CSVRecord record = new CSVRecord(parser, recordValues, csvInput, recordNumber, sourcePosition, sourcePosition);

        try {
            record.get(missingValueIndex);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
