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
public class CSVRecord_ESTest_test00 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single-line CSV input whose only value matches the configured header,
     * then verifies the positional metadata exposed by the resulting {@link CSVRecord}.
     */
    @Test(timeout = 4000)
    public void parseSingleRecordAndCheckPositionMetadata() throws Throwable {
        // The same token is used as both the (repeated) header and the input row.
        final String token = "*;Ax}g<";

        // Build a CSV format with a three-column header, all sharing the same name.
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        formatBuilder.setHeader(token, token, token);
        CSVFormat format = formatBuilder.get();

        // Parse the input and read the first (and only) record.
        CSVParser parser = CSVParser.parse(token, format);
        CSVRecord record = parser.nextRecord();

        // Build the header-to-value map (exercised for side-effect coverage).
        record.toMap();

        // The first parsed record is record number 1 and starts at the stream origin.
        assertEquals(1L, record.getRecordNumber());
        assertEquals(1, record.size());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
    }
}
