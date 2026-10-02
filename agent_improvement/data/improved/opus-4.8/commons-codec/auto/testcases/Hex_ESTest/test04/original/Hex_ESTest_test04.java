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
public class Hex_ESTest_test04 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Hex hex0 = new Hex();
        ByteBuffer byteBuffer0 = ByteBuffer.allocate(1533);
        hex0.encode((Object) byteBuffer0);
        assertEquals(0, byteBuffer0.remaining());
        assertFalse(byteBuffer0.hasRemaining());
    }
}
