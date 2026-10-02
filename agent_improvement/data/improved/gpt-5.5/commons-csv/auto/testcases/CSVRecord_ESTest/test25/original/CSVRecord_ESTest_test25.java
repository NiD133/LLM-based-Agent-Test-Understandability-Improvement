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
        CSVFormat cSVFormat0 = CSVFormat.MYSQL;
        CSVParser cSVParser0 = CSVParser.parse("nJ=ULPJYC0~D|7x|2WT", cSVFormat0);
        String[] stringArray0 = new String[2];
        CSVRecord cSVRecord0 = new CSVRecord(cSVParser0, stringArray0, "nJ=ULPJYC0~D|7x|2WT", 1100L, (-1L), 2147483639L);
        cSVRecord0.getComment();
        assertEquals(2, cSVRecord0.size());
        assertEquals((-1L), cSVRecord0.getCharacterPosition());
        assertEquals(2147483639L, cSVRecord0.getBytePosition());
        assertEquals(1100L, cSVRecord0.getRecordNumber());
    }
}
