package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test29 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that ofHours converts hours to minutes correctly and that
     * compareTo returns 0 when an instance is compared to itself.
     *
     * -8 hours * 60 = -480 minutes; compareTo(self) must always be 0.
     */
    @Test(timeout = 4000)
    public void test_ofHours_negativeSelf_compareToSelfIsZeroAndAmountIsConvertedMinutes() throws Throwable {
        // -8 hours should be stored as -480 minutes (8 * 60)
        Minutes negativeEightHours = Minutes.ofHours(-8);

        // Comparing an instance to itself must return 0 (equal ordering)
        int comparisonResult = negativeEightHours.compareTo(negativeEightHours);

        assertEquals("ofHours(-8) should yield -480 minutes", -480, negativeEightHours.getAmount());
        assertEquals("compareTo(self) must be 0", 0, comparisonResult);
    }
}
