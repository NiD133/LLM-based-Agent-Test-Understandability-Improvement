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
     * A BCodec built with only a Charset should adopt the default, lenient
     * decoding policy. Therefore isStrictDecoding() must report false.
     */
    @Test(timeout = 4000)
    public void strictDecodingIsFalseWhenUsingDefaultPolicy() throws Throwable {
        BCodec bCodec = new BCodec(Charset.defaultCharset());

        boolean strictDecoding = bCodec.isStrictDecoding();

        assertFalse("BCodec created without an explicit policy should decode leniently",
                strictDecoding);
    }
}
