package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test07 extends QCodec_ESTest_scaffolding {

    /**
     * QCodec.decode(Object) only accepts {@code String} arguments. Passing any
     * other type of object (here, the QCodec instance itself) must raise a
     * DecoderException stating that the object cannot be decoded using Q codec.
     */
    @Test(timeout = 4000)
    public void decodeRejectsNonStringObject() throws Throwable {
        QCodec qCodec = new QCodec();

        try {
            qCodec.decode((Object) qCodec);
            fail("Expected a DecoderException when decoding a non-String object");
        } catch (Exception e) {
            // Message: "Objects of type org.apache.commons.codec.net.QCodec
            //           cannot be decoded using Q codec"
            verifyException("org.apache.commons.codec.net.QCodec", e);
        }
    }
}
