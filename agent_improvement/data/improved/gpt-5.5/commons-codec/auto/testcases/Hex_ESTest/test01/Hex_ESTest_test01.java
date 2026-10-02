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
public class Hex_ESTest_test01 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Hex hexCodec = new Hex();
        Object nonCharArrayInput = new Object();

        try {
            hexCodec.decode(nonCharArrayInput);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            //
            // java.lang.Object cannot be cast to [C
            //
            verifyException("org.apache.commons.codec.binary.Hex", exception);
        }
    }
}
