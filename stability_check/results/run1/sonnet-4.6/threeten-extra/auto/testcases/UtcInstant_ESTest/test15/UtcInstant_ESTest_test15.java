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
public class UtcInstant_ESTest_test15 extends UtcInstant_ESTest_scaffolding {

    // MJD 56702 corresponds to 2014-02-07 (days since the Modified Julian Day epoch 1858-11-17)
    private static final long MOCKED_NOW_MJD = 56702L;

    @Test(timeout = 4000)
    public void test_roundTripConversionFromInstantAndVerifyModifiedJulianDay() throws Throwable {
        Instant mockNow = MockInstant.now();

        UtcInstant utcInstant = UtcInstant.of(mockNow);
        Instant roundTripped = utcInstant.toInstant();

        // Converting Instant → UtcInstant → Instant should yield the same Instant
        assertTrue(roundTripped.equals((Object) mockNow));
        // The mocked current time falls on MJD 56702 (2014-02-07)
        assertEquals(MOCKED_NOW_MJD, utcInstant.getModifiedJulianDay());
    }
}
