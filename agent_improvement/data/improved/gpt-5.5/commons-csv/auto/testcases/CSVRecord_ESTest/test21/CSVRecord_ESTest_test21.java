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

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        final CSVParser parser = null;
        final String[] recordValues = new String[2];
        final String comment = "org.apache.commons.io.output.UncheckedFilterWriter";
        final long recordNumber = 0L;
        final long characterPosition = -3204L;
        final long bytePosition = -416L;

        final CSVRecord record = new CSVRecord(parser, recordValues, comment, recordNumber, characterPosition, bytePosition);

        record.getParser();
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(recordValues.length, record.size());
        assertEquals(recordNumber, record.getRecordNumber());
    }
}
