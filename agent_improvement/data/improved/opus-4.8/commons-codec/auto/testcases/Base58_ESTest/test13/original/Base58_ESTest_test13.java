package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test13 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Base58.Builder base58_Builder0 = Base58.builder();
        assertNotNull(base58_Builder0);
    }
}
