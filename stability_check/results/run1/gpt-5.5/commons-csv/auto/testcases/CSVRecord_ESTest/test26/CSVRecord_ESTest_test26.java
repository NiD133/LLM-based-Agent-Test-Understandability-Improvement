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
        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        String csvInputAndComment = "nJ=ULPJYC0~D|7x|2WT";
        CSVParser parser = CSVParser.parse(csvInputAndComment, mysqlFormat);
        String[] recordValues = new String[2];
        long recordNumber = 0L;
        long characterPosition = -1060L;
        long bytePosition = -1060L;

        CSVRecord record = new CSVRecord(parser, recordValues, csvInputAndComment, recordNumber, characterPosition, bytePosition);

        long actualBytePosition = record.getBytePosition();

        assertEquals(0L, record.getRecordNumber());
        assertEquals(-1060L, actualBytePosition);
        assertEquals(-1060L, record.getCharacterPosition());
        assertEquals(2, record.size());
    }
}
