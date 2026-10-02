package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test08 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that requesting the current Julian date with a null clock fails fast
     * with a NullPointerException. The implementation delegates to JulianDate.now(clock),
     * which rejects the null argument via Objects.requireNonNull(clock, "clock").
     */
    @Test(timeout = 4000)
    public void dateNow_withNullClock_throwsNullPointerException() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();
        Clock nullClock = null;

        try {
            julianChronology.dateNow(nullClock);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null check originates from java.util.Objects (message: "clock").
            verifyException("java.util.Objects", e);
        }
    }
}
