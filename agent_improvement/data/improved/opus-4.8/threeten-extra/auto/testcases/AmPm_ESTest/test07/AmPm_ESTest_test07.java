package org.threeten.extra;

import static org.junit.Assert.assertFalse;

import java.time.temporal.TemporalField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test07 extends AmPm_ESTest_scaffolding {

    /**
     * A null field is never supported: {@link AmPm#isSupported(TemporalField)}
     * is documented to return false when the field is null.
     */
    @Test(timeout = 4000)
    public void isSupportedReturnsFalseForNullField() throws Throwable {
        AmPm am = AmPm.AM;

        boolean supported = am.isSupported((TemporalField) null);

        assertFalse(supported);
    }
}
