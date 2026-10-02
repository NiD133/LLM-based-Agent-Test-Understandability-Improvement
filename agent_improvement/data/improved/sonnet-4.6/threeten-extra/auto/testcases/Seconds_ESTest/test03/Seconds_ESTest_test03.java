package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test03 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03_equalsReturnsFalseForNonSecondsObject() throws Throwable {
        // A CharBuffer is not a Seconds instance, so equals() must return false
        CharBuffer nonSecondsObject = CharBuffer.allocate(0);

        // -19 minutes * 60 seconds/minute = -1140 seconds
        Seconds negativeNineteenMinutes = Seconds.ofMinutes(-19);

        boolean isEqual = negativeNineteenMinutes.equals(nonSecondsObject);

        assertFalse(isEqual);
        assertEquals(-1140, negativeNineteenMinutes.getAmount());
    }
}
