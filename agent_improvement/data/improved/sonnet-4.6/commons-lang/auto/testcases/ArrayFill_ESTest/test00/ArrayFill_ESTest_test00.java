package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Arrays;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test00 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that fill(T[] array, T val) returns the exact same array instance it was given.
     * The FailableIntFunction is cast to Object so the compiler resolves the value-fill overload
     * (fill(T[], T)) rather than the generator overload (fill(T[], FailableIntFunction)).
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Object[] array = new Object[1];
        // Cast to Object to select fill(T[] a, T val) overload, not the generator overload
        Object fillValue = (Object) FailableIntFunction.nop();
        Object[] filledArray = ArrayFill.fill(array, fillValue);
        assertSame(filledArray, array);
    }
}
