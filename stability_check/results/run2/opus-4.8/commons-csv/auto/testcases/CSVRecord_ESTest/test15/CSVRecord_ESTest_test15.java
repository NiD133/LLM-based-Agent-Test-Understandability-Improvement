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
     * Calling {@link CSVRecord#get(String)} with a name that is not present in the
     * header mapping must fail with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void getByUnknownHeaderNameThrowsIllegalArgumentException() throws Throwable {
        // The single column header used for the record.
        String headerName = "*;Ax}g<";
        String[] recordValues = { headerName, headerName };

        // Build a format whose header maps the name above to a column index.
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(recordValues)
                .get();
        CSVParser parser = CSVParser.parse(headerName, format);

        CSVRecord record = new CSVRecord(parser, recordValues, headerName, -1013L, -1013L, -1013L);

        // The empty string is not a mapped header name, so lookup by name must fail.
        try {
            record.get("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Mapping for  not found, expected one of [*;Ax}g<]"
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
