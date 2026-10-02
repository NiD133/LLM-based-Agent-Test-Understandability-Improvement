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

    // BCodec.encode(Object) must return null when given a null input
    @Test(timeout = 4000)
    public void test04_encodeNullObjectReturnsNull() throws Throwable {
        BCodec bCodec = new BCodec();
        Object encodedResult = bCodec.encode((Object) null);
        assertNull(encodedResult);
    }
}
