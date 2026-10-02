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
public class CSVRecord_ESTest_test18 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        final CSVParser parser = null;
        final String[] values = null;
        final String comment = "h%l{_WoZB#FA_}`";
        final long recordNumber = 3988L;
        final long characterPosition = 3988L;
        final long bytePosition = -977L;

        final CSVRecord record = new CSVRecord(parser, values, comment, recordNumber, characterPosition, bytePosition);

        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(recordNumber, record.getRecordNumber());
    }
}
