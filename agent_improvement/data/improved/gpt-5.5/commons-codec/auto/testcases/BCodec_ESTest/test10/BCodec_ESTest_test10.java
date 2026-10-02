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
public class BCodec_ESTest_test10 extends BCodec_ESTest_scaffolding {

    private static final String MALFORMED_ENCODED_CONTENT = ")1Y'}:,$Nj&:wqC";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BCodec codec = new BCodec();

        try {
            codec.decode((Object) MALFORMED_ENCODED_CONTENT);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            //
            // RFC 1522 violation: malformed encoded content
            //
            verifyException("org.apache.commons.codec.net.RFC1522Codec", exception);
        }
    }
}
