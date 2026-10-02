package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test27 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        Hours twoHours = Hours.of(2);
        Hours doubledHours = twoHours.plus((TemporalAmount) twoHours);

        assertTrue(twoHours.isPositive());
        assertEquals(4, doubledHours.getAmount());
    }
}
