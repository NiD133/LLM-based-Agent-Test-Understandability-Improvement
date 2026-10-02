package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test02 extends DayOfYear_ESTest_scaffolding {

    /**
     * A DayOfYear obtained from the (mocked) system clock should report the
     * expected day-of-year value and be equal to itself (reflexivity of equals).
     */
    @Test(timeout = 4000)
    public void nowIsEqualToItselfAndHasExpectedValue() throws Throwable {
        DayOfYear today = DayOfYear.now();

        boolean equalToItself = today.equals(today);

        assertEquals(45, today.getValue());
        assertTrue(equalToItself);
    }
}
