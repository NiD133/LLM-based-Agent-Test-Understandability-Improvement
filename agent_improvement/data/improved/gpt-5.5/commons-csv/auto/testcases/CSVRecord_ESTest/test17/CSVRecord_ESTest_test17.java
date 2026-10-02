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
        final CSVFormat formatWithoutHeaders = CSVFormat.POSTGRESQL_CSV;
        final CSVParser parser = CSVParser.parse("*PAx}[gk", formatWithoutHeaders);
        final CSVRecord recordWithoutHeaderMapping = parser.nextRecord();

        try {
            recordWithoutHeaderMapping.get((Enum<?>) null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // CSVRecord#get(Enum<?>) delegates to name lookup, which requires a header map.
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
