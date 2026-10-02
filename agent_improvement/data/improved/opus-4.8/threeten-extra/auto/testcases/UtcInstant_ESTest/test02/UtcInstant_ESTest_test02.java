package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test02 extends UtcInstant_ESTest_scaffolding {

    /**
     * Verifies that a far-future {@link Instant} converts to a {@link UtcInstant}
     * whose ISO-8601 string carries the leading '+' year sign and full
     * nanosecond precision.
     */
    @Test(timeout = 4000)
    public void toString_forFarFutureInstant_usesExpandedYearAndNanos() throws Throwable {
        // An instant well beyond year 9999, so the ISO year is rendered with a '+' prefix.
        Instant farFutureSecond = MockInstant.ofEpochSecond(86400000000000L);
        // Back up 3 nanoseconds to land on the .999999997 fraction.
        Instant farFutureInstant = MockInstant.minusNanos(farFutureSecond, 3L);

        UtcInstant utcInstant = UtcInstant.of(farFutureInstant);
        String isoText = utcInstant.toString();

        assertNotNull(isoText);
        assertEquals("+2739877-01-02T23:59:59.999999997Z", isoText);
    }
}
