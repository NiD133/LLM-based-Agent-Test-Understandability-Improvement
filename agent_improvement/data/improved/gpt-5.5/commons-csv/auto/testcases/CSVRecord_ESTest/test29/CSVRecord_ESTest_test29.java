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
public class CSVRecord_ESTest_test29 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test29() throws Throwable {
        CSVFormat defaultFormat = CSVFormat.DEFAULT;
        CSVParser parser = CSVParser.parse("*;Ax}g<", defaultFormat);
        CSVRecord record = parser.nextRecord();

        record.spliterator();

        assertEquals(1L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
        assertEquals(1, record.size());
    }
}
