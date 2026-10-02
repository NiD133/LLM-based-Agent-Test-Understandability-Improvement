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
public class Base16_ESTest_test08 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Base16 base16_0 = new Base16(false);
        byte[] byteArray0 = new byte[2];
        BaseNCodec.Context baseNCodec_Context0 = new BaseNCodec.Context();
        baseNCodec_Context0.ibitWorkArea = (int) (byte) 106;
        base16_0.encode(byteArray0, 2741, (-2272), baseNCodec_Context0);
        base16_0.decode(byteArray0, (int) (byte) 85, (int) (byte) 85, baseNCodec_Context0);
        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
