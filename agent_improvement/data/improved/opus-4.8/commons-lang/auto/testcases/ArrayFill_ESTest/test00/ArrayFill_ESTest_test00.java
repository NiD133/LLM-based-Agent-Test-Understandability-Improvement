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
     * Verifies that {@link ArrayFill#fill(Object[], Object)} fills every element
     * of the array with the supplied value and returns the very same array
     * instance (fluent style), rather than a copy.
     *
     * <p>The fill value is cast to {@code Object} so that the
     * {@code fill(T[], T val)} overload is selected (filling each slot with the
     * value), not the generator-based {@code fill(T[], FailableIntFunction)}
     * overload.</p>
     */
    @Test(timeout = 4000)
    public void fill_withObjectValue_returnsSameArrayInstance() throws Throwable {
        Object[] arrayToFill = new Object[1];
        FailableIntFunction<Throwable, Throwable> fillValue = FailableIntFunction.nop();

        Object[] filledArray = ArrayFill.fill(arrayToFill, (Object) fillValue);

        assertSame(arrayToFill, filledArray);
    }
}
