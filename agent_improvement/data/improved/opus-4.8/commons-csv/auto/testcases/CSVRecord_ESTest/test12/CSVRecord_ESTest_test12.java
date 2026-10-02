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
public class CSVRecord_ESTest_test12 extends CSVRecord_ESTest_scaffolding {

    /**
     * Builds a CSVRecord directly (without a parser) and verifies that the
     * positional metadata passed to the constructor is reported back unchanged,
     * and that a non-null comment makes {@link CSVRecord#hasComment()} return true.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Constructor arguments, named for clarity.
        CSVParser noParser = null;
        String[] twoValues = new String[2];
        String comment = "org.apache.commons.io.output.UncheckedFilterWriter";
        long recordNumber = 0L;
        long characterPosition = -3204L;
        long bytePosition = -416L;

        CSVRecord record = new CSVRecord(noParser, twoValues, comment, recordNumber, characterPosition, bytePosition);

        // A non-null comment was supplied, so the record reports having a comment.
        boolean hasComment = record.hasComment();
        assertTrue(hasComment);

        // The constructor stores the positional/metadata values verbatim.
        assertEquals(-3204L, record.getCharacterPosition());
        assertEquals(-416L, record.getBytePosition());
        assertEquals(0L, record.getRecordNumber());

        // size() reflects the length of the supplied values array.
        assertEquals(2, record.size());
    }
}
