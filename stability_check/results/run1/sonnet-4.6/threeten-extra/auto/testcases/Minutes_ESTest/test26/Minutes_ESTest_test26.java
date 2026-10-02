package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test26 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_zeroMinutes_toString_returnsIsoDuration() throws Throwable {
        String isoString = Minutes.ZERO.toString();
        assertEquals("PT0M", isoString);
    }
}
