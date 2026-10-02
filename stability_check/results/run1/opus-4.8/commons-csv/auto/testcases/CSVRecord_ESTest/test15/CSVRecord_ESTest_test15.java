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
     * Verifies that {@link CSVRecord#get(String)} throws an
     * {@link IllegalArgumentException} when the requested column name is not
     * present in the record's header mapping. Here the only mapped header is
     * "*;Ax}g<", so requesting the empty-string column name is unmapped.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        final String headerName = "*;Ax}g<";
        final String[] headers = { headerName, headerName };

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(headers)
                .get();
        CSVParser parser = CSVParser.parse(headerName, format);
        CSVRecord record = new CSVRecord(parser, headers, headerName, -1013L, -1013L, -1013L);

        try {
            record.get("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Mapping for  not found, expected one of [*;Ax}g<]
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
