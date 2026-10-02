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
     * Calling withModifiedJulianDay with the same day the instant already has
     * should yield an instant that is equal to the original.
     */
    @Test(timeout = 4000)
    public void withSameModifiedJulianDay_returnsEqualInstant() throws Throwable {
        long modifiedJulianDay = 13L;
        long nanoOfDay = 13L;
        UtcInstant original = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay);

        UtcInstant sameDay = original.withModifiedJulianDay(modifiedJulianDay);

        assertEquals("the Modified Julian Day should be unchanged",
                modifiedJulianDay, sameDay.getModifiedJulianDay());
        assertTrue("an instant with identical day and nano-of-day should be equal",
                sameDay.equals(original));
    }
}
