package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test25 extends Months_ESTest_scaffolding {

    /**
     * Adding ONE month to ZERO months yields ONE month.
     * Because Months.of(1) always returns the cached ONE singleton, the
     * result is the very same instance as Months.ONE.
     */
    @Test(timeout = 4000)
    public void addingOneToZeroReturnsOneSingleton() throws Throwable {
        Months one = Months.ONE;

        Months sum = Months.ZERO.plus((TemporalAmount) one);

        assertSame(one, sum);
    }
}
