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

    private static final String SOURCE_CONTENT = "nJ=ULPJYC0~D|7x|2WT";
    private static final long RECORD_NUMBER = 0L;
    private static final long POSITION_OUTSIDE_SOURCE = -1060L;

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        CSVParser parser = CSVParser.parse(SOURCE_CONTENT, mysqlFormat);
        String[] recordValues = new String[2];

        CSVRecord record = new CSVRecord(
                parser,
                recordValues,
                SOURCE_CONTENT,
                RECORD_NUMBER,
                POSITION_OUTSIDE_SOURCE,
                POSITION_OUTSIDE_SOURCE);

        long bytePosition = record.getBytePosition();

        assertEquals(RECORD_NUMBER, record.getRecordNumber());
        assertEquals(POSITION_OUTSIDE_SOURCE, bytePosition);
        assertEquals(POSITION_OUTSIDE_SOURCE, record.getCharacterPosition());
        assertEquals(2, record.size());
    }
}
