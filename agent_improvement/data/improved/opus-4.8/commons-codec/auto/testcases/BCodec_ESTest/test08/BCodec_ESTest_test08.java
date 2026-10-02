package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test08 extends BCodec_ESTest_scaffolding {

    /**
     * BCodec.decode(Object) only supports {@code String} inputs. Passing any other
     * type of object (here, the codec itself) must raise a DecoderException whose
     * message reports that the object's type cannot be decoded.
     */
    @Test(timeout = 4000)
    public void decodeRejectsNonStringObject() throws Throwable {
        BCodec bCodec = new BCodec();

        try {
            bCodec.decode((Object) bCodec);
            fail("Expected a DecoderException because a BCodec object is not a String and cannot be decoded.");
        } catch (Exception e) {
            // Message: "Objects of type org.apache.commons.codec.net.BCodec cannot be decoded using BCodec"
            verifyException("org.apache.commons.codec.net.BCodec", e);
        }
    }
}
