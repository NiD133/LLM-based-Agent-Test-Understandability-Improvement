package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test14 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that the deprecated no-arg constructor of ExtraFieldUtils can be
     * instantiated without throwing an exception. ExtraFieldUtils is a utility
     * class whose constructor is retained for backwards compatibility.
     */
    @Test(timeout = 4000)
    public void test_deprecatedConstructor_canBeInstantiatedWithoutError() throws Throwable {
        ExtraFieldUtils extraFieldUtils = new ExtraFieldUtils();
        assertNotNull(extraFieldUtils);
    }
}
