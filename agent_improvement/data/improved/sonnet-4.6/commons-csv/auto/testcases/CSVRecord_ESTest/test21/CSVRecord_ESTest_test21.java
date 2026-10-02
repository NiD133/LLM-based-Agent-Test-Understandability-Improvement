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
public class CSVRecord_ESTest_test21 extends CSVRecord_ESTest_scaffolding {

    private static final int    FIELD_COUNT        = 2;
    private static final long   RECORD_NUMBER      = 0L;
    private static final long   CHARACTER_POSITION = -3204L;
    private static final long   BYTE_POSITION      = -416L;
    private static final String COMMENT_TEXT       = "org.apache.commons.io.output.UncheckedFilterWriter";

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        String[] fields = new String[FIELD_COUNT];

        CSVRecord record = new CSVRecord(
                (CSVParser) null,
                fields,
                COMMENT_TEXT,
                RECORD_NUMBER,
                CHARACTER_POSITION,
                BYTE_POSITION);

        // getParser() must not throw even when the parser is null
        record.getParser();

        assertEquals(BYTE_POSITION,      record.getBytePosition());
        assertEquals(CHARACTER_POSITION, record.getCharacterPosition());
        assertEquals(FIELD_COUNT,        record.size());
        assertEquals(RECORD_NUMBER,      record.getRecordNumber());
    }
}
