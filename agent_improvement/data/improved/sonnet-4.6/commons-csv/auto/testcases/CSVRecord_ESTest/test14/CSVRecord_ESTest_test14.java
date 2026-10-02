package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test14 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that calling {@code get(Enum)} on a CSVRecord created without a parser
     * (and therefore without a header mapping) throws an {@link IllegalStateException}.
     * The enum's name is used as the column key, but without a header map there is no
     * way to resolve it to an index.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // A record with no associated parser has no header mapping
        String[] values = new String[5];
        long recordNumber = 3988L;
        long characterPosition = 3988L;
        long bytePosition = 3988L;
        CSVRecord record = new CSVRecord(
                (CSVParser) null, values, "h%l{_WoZB#FA_}`",
                recordNumber, characterPosition, bytePosition);

        // Any enum can be used; its name() is looked up in the (absent) header map
        Locale.FilteringMode filteringMode = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;

        // Expecting IllegalStateException because no header mapping was specified
        try {
            record.get((Enum<?>) filteringMode);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // No header mapping was specified, the record values can't be accessed by name
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
