package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test02 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single record whose header declares a column named {@code TOKEN} and
     * verifies that {@link CSVRecord#isSet(String)} reports that column as set, while
     * the record's positional metadata reflects the first (and only) record.
     */
    @Test(timeout = 4000)
    public void isSet_returnsTrue_forMappedColumnWithValue() throws Throwable {
        // The same token serves as both a header column name and the field delimiter.
        final String token = "*;Ax}g<";

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(token, token)
                .setDelimiter(token)
                .get();

        CSVParser parser = CSVParser.parse(token, format);
        CSVRecord record = parser.nextRecord();

        // The "token" column is mapped by the header and has a corresponding value.
        assertTrue(record.isSet(token));

        // First record parsed, positioned at the very start of the source stream.
        assertEquals(1L, record.getRecordNumber());
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
    }
}
