package org.apache.commons.lang3;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test15 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that the public (deprecated) {@link EnumUtils} constructor can be
     * invoked. The constructor exists only so tools requiring a JavaBean instance
     * can create one; it produces a usable object.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        EnumUtils enumUtils = new EnumUtils();

        assertNotNull(enumUtils);
    }
}
