package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test09 extends Days_ESTest_scaffolding {

    /**
     * Verifies that:
     * <ul>
     *   <li>{@code ofWeeks} converts weeks to days by multiplying by 7
     *       (-3380 weeks * 7 = -23660 days), and</li>
     *   <li>{@code abs()} on the {@code Days.ONE} constant leaves the
     *       already-positive value of 1 unchanged.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Days negativeWeeks = Days.ofWeeks(-3380);
        assertEquals(-23660, negativeWeeks.getAmount());

        Days absoluteOfOne = Days.ONE.abs();
        assertEquals(1, absoluteOfOne.getAmount());
    }
}
