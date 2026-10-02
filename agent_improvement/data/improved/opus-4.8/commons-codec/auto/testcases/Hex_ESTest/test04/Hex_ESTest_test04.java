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

    /**
     * Encoding a ByteBuffer via {@link Hex#encode(Object)} consumes every
     * remaining byte: afterwards the buffer's position reaches its limit,
     * so {@code remaining()} is 0 and {@code hasRemaining()} is false.
     */
    @Test(timeout = 4000)
    public void encodeByteBufferConsumesAllRemainingBytes() throws Throwable {
        Hex hex = new Hex();
        ByteBuffer buffer = ByteBuffer.allocate(1533);

        hex.encode((Object) buffer);

        assertEquals(0, buffer.remaining());
        assertFalse(buffer.hasRemaining());
    }
}
