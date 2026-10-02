package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test10 extends Base58_ESTest_scaffolding {

    /**
     * Building a Base58 codec via its Builder must succeed, and the inherited
     * MIME chunk-size constant should keep its documented value of 76.
     */
    @Test(timeout = 4000)
    public void builderCreatesCodecAndMimeChunkSizeIsSeventySix() throws Throwable {
        Base58.Builder builder = new Base58.Builder();
        Base58 codec = builder.get();

        assertEquals(76, BaseNCodec.MIME_CHUNK_SIZE);
    }
}
