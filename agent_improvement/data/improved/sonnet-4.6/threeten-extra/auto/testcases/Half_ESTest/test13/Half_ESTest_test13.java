package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test13 extends Half_ESTest_scaffolding {

    /**
     * Verifies that Half does not support ChronoField instances.
     * According to the isSupported contract, only HALF_OF_YEAR is supported;
     * all ChronoField values (such as MILLI_OF_SECOND) must return false.
     */
    @Test(timeout = 4000)
    public void test_H2_doesNotSupport_ChronoField_milliOfSecond() throws Throwable {
        assertFalse(
            "Half.H2 should not support ChronoField.MILLI_OF_SECOND",
            Half.H2.isSupported(ChronoField.MILLI_OF_SECOND)
        );
    }
}
