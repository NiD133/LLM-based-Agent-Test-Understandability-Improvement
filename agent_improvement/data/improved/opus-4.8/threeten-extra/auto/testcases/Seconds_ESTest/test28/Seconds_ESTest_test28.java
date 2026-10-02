package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test28 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that {@link Seconds#ofMinutes(int)} converts minutes to seconds
     * (60 seconds per minute), and that calling {@link Seconds#hashCode()} does
     * not alter the stored amount.
     */
    @Test(timeout = 4000)
    public void test28() throws Throwable {
        // -19 minutes should become -19 * 60 = -1140 seconds.
        Seconds negativeNineteenMinutes = Seconds.ofMinutes(-19);

        // hashCode() must be a read-only operation with no side effects.
        negativeNineteenMinutes.hashCode();

        assertEquals(-1140, negativeNineteenMinutes.getAmount());
    }
}
