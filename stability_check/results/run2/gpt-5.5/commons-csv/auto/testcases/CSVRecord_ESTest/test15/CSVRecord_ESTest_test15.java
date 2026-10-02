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
public class CSVRecord_ESTest_test15 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        String repeatedHeaderName = "*;Ax}g<";
        long negativePosition = (-1013L);

        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] duplicateHeaders = new String[2];
        duplicateHeaders[0] = repeatedHeaderName;
        duplicateHeaders[1] = repeatedHeaderName;

        CSVFormat.Builder formatBuilderWithHeaders = formatBuilder.setHeader(duplicateHeaders);
        CSVFormat format = formatBuilderWithHeaders.get();
        CSVParser parser = CSVParser.parse(repeatedHeaderName, format);
        CSVRecord record = new CSVRecord(parser, duplicateHeaders, repeatedHeaderName, negativePosition, negativePosition, negativePosition);

        // The record has a mapping for repeatedHeaderName, but not for the empty header name.
        try {
            record.get("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Mapping for  not found, expected one of [*;Ax}g<]
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
