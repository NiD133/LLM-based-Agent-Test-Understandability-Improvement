package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test18 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Hex hexCodec = new Hex();
        Object encodedText = hexCodec.encode((Object) "5TuU>'M{Jxu_");
        Object decodedBytes = hexCodec.decode(encodedText);

        try {
            hexCodec.decode(decodedBytes);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Illegal hexadecimal character 0x57 at index 1.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
