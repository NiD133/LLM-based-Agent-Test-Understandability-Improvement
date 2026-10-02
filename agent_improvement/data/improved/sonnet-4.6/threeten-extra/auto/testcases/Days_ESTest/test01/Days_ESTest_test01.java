package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test01 extends Days_ESTest_scaffolding {

    // -3386 weeks = -3386 * 7 = -23702 days; -23702 * -3386 = 80,254,972 days
    private static final int WEEKS_INPUT = -3386;
    private static final int MULTIPLIER = -3386;
    private static final int EXPECTED_PRODUCT_DAYS = 80254972;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Days negativeDays = Days.ofWeeks(WEEKS_INPUT);
        Days product = negativeDays.multipliedBy(MULTIPLIER);

        boolean negativeDaysEqualsProduct = negativeDays.equals(product);
        assertFalse(negativeDaysEqualsProduct);
        assertFalse(product.equals((Object) negativeDays));
        assertFalse(negativeDays.isPositive());
        assertEquals(EXPECTED_PRODUCT_DAYS, product.getAmount());
    }
}
