package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test01 extends Hours_ESTest_scaffolding {

    /**
     * Two {@link Hours} amounts holding different numbers of hours should not be
     * considered equal, regardless of which side initiates the comparison.
     */
    @Test(timeout = 4000)
    public void zeroHoursIsNotEqualToTwoHours() throws Throwable {
        Hours zeroHours = Hours.from(Duration.ZERO);
        Hours twoHours = Hours.of(2);

        assertEquals(0, zeroHours.getAmount());
        assertEquals(2, twoHours.getAmount());

        assertFalse("2 hours should not equal 0 hours", twoHours.equals(zeroHours));
        assertFalse("0 hours should not equal 2 hours", zeroHours.equals(twoHours));
    }
}
