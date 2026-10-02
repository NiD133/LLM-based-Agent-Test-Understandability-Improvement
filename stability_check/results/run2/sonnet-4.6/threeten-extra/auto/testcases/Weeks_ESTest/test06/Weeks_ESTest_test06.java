package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Period;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test06 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_addTo_nullTemporal_throwsNullPointerException() throws Throwable {
        Weeks oneWeek = Weeks.from(Period.ofWeeks(1));

        try {
            oneWeek.addTo((Temporal) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
