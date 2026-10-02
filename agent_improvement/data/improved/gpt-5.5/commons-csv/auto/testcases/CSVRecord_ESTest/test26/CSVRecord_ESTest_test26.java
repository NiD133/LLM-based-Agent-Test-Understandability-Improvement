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
public class CSVRecord_ESTest_test26 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        final CSVFormat format = CSVFormat.MYSQL;
        final String source = "nJ=ULPJYC0~D|7x|2WT";
        final CSVParser parser = CSVParser.parse(source, format);
        final String[] values = new String[2];
        final String comment = source;
        final long recordNumber = 0L;
        final long characterPosition = -1060L;
        final long bytePosition = -1060L;

        final CSVRecord record = new CSVRecord(parser, values, comment, recordNumber, characterPosition, bytePosition);

        final long actualBytePosition = record.getBytePosition();
        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(bytePosition, actualBytePosition);
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(values.length, record.size());
    }
}
