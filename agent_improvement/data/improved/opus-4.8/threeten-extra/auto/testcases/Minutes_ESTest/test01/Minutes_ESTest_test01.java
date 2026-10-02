package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test01 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that {@link Minutes#ofHours(int)} converts hours to minutes
     * (-899 hours = -899 * 60 = -53940 minutes), and that a {@code Minutes}
     * instance is never equal to a plain {@code Object}.
     */
    @Test(timeout = 4000)
    public void ofHours_convertsToMinutes_andNotEqualToPlainObject() throws Throwable {
        Minutes negativeNineHundredHours = Minutes.ofHours(-899);

        boolean equalsPlainObject = negativeNineHundredHours.equals(new Object());

        assertEquals(-53940, negativeNineHundredHours.getAmount());
        assertFalse(equalsPlainObject);
    }
}
