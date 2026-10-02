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
public class BCodec_ESTest_test04 extends BCodec_ESTest_scaffolding {

    /**
     * Encoding a null Object should return null without throwing an exception.
     */
    @Test(timeout = 4000)
    public void test_encodeNullObject_returnsNull() throws Throwable {
        BCodec bCodec = new BCodec();
        Object result = bCodec.encode((Object) null);
        assertNull(result);
    }
}
