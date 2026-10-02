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

    // The Modified Julian Day corresponding to the fixed instant returned by MockInstant.now()
    private static final long EXPECTED_MJD_FOR_MOCKED_NOW = 56702L;

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Instant mockedNow = MockInstant.now();
        UtcInstant utcInstant = UtcInstant.of(mockedNow);

        // Converting UtcInstant back to Instant must round-trip to the original value
        Instant roundTripped = utcInstant.toInstant();
        assertEquals(mockedNow, roundTripped);

        // The mocked "now" falls on the expected Modified Julian Day
        assertEquals(EXPECTED_MJD_FOR_MOCKED_NOW, utcInstant.getModifiedJulianDay());
    }
}
