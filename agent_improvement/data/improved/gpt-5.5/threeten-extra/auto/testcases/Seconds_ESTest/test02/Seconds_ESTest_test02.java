package org.threeten.extra;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test02 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        boolean equalsItself = zeroSeconds.equals(zeroSeconds);

        assertTrue(equalsItself);
    }
}
