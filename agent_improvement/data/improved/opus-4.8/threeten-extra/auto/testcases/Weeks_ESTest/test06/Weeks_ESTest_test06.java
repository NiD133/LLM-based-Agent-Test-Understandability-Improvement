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
     * Verifies that calling {@link Weeks#addTo(Temporal)} with a null temporal
     * throws a {@link NullPointerException}, since addTo dereferences the
     * temporal argument when the amount is non-zero.
     */
    @Test(timeout = 4000)
    public void addTo_withNullTemporal_throwsNullPointerException() throws Throwable {
        Weeks oneWeek = Weeks.from(Period.ofWeeks(1));

        try {
            oneWeek.addTo((Temporal) null);
            fail("Expected a NullPointerException when adding to a null temporal");
        } catch (NullPointerException e) {
            // The exception carries no message and originates from Weeks.addTo.
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
