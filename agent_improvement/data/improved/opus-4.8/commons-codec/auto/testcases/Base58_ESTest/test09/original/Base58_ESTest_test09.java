package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test09 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Base58.Builder base58_Builder0 = new Base58.Builder();
        byte[] byteArray0 = new byte[3];
        Base58.Builder base58_Builder1 = base58_Builder0.setEncodeTable(byteArray0);
        assertSame(base58_Builder1, base58_Builder0);
    }
}
