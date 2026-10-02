package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test03 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that an {@link Hours} value built from a zero {@link Duration}
     * is not equal to an unrelated, non-Hours object, while still correctly
     * reporting itself as zero.
     */
    @Test(timeout = 4000)
    public void zeroHoursIsNotEqualToArbitraryObject() throws Throwable {
        Hours zeroHours = Hours.from(Duration.ZERO);

        boolean equalsArbitraryObject = zeroHours.equals(new Object());

        assertFalse("Hours must not equal a non-Hours object", equalsArbitraryObject);
        assertTrue("Hours.from(Duration.ZERO) should represent zero hours", zeroHours.isZero());
    }
}
