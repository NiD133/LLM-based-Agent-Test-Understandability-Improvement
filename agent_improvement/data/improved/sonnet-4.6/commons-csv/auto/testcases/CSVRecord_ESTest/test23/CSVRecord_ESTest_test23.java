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
public class CSVRecord_ESTest_test23 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        // Parse a string with no comma delimiters using DEFAULT format, so the
        // entire input becomes one field in a single record
        CSVFormat defaultFormat = CSVFormat.DEFAULT;
        CSVParser parser = CSVParser.parse("*;Ax}g<", defaultFormat);
        CSVRecord record = parser.nextRecord();

        String recordString = record.toString();

        assertEquals(0L, record.getBytePosition());
        assertEquals("CSVRecord [comment='null', recordNumber=1, values=[*;Ax}g<]]", recordString);
        assertEquals(0L, record.getCharacterPosition());
    }
}
