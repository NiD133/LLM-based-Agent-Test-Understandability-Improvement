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
        CSVFormat cSVFormat0 = CSVFormat.DEFAULT;
        CSVParser cSVParser0 = CSVParser.parse("org.apach.commons.io.input.UnsynchronizedFilterInputStream$Builder", cSVFormat0);
        CSVRecord cSVRecord0 = cSVParser0.nextRecord();
        boolean boolean0 = cSVRecord0.isConsistent();
        assertEquals(0L, cSVRecord0.getBytePosition());
        assertEquals(1, cSVRecord0.size());
        assertEquals(1L, cSVRecord0.getRecordNumber());
        assertTrue(boolean0);
        assertEquals(0L, cSVRecord0.getCharacterPosition());
    }
}
