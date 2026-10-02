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
public class CSVRecord_ESTest_test25 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        String source = "nJ=ULPJYC0~D|7x|2WT";
        CSVParser parser = CSVParser.parse(source, mysqlFormat);

        String[] values = new String[2];
        long recordNumber = 1100L;
        long characterPosition = -1L;
        long bytePosition = 2147483639L;
        CSVRecord record = new CSVRecord(parser, values, source, recordNumber, characterPosition, bytePosition);

        record.getComment();

        assertEquals(2, record.size());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(recordNumber, record.getRecordNumber());
    }
}
