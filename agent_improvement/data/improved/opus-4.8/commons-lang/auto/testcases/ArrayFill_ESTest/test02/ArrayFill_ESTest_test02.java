package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test02 extends ArrayFill_ESTest_scaffolding {

    /**
     * When the generator function is null, {@link ArrayFill#fill(Object[], FailableIntFunction)}
     * leaves the array untouched and returns the very same array instance.
     */
    @Test(timeout = 4000)
    public void fillWithNullGeneratorReturnsSameArrayInstance() throws Throwable {
        Object[] input = new Object[2];
        FailableIntFunction<Object, Throwable> nullGenerator = null;

        Object[] result = ArrayFill.fill(input, nullGenerator);

        assertSame("fill must return the same array instance it was given", input, result);
    }
}
