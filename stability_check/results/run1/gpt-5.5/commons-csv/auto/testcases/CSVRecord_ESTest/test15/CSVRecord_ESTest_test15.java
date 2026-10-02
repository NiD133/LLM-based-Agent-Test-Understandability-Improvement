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
        String mappedHeader = "*;Ax}g<";
        String missingHeader = "";
        long recordMetadata = -1013L;

        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] recordValuesAndHeaders = new String[2];
        recordValuesAndHeaders[0] = mappedHeader;
        recordValuesAndHeaders[1] = mappedHeader;

        CSVFormat.Builder formatWithHeader = formatBuilder.setHeader(recordValuesAndHeaders);
        CSVFormat format = formatWithHeader.get();
        CSVParser parser = CSVParser.parse(mappedHeader, format);
        CSVRecord record = new CSVRecord(parser, recordValuesAndHeaders, mappedHeader, recordMetadata, recordMetadata, recordMetadata);

        try {
            record.get(missingHeader);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
