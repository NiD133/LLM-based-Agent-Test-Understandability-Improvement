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
public class UtcInstant_ESTest_test04 extends UtcInstant_ESTest_scaffolding {

    private static final long EXPECTED_MJD = 56702L;
    private static final long MODIFIED_NANO_OF_DAY = 1136L;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Create a UtcInstant from the deterministic mocked current time
        Instant now = MockInstant.now();
        UtcInstant original = UtcInstant.of(now);

        // Derive a copy that shares the same MJD but has a different nano-of-day
        UtcInstant modified = original.withNanoOfDay(MODIFIED_NANO_OF_DAY);

        // Both instants should reside on the same Modified Julian Day
        assertEquals("original instant should be on MJD 56702", EXPECTED_MJD, original.getModifiedJulianDay());
        assertEquals("modified instant should retain the same MJD", EXPECTED_MJD, modified.getModifiedJulianDay());

        // Instants that differ only in nano-of-day must not be equal, in either direction
        assertFalse("modified should not equal original", modified.equals(original));
        assertFalse("original should not equal modified", original.equals((Object) modified));
    }
}
