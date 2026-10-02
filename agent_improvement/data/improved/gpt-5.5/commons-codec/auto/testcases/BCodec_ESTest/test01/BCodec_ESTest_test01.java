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
        Charset defaultCharset = Charset.defaultCharset();
        CodecPolicy strictDecodingPolicy = CodecPolicy.STRICT;
        BCodec codec = new BCodec(defaultCharset, strictDecodingPolicy);

        boolean strictDecodingEnabled = codec.isStrictDecoding();

        assertTrue(strictDecodingEnabled);
    }
}
