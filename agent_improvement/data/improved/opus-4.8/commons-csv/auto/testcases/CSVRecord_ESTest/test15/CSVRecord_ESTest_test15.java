package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test15 extends CSVRecord_ESTest_scaffolding {

    /**
     * Looking up a record value by a column name that is not present in the
     * header mapping must fail with an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void getByUnmappedColumnNameThrowsIllegalArgumentException() throws Throwable {
        // The only mapped header name; "" (the name we look up) is intentionally absent.
        final String headerName = "*;Ax}g<";
        final String[] headerValues = { headerName, headerName };

        // Build a format whose header defines the available column name mapping.
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(headerValues)
                .get();
        CSVParser parser = CSVParser.parse(headerName, format);

        CSVRecord record = new CSVRecord(parser, headerValues, headerName, -1013L, -1013L, -1013L);

        try {
            record.get("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Mapping for  not found, expected one of [*;Ax}g<]"
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
