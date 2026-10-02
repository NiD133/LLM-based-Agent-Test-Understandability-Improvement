package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test06 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding a null Object input returns null,
     * as URLCodec.encode(Object) must propagate null through without error.
     */
    @Test(timeout = 4000)
    public void test_encodeObject_withNullInput_returnsNull() throws Throwable {
        URLCodec codec = new URLCodec();

        Object encodedResult = codec.encode((Object) null);

        assertNull(encodedResult);
    }
}
