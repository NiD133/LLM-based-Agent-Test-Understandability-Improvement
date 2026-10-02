package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test09 extends Days_ESTest_scaffolding {

    /**
     * Verifies that:
     * <ul>
     *   <li>{@link Days#ofWeeks(int)} converts weeks to days by multiplying by 7
     *       (-3380 weeks = -23660 days), and</li>
     *   <li>{@link Days#abs()} on the already-positive {@link Days#ONE} constant
     *       returns a value of 1 day.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Days negativeWeeksAsDays = Days.ofWeeks(-3380);
        assertEquals(-23660, negativeWeeksAsDays.getAmount());

        Days absoluteOfOne = Days.ONE.abs();
        assertEquals(1, absoluteOfOne.getAmount());
    }
}
