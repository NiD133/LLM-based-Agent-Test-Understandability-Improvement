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
public class CSVRecord_ESTest_test11 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        CSVFormat defaultFormat = CSVFormat.DEFAULT;
        CSVParser parser = CSVParser.parse("org.apach.commons.io.input.UnsynchronizedFilterInputStream$Builder", defaultFormat);
        CSVRecord firstRecord = parser.nextRecord();

        boolean isRecordConsistent = firstRecord.isConsistent();

        assertEquals(0L, firstRecord.getBytePosition());
        assertEquals(1, firstRecord.size());
        assertEquals(1L, firstRecord.getRecordNumber());
        assertTrue(isRecordConsistent);
        assertEquals(0L, firstRecord.getCharacterPosition());
    }
}
