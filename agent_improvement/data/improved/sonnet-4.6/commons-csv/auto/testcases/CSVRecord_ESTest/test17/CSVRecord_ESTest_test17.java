package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test17 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that calling get(Enum) on a record parsed from a format with no
     * header mapping throws IllegalStateException, because values cannot be
     * accessed by name when no header was defined.
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        CSVFormat postgresFormat = CSVFormat.POSTGRESQL_CSV;
        CSVParser parser = CSVParser.parse("*PAx}[gk", postgresFormat);
        CSVRecord record = parser.nextRecord();

        try {
            record.get((Enum<?>) null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
