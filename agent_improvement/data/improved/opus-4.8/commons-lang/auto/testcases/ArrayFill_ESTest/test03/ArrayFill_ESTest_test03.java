package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test03 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that filling an array with a generator function returns the very
     * same array instance that was passed in (the fill is performed in place).
     */
    @Test(timeout = 4000)
    public void fillWithGeneratorReturnsSameArrayInstance() throws Throwable {
        Object[] arrayToFill = new Object[1];
        // nop() is a generator that produces null for every index.
        FailableIntFunction<Throwable, Throwable> nullGenerator = FailableIntFunction.nop();

        Object[] filledArray = ArrayFill.fill(arrayToFill, (FailableIntFunction<?, Throwable>) nullGenerator);

        assertSame(arrayToFill, filledArray);
    }
}
