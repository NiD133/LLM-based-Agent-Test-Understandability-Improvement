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
public class BCodec_ESTest_test02 extends BCodec_ESTest_scaffolding {

    /**
     * BCodec(Charset) uses the default LENIENT decoding policy,
     * so isStrictDecoding() must return false.
     */
    @Test(timeout = 4000)
    public void test_isStrictDecoding_returnsFalse_whenConstructedWithCharsetOnly() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        BCodec bCodec = new BCodec(defaultCharset);

        boolean isStrict = bCodec.isStrictDecoding();

        assertFalse(isStrict);
    }
}
