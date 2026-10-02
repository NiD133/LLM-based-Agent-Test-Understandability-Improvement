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
public class CSVRecord_ESTest_test24 extends CSVRecord_ESTest_scaffolding {

    // Sentinel value used as a placeholder for negative/invalid position metadata
    private static final long NEGATIVE_POSITION = -1013L;

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        // Build a default CSVFormat (no headers defined)
        CSVFormat defaultFormat = CSVFormat.Builder.create().get();

        // Create a parser for a raw CSV string using the default format
        CSVParser parser = CSVParser.parse("*;Ax}g<", defaultFormat);

        // Construct a CSVRecord with 2 values and negative metadata positions,
        // simulating a record with out-of-band/invalid positional information
        String[] twoNullValues = new String[2];
        String comment = "*;Ax}g<";
        CSVRecord record = new CSVRecord(parser, twoNullValues, comment,
                NEGATIVE_POSITION, NEGATIVE_POSITION, NEGATIVE_POSITION);

        // toMap() should return an empty map because no header mapping is defined
        record.toMap();

        // Verify that all metadata is preserved exactly as provided, even when negative
        assertEquals(NEGATIVE_POSITION, record.getRecordNumber());
        assertEquals(NEGATIVE_POSITION, record.getBytePosition());
        assertEquals(NEGATIVE_POSITION, record.getCharacterPosition());

        // Verify that the record size reflects the number of values in the array
        assertEquals(2, record.size());
    }
}
