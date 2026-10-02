package org.apache.commons.codec.digest;

import static org.junit.Assert.assertNotNull;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Md5Crypt_ESTest_test6 extends Md5Crypt_ESTest_scaffolding {

    /**
     * The (deprecated) public no-arg constructor of {@link Md5Crypt} should
     * create an instance successfully, even though the class only exposes
     * static helper methods.
     */
    @Test(timeout = 4000)
    public void defaultConstructorCreatesInstance() throws Throwable {
        Md5Crypt md5Crypt = new Md5Crypt();

        assertNotNull(md5Crypt);
    }
}
