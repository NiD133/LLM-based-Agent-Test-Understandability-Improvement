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
public class CSVRecord_ESTest_test16 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String duplicateHeaderName = "*;Ax}g<";
        String[] duplicateHeaders = new String[3];
        duplicateHeaders[0] = duplicateHeaderName;
        duplicateHeaders[1] = duplicateHeaderName;
        duplicateHeaders[2] = duplicateHeaderName;

        CSVFormat.Builder formatWithHeadersBuilder = formatBuilder.setHeader(duplicateHeaders);
        CSVFormat formatWithDuplicateHeaders = formatWithHeadersBuilder.get();
        CSVParser parser = CSVParser.parse(duplicateHeaderName, formatWithDuplicateHeaders);
        CSVRecord recordWithOneValue = parser.nextRecord();

        // Undeclared exception!
        try {
            recordWithOneValue.get(duplicateHeaderName);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Index for header '*;Ax}g<' is 2 but CSVRecord only has 1 values!
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
