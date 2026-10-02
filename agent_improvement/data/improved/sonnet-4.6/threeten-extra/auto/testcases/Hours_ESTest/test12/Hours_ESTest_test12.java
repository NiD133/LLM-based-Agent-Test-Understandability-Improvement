package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test12 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Hours.between the same instant is zero; multiplying zero by any scalar stays zero
        Instant sameInstant = MockInstant.ofEpochSecond(1137L);
        Hours zeroHours = Hours.between(sameInstant, sameInstant);
        Hours multipliedHours = zeroHours.multipliedBy(1);
        assertEquals(0, multipliedHours.getAmount());
    }
}
