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
public class CSVRecord_ESTest_test17 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        CSVFormat cSVFormat0 = CSVFormat.POSTGRESQL_CSV;
        CSVParser cSVParser0 = CSVParser.parse("*PAx}[gk", cSVFormat0);
        CSVRecord cSVRecord0 = cSVParser0.nextRecord();
        // Undeclared exception!
        try {
            cSVRecord0.get((Enum<?>) null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // No header mapping was specified, the record values can't be accessed by name
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
