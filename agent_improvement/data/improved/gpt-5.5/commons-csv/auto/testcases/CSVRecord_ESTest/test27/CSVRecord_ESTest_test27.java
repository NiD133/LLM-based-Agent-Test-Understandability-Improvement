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
public class CSVRecord_ESTest_test27 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        String csvLine = "org.apache.commons.io.input.UncheckedBufferedReader$Builder";
        CSVFormat oracleFormat = CSVFormat.ORACLE;
        CSVParser parser = CSVParser.parse(csvLine, oracleFormat);

        CSVRecord firstRecord = parser.nextRecord();
        String[] recordValues = firstRecord.values();

        assertEquals(1L, firstRecord.getRecordNumber());
        assertEquals(1, recordValues.length);
        assertEquals(0L, firstRecord.getBytePosition());
        assertEquals(0L, firstRecord.getCharacterPosition());
    }
}
