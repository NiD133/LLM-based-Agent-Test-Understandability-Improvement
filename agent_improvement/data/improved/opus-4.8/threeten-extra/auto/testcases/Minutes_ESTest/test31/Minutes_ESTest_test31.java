package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test31 extends Minutes_ESTest_scaffolding {

    /**
     * The number of minutes between an instant and itself is zero,
     * regardless of which instant is used.
     */
    @Test(timeout = 4000)
    public void betweenSameInstantIsZeroMinutes() throws Throwable {
        OffsetDateTime sameInstant = MockOffsetDateTime.now();

        Minutes minutesBetween = Minutes.between(sameInstant, sameInstant);

        // getUnits() is exercised to confirm the amount exposes its supported unit.
        minutesBetween.getUnits();
        assertEquals(0, minutesBetween.getAmount());
    }
}
