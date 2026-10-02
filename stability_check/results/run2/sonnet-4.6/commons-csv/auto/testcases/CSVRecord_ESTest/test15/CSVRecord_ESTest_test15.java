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

    /**
     * Verifies that CSVRecord.get(String) throws IllegalArgumentException when the
     * given column name is not present in the header mapping.
     *
     * The record is built with headers ["*;Ax}g<", "*;Ax}g<"], so looking up the
     * empty-string key "" has no mapping and must trigger the exception.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Build a CSVFormat whose header columns are both named "*;Ax}g<"
        String[] headerNames = new String[2];
        headerNames[0] = "*;Ax}g<";
        headerNames[1] = "*;Ax}g<";
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        CSVFormat.Builder formatBuilderWithHeader = formatBuilder.setHeader(headerNames);
        CSVFormat csvFormat = formatBuilderWithHeader.get();

        // Parse a CSV string that matches the header value
        CSVParser csvParser = CSVParser.parse("*;Ax}g<", csvFormat);

        // Construct a CSVRecord directly; record number and positions are set to -1013
        CSVRecord csvRecord = new CSVRecord(csvParser, headerNames, "*;Ax}g<", (-1013L), (-1013L), (-1013L));

        // Attempting to retrieve a column by the empty string should fail because
        // "" is not a defined header name — only "*;Ax}g<" is mapped
        try {
            csvRecord.get("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Mapping for  not found, expected one of [*;Ax}g<]
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
