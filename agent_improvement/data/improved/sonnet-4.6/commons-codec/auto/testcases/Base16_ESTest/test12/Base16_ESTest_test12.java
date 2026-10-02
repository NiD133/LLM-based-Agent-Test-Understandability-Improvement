package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test12 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_pemChunkSizeIs64_whenLowerCaseLenientBase16IsCreated() throws Throwable {
        // Create a lower-case Base16 codec with lenient decoding policy
        Base16 lowerCaseLenientBase16 = new Base16(true, CodecPolicy.LENIENT);

        // PEM_CHUNK_SIZE is a constant inherited from BaseNCodec and must equal 64
        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
