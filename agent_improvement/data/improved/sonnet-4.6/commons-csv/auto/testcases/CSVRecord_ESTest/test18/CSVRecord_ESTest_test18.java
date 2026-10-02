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
        // Construct a CSVRecord with null parser and values, a comment, and explicit position metadata
        final long recordNumber      = 3988L;
        final long characterPosition = 3988L;
        final long bytePosition      = -977L;

        CSVRecord record = new CSVRecord(
                (CSVParser) null,
                (String[]) null,
                "h%l{_WoZB#FA_}`",
                recordNumber,
                characterPosition,
                bytePosition);

        assertEquals(bytePosition,      record.getBytePosition());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(recordNumber,      record.getRecordNumber());
    }
}
