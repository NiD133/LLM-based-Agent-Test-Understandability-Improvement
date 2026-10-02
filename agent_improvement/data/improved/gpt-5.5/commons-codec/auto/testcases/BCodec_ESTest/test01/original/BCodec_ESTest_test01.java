package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test01 extends BCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Charset charset0 = Charset.defaultCharset();
        CodecPolicy codecPolicy0 = CodecPolicy.STRICT;
        BCodec bCodec0 = new BCodec(charset0, codecPolicy0);
        boolean boolean0 = bCodec0.isStrictDecoding();
        assertTrue(boolean0);
    }
}
