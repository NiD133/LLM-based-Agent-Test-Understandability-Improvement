package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test06 extends UtcInstant_ESTest_scaffolding {

    /**
     * Calling withModifiedJulianDay with the instant's existing Modified Julian
     * Day should leave the value unchanged and yield an instant equal to the
     * original.
     */
    @Test(timeout = 4000)
    public void withSameModifiedJulianDayReturnsEqualInstant() throws Throwable {
        long modifiedJulianDay = 13L;
        long nanoOfDay = 13L;
        UtcInstant original = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay);

        UtcInstant unchanged = original.withModifiedJulianDay(modifiedJulianDay);

        assertEquals(modifiedJulianDay, unchanged.getModifiedJulianDay());
        assertTrue(unchanged.equals(original));
    }
}
