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
        final String duplicateHeaderName = "*;Ax}g<";
        final String missingHeaderName = "";
        final long negativePosition = -1013L;

        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] headerAndRecordValues = new String[2];
        headerAndRecordValues[0] = duplicateHeaderName;
        headerAndRecordValues[1] = duplicateHeaderName;

        CSVFormat.Builder builderWithDuplicateHeader = formatBuilder.setHeader(headerAndRecordValues);
        CSVFormat formatWithDuplicateHeader = builderWithDuplicateHeader.get();
        CSVParser parser = CSVParser.parse(duplicateHeaderName, formatWithDuplicateHeader);
        CSVRecord record = new CSVRecord(
                parser,
                headerAndRecordValues,
                duplicateHeaderName,
                negativePosition,
                negativePosition,
                negativePosition);

        try {
            record.get(missingHeaderName);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
