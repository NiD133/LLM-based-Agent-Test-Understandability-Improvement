package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test06 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_withModifiedJulianDay_retainsSameDayAndEqualsOriginal() throws Throwable {
        // Create a UtcInstant at MJD 13 with 13 nanoseconds into the day
        UtcInstant original = UtcInstant.ofModifiedJulianDay(13L, 13L);

        // Calling withModifiedJulianDay with the same MJD should return an equal instance
        UtcInstant withSameDay = original.withModifiedJulianDay(13L);

        assertEquals(13L, withSameDay.getModifiedJulianDay());
        assertTrue(withSameDay.equals(original));
    }
}
