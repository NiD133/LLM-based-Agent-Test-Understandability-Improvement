package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.temporal.ChronoField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test17 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter only supports the QUARTER_OF_YEAR field; every {@link ChronoField}
     * (such as YEAR_OF_ERA) is explicitly unsupported, so isSupported returns false.
     */
    @Test(timeout = 4000)
    public void isSupportedReturnsFalseForChronoField() throws Throwable {
        Quarter fourthQuarter = Quarter.of(4);
        assertEquals(Quarter.Q4, fourthQuarter);

        boolean supportsYearOfEra = fourthQuarter.isSupported(ChronoField.YEAR_OF_ERA);

        assertFalse(supportsYearOfEra);
    }
}
