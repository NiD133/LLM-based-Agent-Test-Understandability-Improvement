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
public class CSVRecord_ESTest_test22 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        CSVFormat cSVFormat0 = CSVFormat.MYSQL;
        CSVParser cSVParser0 = CSVParser.parse("nJ=ULPJYC0~D|7x|2WT", cSVFormat0);
        String[] stringArray0 = new String[2];
        CSVRecord cSVRecord0 = new CSVRecord(cSVParser0, stringArray0, "nJ=ULPJYC0~D|7x|2WT", 0L, (-1060L), (-1060L));
        // Undeclared exception!
        try {
            cSVRecord0.get(2146);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // Index 2146 out of bounds for length 2
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
