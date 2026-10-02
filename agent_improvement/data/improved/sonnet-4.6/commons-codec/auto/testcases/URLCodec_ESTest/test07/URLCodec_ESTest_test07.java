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
public class URLCodec_ESTest_test07 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a null string with a null charset returns null,
     * confirming URLCodec handles null inputs gracefully without throwing.
     */
    @Test(timeout = 4000)
    public void test07_decodeNullStringWithNullCharset_returnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String result = urlCodec.decode((String) null, (String) null);

        assertNull(result);
    }
}
