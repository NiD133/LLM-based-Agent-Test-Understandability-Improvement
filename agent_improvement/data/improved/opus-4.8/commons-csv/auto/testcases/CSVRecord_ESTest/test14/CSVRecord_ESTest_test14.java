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
     * A record created without a parser (null) has no header mapping, so any
     * attempt to look up a value by name must fail. {@link CSVRecord#get(Enum)}
     * resolves the enum to its name and delegates to the name-based lookup,
     * which throws an IllegalStateException when no header mapping exists.
     */
    @Test(timeout = 4000)
    public void getByEnumWithoutHeaderMappingThrowsIllegalState() throws Throwable {
        String[] recordValues = new String[5];
        CSVRecord recordWithoutParser = new CSVRecord(
                (CSVParser) null,
                recordValues,
                "h%l{_WoZB#FA_}`",
                3988L,
                3988L,
                3988L);

        Enum<?> anyEnumKey = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;
        try {
            recordWithoutParser.get(anyEnumKey);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // No header mapping was specified, the record values can't be accessed by name
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
