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
public class Minutes_ESTest_test05 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that adding a non-zero {@link Minutes} amount to a temporal
     * returns a new temporal instance (the operation is immutable and does
     * not mutate the original), and that the source amount keeps its value.
     */
    @Test(timeout = 4000)
    public void addToReturnsNewTemporalAndLeavesAmountUnchanged() throws Throwable {
        // 3 hours is stored internally as 180 minutes.
        Minutes threeHours = Minutes.ofHours(3);
        ZonedDateTime startDateTime = MockZonedDateTime.now();

        Temporal adjustedDateTime = threeHours.addTo(startDateTime);

        // addTo must produce a new instance rather than modifying the input.
        assertNotSame(adjustedDateTime, startDateTime);
        // The Minutes amount itself is unaffected by addTo.
        assertEquals(180, threeHours.getAmount());
    }
}
