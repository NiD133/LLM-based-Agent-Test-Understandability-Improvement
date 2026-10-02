package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test03 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that:
     *  - {@code ofMinutes(-19)} converts minutes to seconds (-19 * 60 = -1140), and
     *  - {@code equals} returns false when compared against an object that is not a {@code Seconds}.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Seconds negativeNineteenMinutes = Seconds.ofMinutes(-19);

        Object nonSecondsObject = CharBuffer.allocate(0);
        boolean isEqualToNonSeconds = negativeNineteenMinutes.equals(nonSecondsObject);

        assertFalse(isEqualToNonSeconds);
        assertEquals(-1140, negativeNineteenMinutes.getAmount());
    }
}
