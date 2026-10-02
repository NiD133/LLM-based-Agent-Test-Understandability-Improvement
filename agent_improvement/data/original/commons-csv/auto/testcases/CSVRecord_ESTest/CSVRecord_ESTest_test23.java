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
        CSVFormat cSVFormat0 = CSVFormat.DEFAULT;
        CSVParser cSVParser0 = CSVParser.parse("*;Ax}g<", cSVFormat0);
        CSVRecord cSVRecord0 = cSVParser0.nextRecord();
        String string0 = cSVRecord0.toString();
        assertEquals(0L, cSVRecord0.getBytePosition());
        assertEquals("CSVRecord [comment='null', recordNumber=1, values=[*;Ax}g<]]", string0);
        assertEquals(0L, cSVRecord0.getCharacterPosition());
    }
}
