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
public class CSVRecord_ESTest_test01 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that {@link CSVRecord#toMap()} collapses duplicate header names into a
     * single entry, and that the record's positional metadata (character position, byte
     * position and record number) is preserved exactly as passed to the constructor.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // A single token used as both the parsed input and the two (identical) header names.
        final String token = "*;Ax}g<";

        // Two columns share the same header name; toMap() should keep only one of them.
        final String[] duplicateHeaders = { token, token };

        // The positional metadata values fed to the CSVRecord constructor below.
        final long expectedRecordNumber = -1013L;
        final long expectedCharacterPosition = -1013L;
        final long expectedBytePosition = -1013L;

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(duplicateHeaders)
                .get();
        CSVParser parser = CSVParser.parse(token, format);

        CSVRecord record = new CSVRecord(
                parser,
                duplicateHeaders,
                token, // comment
                expectedRecordNumber,
                expectedCharacterPosition,
                expectedBytePosition);

        Map<String, String> recordAsMap = record.toMap();

        // The two duplicate headers collapse to a single map entry.
        assertEquals(1, recordAsMap.size());

        // Positional metadata is returned unchanged.
        assertEquals(expectedCharacterPosition, record.getCharacterPosition());
        assertEquals(expectedRecordNumber, record.getRecordNumber());
        assertEquals(expectedBytePosition, record.getBytePosition());
    }
}
