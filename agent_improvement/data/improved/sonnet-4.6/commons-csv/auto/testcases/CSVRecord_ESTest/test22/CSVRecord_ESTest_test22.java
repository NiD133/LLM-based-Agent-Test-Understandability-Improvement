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

    // CSVRecord.get(int) delegates directly to the backing values array,
    // so accessing an index beyond the array length throws ArrayIndexOutOfBoundsException.
    @Test(timeout = 4000)
    public void test_getByIndex_throwsWhenIndexExceedsRecordSize() throws Throwable {
        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        CSVParser parser = CSVParser.parse("nJ=ULPJYC0~D|7x|2WT", mysqlFormat);

        // Record backed by a 2-element array
        String[] twoElementValues = new String[2];
        CSVRecord record = new CSVRecord(parser, twoElementValues, "nJ=ULPJYC0~D|7x|2WT", 0L, (-1060L), (-1060L));

        // Accessing index 2146 on a 2-element array must throw ArrayIndexOutOfBoundsException
        try {
            record.get(2146);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // Index 2146 out of bounds for length 2
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
