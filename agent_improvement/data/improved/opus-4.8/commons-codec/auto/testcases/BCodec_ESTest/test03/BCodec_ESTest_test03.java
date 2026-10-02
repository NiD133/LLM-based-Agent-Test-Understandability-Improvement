package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test03 extends BCodec_ESTest_scaffolding {

    /**
     * BCodec.encode(Object) only supports String values. Passing any other
     * object type (here, the codec instance itself) must raise an exception
     * reporting that the object cannot be encoded.
     */
    @Test(timeout = 4000)
    public void encodeRejectsNonStringObject() throws Throwable {
        BCodec bCodec = new BCodec();

        try {
            bCodec.encode((Object) bCodec);
            fail("Expected an exception: BCodec instances cannot be encoded using BCodec");
        } catch (Exception e) {
            // Message: "Objects of type org.apache.commons.codec.net.BCodec cannot be encoded using BCodec"
            verifyException("org.apache.commons.codec.net.BCodec", e);
        }
    }
}
