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

    @Test(timeout = 4000)
    public void testHasCommentReturnsTrueWhenCommentIsProvided() throws Throwable {
        String[] twoValueFields = new String[2];
        CSVRecord record = new CSVRecord(
                (CSVParser) null,
                twoValueFields,
                "org.apache.commons.io.output.UncheckedFilterWriter",
                /* recordNumber */ 0L,
                /* characterPosition */ (-3204L),
                /* bytePosition */ (-416L));

        boolean hasComment = record.hasComment();

        assertTrue(hasComment);
        assertEquals(2, record.size());
        assertEquals(0L, record.getRecordNumber());
        assertEquals((-3204L), record.getCharacterPosition());
        assertEquals((-416L), record.getBytePosition());
    }
}
