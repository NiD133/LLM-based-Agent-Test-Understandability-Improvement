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
    public void test12() throws Throwable {
        String[] values = new String[2];
        String comment = "org.apache.commons.io.output.UncheckedFilterWriter";
        long recordNumber = 0L;
        long characterPosition = -3204L;
        long bytePosition = -416L;

        CSVRecord record = new CSVRecord((CSVParser) null, values, comment, recordNumber, characterPosition, bytePosition);

        boolean hasComment = record.hasComment();
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(2, record.size());
        assertEquals(recordNumber, record.getRecordNumber());
        assertTrue(hasComment);
    }
}
