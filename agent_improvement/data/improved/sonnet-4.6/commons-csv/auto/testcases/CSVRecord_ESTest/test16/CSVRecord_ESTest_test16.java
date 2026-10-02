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

    // When multiple headers share the same name, the last occurrence wins and gets the highest index.
    // If a parsed record has fewer values than that index, get(name) must throw IllegalArgumentException.
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // Define a format with three identical header columns; the duplicate name maps to index 2 (last wins).
        String duplicateHeaderName = "*;Ax}g<";
        String[] headersWithDuplicates = new String[] {duplicateHeaderName, duplicateHeaderName, duplicateHeaderName};
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(headersWithDuplicates)
                .get();

        // Parse a CSV string that produces only one value, so the record size (1) is less than header index (2).
        CSVParser parser = CSVParser.parse(duplicateHeaderName, format);
        CSVRecord record = parser.nextRecord();

        // Accessing by the duplicate header name resolves to index 2, but the record only has 1 value.
        try {
            record.get(duplicateHeaderName);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Index for header '*;Ax}g<' is 2 but CSVRecord only has 1 values!
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
