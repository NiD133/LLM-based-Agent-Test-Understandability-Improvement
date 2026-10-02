package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test08 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        final Base58 base58 = new Base58();
        final byte[] decodedBytes = base58.decode("11");

        assertEquals(2, decodedBytes.length);
    }
}
