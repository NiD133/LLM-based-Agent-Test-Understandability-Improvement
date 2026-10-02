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
     * Verifies that withModifiedJulianDay returns an equal instant when called
     * with the same MJD value as the original, and that the MJD is preserved correctly.
     */
    @Test(timeout = 4000)
    public void test_withModifiedJulianDay_sameDayReturnedEqualsOriginal() throws Throwable {
        long mjDay = 13L;
        long nanoOfDay = 13L;

        UtcInstant original = UtcInstant.ofModifiedJulianDay(mjDay, nanoOfDay);
        UtcInstant copy = original.withModifiedJulianDay(mjDay);

        assertEquals(mjDay, copy.getModifiedJulianDay());
        assertTrue(copy.equals(original));
    }
}
