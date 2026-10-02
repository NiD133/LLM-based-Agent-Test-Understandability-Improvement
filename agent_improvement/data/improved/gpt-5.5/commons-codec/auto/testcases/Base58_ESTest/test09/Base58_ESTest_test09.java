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
        final Base58.Builder builder = new Base58.Builder();
        final byte[] customEncodeTable = new byte[3];

        final Base58.Builder returnedBuilder = builder.setEncodeTable(customEncodeTable);

        assertSame(returnedBuilder, builder);
    }
}
