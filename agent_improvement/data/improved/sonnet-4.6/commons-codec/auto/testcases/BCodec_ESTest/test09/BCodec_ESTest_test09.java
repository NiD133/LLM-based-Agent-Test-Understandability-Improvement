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
public class BCodec_ESTest_test09 extends BCodec_ESTest_scaffolding {

    // BCodec.decode(Object) must return null when passed a null input, not throw an exception.
    @Test(timeout = 4000)
    public void test09_decodeNullObjectReturnsNull() throws Throwable {
        BCodec codec = new BCodec();
        Object result = codec.decode((Object) null);
        assertNull(result);
    }
}
