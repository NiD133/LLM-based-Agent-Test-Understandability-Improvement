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
public class ArrayFill_ESTest_test03 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Arrange: a single-element array and a no-op generator that returns null for every index
        Object[] inputArray = new Object[1];
        FailableIntFunction<Throwable, Throwable> noOpGenerator = FailableIntFunction.nop();

        // Act: fill the array using the no-op generator
        Object[] resultArray = ArrayFill.fill(inputArray, (FailableIntFunction<?, Throwable>) noOpGenerator);

        // Assert: fill() must return the same array instance it received
        assertSame(resultArray, inputArray);
    }
}
