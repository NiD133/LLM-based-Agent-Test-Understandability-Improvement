package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test01 extends Months_ESTest_scaffolding {

    /**
     * A {@link Months} instance is never equal to an arbitrary, non-{@code Months}
     * object, and creating it preserves the requested (negative) amount.
     */
    @Test(timeout = 4000)
    public void monthsIsNotEqualToPlainObjectAndKeepsAmount() throws Throwable {
        Months negativeMonths = Months.of(-1428);

        boolean equalsPlainObject = negativeMonths.equals(new Object());

        assertEquals(-1428, negativeMonths.getAmount());
        assertFalse(equalsPlainObject);
    }
}
