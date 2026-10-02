package org.threeten.extra;

import static org.junit.Assert.assertFalse;

import java.time.temporal.TemporalField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test14 extends Half_ESTest_scaffolding {

    /**
     * A null field is never supported, so {@link Half#isSupported(TemporalField)}
     * should return {@code false} when given {@code null}.
     */
    @Test(timeout = 4000)
    public void isSupported_returnsFalse_forNullField() throws Throwable {
        Half half = Half.H2;

        boolean supported = half.isSupported((TemporalField) null);

        assertFalse(supported);
    }
}
