package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test01 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Base16 upperCaseBase16 = new Base16(false);

        byte[] encodedBytes = new byte[6];
        encodedBytes[5] = (byte) 68;

        BaseNCodec.Context decodeContext = new BaseNCodec.Context();
        upperCaseBase16.decode(encodedBytes, (int) (byte) 5, 1767, decodeContext);

        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
