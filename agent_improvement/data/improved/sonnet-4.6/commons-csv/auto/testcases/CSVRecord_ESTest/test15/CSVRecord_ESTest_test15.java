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
     * Verifies that CSVRecord.get(String) throws IllegalArgumentException
     * when the requested column name is not present in the header mapping.
     *
     * The CSV format is built with a single distinct header name ("*;Ax}g<").
     * Looking up an empty string ("") which is not a defined header should fail.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Build a CSVFormat with a two-element header array where both entries
        // share the same column name "*;Ax}g<" (duplicate headers are allowed).
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String headerColumnName = "*;Ax}g<";
        String[] headerNames = new String[2];
        headerNames[0] = headerColumnName;
        headerNames[1] = headerColumnName;
        CSVFormat.Builder formatBuilderWithHeader = formatBuilder.setHeader(headerNames);
        CSVFormat csvFormat = formatBuilderWithHeader.get();

        // Parse a CSV input that matches the header column name.
        CSVParser csvParser = CSVParser.parse(headerColumnName, csvFormat);

        // Construct a CSVRecord directly, using the header names array as values,
        // with a comment and negative positions/record number.
        long recordNumber = -1013L;
        long characterPosition = -1013L;
        long bytePosition = -1013L;
        CSVRecord csvRecord = new CSVRecord(csvParser, headerNames, headerColumnName,
                recordNumber, characterPosition, bytePosition);

        // Attempting to get a column by an empty string name should throw
        // IllegalArgumentException because "" is not in the header mapping.
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
