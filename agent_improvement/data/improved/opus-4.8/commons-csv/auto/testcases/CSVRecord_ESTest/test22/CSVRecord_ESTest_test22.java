package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test22 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that {@link CSVRecord#get(int)} throws an
     * {@link ArrayIndexOutOfBoundsException} when the requested column index
     * is larger than the number of values in the record.
     */
    @Test(timeout = 4000)
    public void getWithIndexBeyondValuesThrowsArrayIndexOutOfBounds() throws Throwable {
        String csvLine = "nJ=ULPJYC0~D|7x|2WT";
        CSVParser parser = CSVParser.parse(csvLine, CSVFormat.MYSQL);

        // Build a record that holds only 2 values.
        String[] values = new String[2];
        CSVRecord record = new CSVRecord(parser, values, csvLine, 0L, -1060L, -1060L);

        int outOfRangeIndex = 2146;
        try {
            record.get(outOfRangeIndex);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Index 2146 is out of bounds for a record of length 2.
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
