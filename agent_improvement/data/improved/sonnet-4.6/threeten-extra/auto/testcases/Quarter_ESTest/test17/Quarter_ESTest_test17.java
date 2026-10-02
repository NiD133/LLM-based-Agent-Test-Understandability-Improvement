package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test17 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that Quarter.Q4 does not support ChronoField.YEAR_OF_ERA,
     * since Quarter only supports IsoFields.QUARTER_OF_YEAR.
     */
    @Test(timeout = 4000)
    public void test_Q4_doesNotSupportYearOfEraChronoField() throws Throwable {
        Quarter q4 = Quarter.of(4);
        assertEquals(Quarter.Q4, q4);

        boolean supportsYearOfEra = q4.isSupported(ChronoField.YEAR_OF_ERA);

        assertFalse(supportsYearOfEra);
    }
}
