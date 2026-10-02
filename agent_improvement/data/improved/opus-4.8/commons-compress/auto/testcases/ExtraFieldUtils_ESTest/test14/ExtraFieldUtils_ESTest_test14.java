package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test14 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that the (deprecated) no-arg constructor of the
     * {@link ExtraFieldUtils} utility class can be invoked successfully.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstance() throws Throwable {
        ExtraFieldUtils extraFieldUtils = new ExtraFieldUtils();

        assertNotNull(extraFieldUtils);
    }
}
