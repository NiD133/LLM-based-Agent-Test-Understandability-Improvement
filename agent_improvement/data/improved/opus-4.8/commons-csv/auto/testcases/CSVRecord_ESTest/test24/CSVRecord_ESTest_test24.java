package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test24 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that toMap() succeeds on a record whose parser has no header
     * mapping, and that the record's positional metadata and size are
     * preserved exactly as supplied to the constructor.
     */
    @Test(timeout = 4000)
    public void toMapWithoutHeadersKeepsRecordMetadata() throws Throwable {
        final long position = -1013L;
        final String input = "*;Ax}g<";

        CSVFormat format = CSVFormat.Builder.create().get();
        CSVParser parser = CSVParser.parse(input, format);

        // Two-element values array; the parser was created without headers.
        String[] values = new String[2];
        CSVRecord record = new CSVRecord(parser, values, input,
                position, position, position);

        // Without a header mapping, toMap() returns an empty map and does not throw.
        record.toMap();

        assertEquals(position, record.getRecordNumber());
        assertEquals(position, record.getBytePosition());
        assertEquals(position, record.getCharacterPosition());
        assertEquals(2, record.size());
    }
}
