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
public class CSVRecord_ESTest_test08 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        final String unmappedColumnName = "";
        final String recordComment = "";
        final long recordNumber = 1105L;
        final long characterPosition = 0L;
        final long bytePosition = 0L;
        final String[] values = new String[2];

        final CSVRecord record = new CSVRecord(
                (CSVParser) null,
                values,
                recordComment,
                recordNumber,
                characterPosition,
                bytePosition);

        final boolean columnIsSet = record.isSet(unmappedColumnName);

        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(recordNumber, record.getRecordNumber());
        assertFalse(columnIsSet);
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(values.length, record.size());
    }
}
