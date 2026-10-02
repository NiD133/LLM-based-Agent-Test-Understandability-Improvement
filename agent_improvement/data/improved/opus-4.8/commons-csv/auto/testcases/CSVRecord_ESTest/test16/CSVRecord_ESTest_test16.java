package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test16 extends CSVRecord_ESTest_scaffolding {

    /**
     * Looking up a value by header name throws IllegalArgumentException when the
     * resolved column index is out of range for the record.
     *
     * <p>The header here declares the same name three times. Because duplicate header
     * names keep only the last occurrence, "*;Ax}g<" maps to index 2. The single input
     * line parses into a record with just one value (index 0), so resolving the name to
     * index 2 falls outside the record and is rejected.</p>
     */
    @Test(timeout = 4000)
    public void getByNameWithIndexBeyondRecordSizeThrowsIllegalArgument() throws Throwable {
        String duplicatedHeaderName = "*;Ax}g<";

        CSVFormat formatWithDuplicateHeaders = CSVFormat.Builder.create()
                .setHeader(duplicatedHeaderName, duplicatedHeaderName, duplicatedHeaderName)
                .get();

        CSVParser parser = CSVParser.parse(duplicatedHeaderName, formatWithDuplicateHeaders);
        CSVRecord singleValueRecord = parser.nextRecord();

        try {
            singleValueRecord.get(duplicatedHeaderName);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Index for header '*;Ax}g<' is 2 but CSVRecord only has 1 values!
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
