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

    // Encoding a ByteBuffer via encode(Object) must consume all remaining bytes,
    // leaving the buffer's position at its limit (remaining() == 0).
    @Test(timeout = 4000)
    public void test_encodeObjectWithByteBuffer_consumesAllRemainingBytes() throws Throwable {
        Hex hex = new Hex();
        ByteBuffer buffer = ByteBuffer.allocate(1533);

        hex.encode((Object) buffer);

        assertEquals("Buffer should be fully consumed after encoding", 0, buffer.remaining());
        assertFalse("Buffer should have no bytes left after encoding", buffer.hasRemaining());
    }
}
