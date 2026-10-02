package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test13 extends Half_ESTest_scaffolding {

    /**
     * Half supports only the HALF_OF_YEAR field; every ChronoField is unsupported.
     * Here, MILLI_OF_SECOND is a ChronoField, so isSupported must return false.
     */
    @Test(timeout = 4000)
    public void isSupportedReturnsFalseForChronoField() throws Throwable {
        Half half = Half.H2;

        boolean supported = half.isSupported(ChronoField.MILLI_OF_SECOND);

        assertFalse(supported);
    }
}
