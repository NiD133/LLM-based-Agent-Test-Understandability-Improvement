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
public class Minutes_ESTest_test06 extends Minutes_ESTest_scaffolding {

    /**
     * The number of minutes between an instant and itself is zero, and adding
     * that zero amount back to the instant leaves the result unchanged.
     */
    @Test(timeout = 4000)
    public void betweenSameInstantIsZeroMinutes() throws Throwable {
        OffsetDateTime instant = MockOffsetDateTime.now();

        Minutes minutesBetween = Minutes.between(instant, instant);
        minutesBetween.addTo(instant);

        assertTrue(minutesBetween.isZero());
    }
}
