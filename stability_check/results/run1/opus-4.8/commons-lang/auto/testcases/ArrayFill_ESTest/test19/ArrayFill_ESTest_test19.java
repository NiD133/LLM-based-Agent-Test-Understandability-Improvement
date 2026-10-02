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
public class ArrayFill_ESTest_test19 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that filling a null boolean array is a no-op:
     * {@link ArrayFill#fill(boolean[], boolean)} returns the same null reference
     * it was given instead of throwing.
     */
    @Test(timeout = 4000)
    public void fillNullBooleanArrayReturnsNull() throws Throwable {
        boolean[] result = ArrayFill.fill((boolean[]) null, true);

        assertNull(result);
    }
}
