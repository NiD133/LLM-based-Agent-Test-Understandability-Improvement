package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test32 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_ofHours_toStringReturnsISOFormatWithTotalSeconds() throws Throwable {
        // 31 hours * 3600 seconds/hour = 111600 seconds
        Seconds thirtyOneHours = Seconds.ofHours(31);
        String isoString = thirtyOneHours.toString();
        assertEquals("PT111600S", isoString);
    }
}
