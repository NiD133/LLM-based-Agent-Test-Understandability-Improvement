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
public class CSVRecord_ESTest_test05 extends CSVRecord_ESTest_scaffolding {

    private static final int RECORD_VALUE_COUNT = 2;
    private static final int NEGATIVE_COLUMN_INDEX = -2522;
    private static final long RECORD_NUMBER = 1105L;
    private static final long CHARACTER_POSITION = 0L;
    private static final long BYTE_POSITION = 0L;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        String[] values = new String[RECORD_VALUE_COUNT];
        CSVRecord record = new CSVRecord((CSVParser) null, values, "", RECORD_NUMBER, CHARACTER_POSITION, BYTE_POSITION);

        boolean columnIsSet = record.isSet(NEGATIVE_COLUMN_INDEX);

        assertEquals(RECORD_NUMBER, record.getRecordNumber());
        assertEquals(CHARACTER_POSITION, record.getCharacterPosition());
        assertEquals(RECORD_VALUE_COUNT, record.size());
        assertEquals(BYTE_POSITION, record.getBytePosition());
        assertFalse(columnIsSet);
    }
}
