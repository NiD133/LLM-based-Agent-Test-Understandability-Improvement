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
public class CSVRecord_ESTest_test19 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        StringReader stringReader0 = new StringReader("NsO[}lL&3m");
        CSVFormat cSVFormat0 = CSVFormat.INFORMIX_UNLOAD_CSV;
        CSVParser cSVParser0 = CSVParser.parse((Reader) stringReader0, cSVFormat0);
        String[] stringArray0 = new String[2];
        CSVRecord cSVRecord0 = new CSVRecord(cSVParser0, stringArray0, "\u0085", 0L, 0L, 0L);
        int int0 = cSVRecord0.size();
        assertEquals(2, int0);
    }
}
