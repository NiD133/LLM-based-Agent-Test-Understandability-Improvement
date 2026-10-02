package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test04 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Base58 codec = new Base58();
        byte[] input = new byte[5];
        BaseNCodec.Context context = new BaseNCodec.Context();

        int eofOffset = (int) (byte) 95;
        int eofLength = (int) (byte) (-98);
        codec.decode(input, eofOffset, eofLength, context);

        int ignoredAfterEofOffset = (int) (byte) 88;
        int ignoredAfterEofLength = 71;
        codec.decode(input, ignoredAfterEofOffset, ignoredAfterEofLength, context);

        assertFalse(codec.isStrictDecoding());
    }
}
