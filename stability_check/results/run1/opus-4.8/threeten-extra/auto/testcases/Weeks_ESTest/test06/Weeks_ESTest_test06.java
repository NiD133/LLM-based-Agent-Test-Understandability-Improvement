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

    /**
     * Verifies that {@link Weeks#addTo(Temporal)} throws a NullPointerException
     * when the temporal object to adjust is null.
     */
    @Test(timeout = 4000)
    public void addToNullTemporalThrowsNullPointerException() throws Throwable {
        Weeks oneWeek = Weeks.from(Period.ofWeeks(1));

        try {
            oneWeek.addTo((Temporal) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // addTo() dereferences the null temporal inside Weeks, so the
            // exception originates from the Weeks class and carries no message.
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
