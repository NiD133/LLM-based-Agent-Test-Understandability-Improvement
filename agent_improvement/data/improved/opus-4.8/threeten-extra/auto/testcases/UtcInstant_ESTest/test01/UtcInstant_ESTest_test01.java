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
public class UtcInstant_ESTest_test01 extends UtcInstant_ESTest_scaffolding {

    /**
     * Verifies that a {@link UtcInstant} created from an {@link Instant} renders as an
     * ISO-8601 string. The EvoSuite test runner mocks the system clock so that
     * {@code MockInstant.now()} always returns the fixed instant 2014-02-14T20:21:21.320Z,
     * which makes the expected string deterministic.
     */
    @Test(timeout = 4000)
    public void toStringReturnsIsoFormattedInstant() throws Throwable {
        Instant fixedNow = MockInstant.now();

        UtcInstant utcInstant = UtcInstant.of(fixedNow);
        String isoText = utcInstant.toString();

        assertNotNull(isoText);
        assertEquals("2014-02-14T20:21:21.320Z", isoText);
    }
}
