package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test04 extends ArrayFill_ESTest_scaffolding {

    /**
     * When the array passed to {@link ArrayFill#fill(Object[], FailableIntFunction)} is null,
     * the generator is never invoked and the method returns null unchanged.
     */
    @Test(timeout = 4000)
    public void fillNullObjectArrayWithGeneratorReturnsNull() throws Throwable {
        FailableIntFunction<Object, Throwable> generator = FailableIntFunction.nop();

        Object[] result = ArrayFill.fill((Object[]) null, (FailableIntFunction<?, Throwable>) generator);

        assertNull(result);
    }
}
