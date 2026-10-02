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

    /**
     * Verifies that calling {@link UtcInstant#withModifiedJulianDay(long)} with the
     * same Modified Julian Day the instant already has yields an instant that is
     * equal to the original (equality is based on both the MJD and the nano-of-day).
     */
    @Test(timeout = 4000)
    public void withSameModifiedJulianDay_producesEqualInstant() throws Throwable {
        long modifiedJulianDay = 13L;
        long nanoOfDay = 13L;

        UtcInstant original = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay);
        UtcInstant sameDayCopy = original.withModifiedJulianDay(modifiedJulianDay);

        assertEquals("Modified Julian Day should be unchanged",
                modifiedJulianDay, sameDayCopy.getModifiedJulianDay());
        assertTrue("Copy with the same MJD should equal the original",
                sameDayCopy.equals(original));
    }
}
