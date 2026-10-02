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
public class ArrayFill_ESTest_test04 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that fill() with a null array returns null, regardless of the generator function.
     * The nop generator (no-operation) is used to confirm null-safety is independent of generator logic.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        FailableIntFunction<Object, Throwable> nopGenerator = FailableIntFunction.nop();
        Object[] result = ArrayFill.fill((Object[]) null, (FailableIntFunction<?, Throwable>) nopGenerator);
        assertNull("fill() should return null when given a null array", result);
    }
}
