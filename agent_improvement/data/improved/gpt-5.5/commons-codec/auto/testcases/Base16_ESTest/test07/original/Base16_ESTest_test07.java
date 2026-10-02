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
public class Base16_ESTest_test07 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Base16 base16_0 = new Base16();
        byte[] byteArray0 = base16_0.encodeTable;
        BaseNCodec.Context baseNCodec_Context0 = new BaseNCodec.Context();
        base16_0.decode(byteArray0, 3, 16, baseNCodec_Context0);
        assertEquals(CodecPolicy.LENIENT, base16_0.getCodecPolicy());
    }
}
