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
     * Verifies that looking up a value by enum on a record that was parsed without a
     * header mapping fails with an {@link IllegalStateException}.
     *
     * <p>The CSV is parsed with the POSTGRESQL_CSV format, which does not declare any
     * headers. Because no header-to-index mapping exists, accessing a value by name
     * (here via {@code get((Enum<?>) null)}, which delegates to lookup by name) is
     * not allowed.</p>
     */
    @Test(timeout = 4000)
    public void getByEnumWithoutHeaderMappingThrowsIllegalState() throws Throwable {
        CSVFormat formatWithoutHeader = CSVFormat.POSTGRESQL_CSV;
        CSVParser parser = CSVParser.parse("*PAx}[gk", formatWithoutHeader);
        CSVRecord record = parser.nextRecord();

        try {
            record.get((Enum<?>) null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // No header mapping was specified, the record values can't be accessed by name
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
