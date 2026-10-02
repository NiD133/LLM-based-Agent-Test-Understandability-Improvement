package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test11 extends BCodec_ESTest_scaffolding {

    /**
     * A BCodec built from a charset name should default to the lenient
     * decoding policy, so {@link BCodec#isStrictDecoding()} returns false.
     */
    @Test(timeout = 4000)
    public void strictDecodingIsDisabledByDefault() throws Throwable {
        BCodec bCodec = new BCodec("uS");

        assertFalse(bCodec.isStrictDecoding());
    }
}
