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
        Object[] arrayToFill = new Object[1];
        FailableIntFunction<Throwable, Throwable> nopGenerator = FailableIntFunction.nop();

        Object[] returnedArray = ArrayFill.fill(arrayToFill, (FailableIntFunction<?, Throwable>) nopGenerator);

        assertSame("ArrayFill.fill should return the same array instance that it fills", arrayToFill, returnedArray);
    }
}
