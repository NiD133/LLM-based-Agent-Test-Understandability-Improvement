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
public class CSVRecord_ESTest_test13 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        CSVFormat defaultFormat = CSVFormat.DEFAULT;
        CSVParser parser = CSVParser.parse("*;Ax}g<", defaultFormat);
        CSVRecord firstRecord = parser.nextRecord();

        boolean hasComment = firstRecord.hasComment();

        assertEquals(0L, firstRecord.getCharacterPosition());
        assertEquals(1L, firstRecord.getRecordNumber());
        assertEquals(1, firstRecord.size());
        assertFalse(hasComment);
        assertEquals(0L, firstRecord.getBytePosition());
    }
}
