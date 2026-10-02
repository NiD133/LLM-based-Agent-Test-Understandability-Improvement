package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test18 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that the (deprecated) public no-argument constructor of
     * {@link ArraySorter} can be invoked successfully and yields a non-null
     * instance.
     */
    @Test(timeout = 4000)
    public void testDefaultConstructorCreatesInstance() throws Throwable {
        ArraySorter arraySorter = new ArraySorter();

        assertNotNull(arraySorter);
    }
}
