package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test07 extends Hours_ESTest_scaffolding {

    // Adding zero hours to a ZonedDateTime should return the exact same object unchanged.
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        ZonedDateTime now = MockZonedDateTime.now();
        Temporal result = Hours.ZERO.addTo(now);
        assertSame(result, now);
    }
}
