package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test28 extends Minutes_ESTest_scaffolding {

    // Verifies that Minutes.ZERO.plus(zeroMinutes) returns the ZERO singleton itself.
    // Minutes.between identical instants yields zero minutes, and adding zero to ZERO
    // triggers the identity short-circuit in plus(), returning the same ZERO instance.
    @Test(timeout = 4000)
    public void test28() throws Throwable {
        OffsetDateTime now = MockOffsetDateTime.now();

        // Between identical instants the elapsed time is zero minutes
        Minutes zeroMinutes = Minutes.between(now, now);

        // Adding zero minutes to ZERO should return the ZERO singleton unchanged
        Minutes result = Minutes.ZERO.plus((TemporalAmount) zeroMinutes);

        assertSame(result, zeroMinutes);
    }
}
