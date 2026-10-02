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
     * Verifies that BaseNCodec.MIME_CHUNK_SIZE is 76, the standard MIME line length,
     * after constructing a default Base58 instance via its Builder.
     */
    @Test(timeout = 4000)
    public void test_mimeChunkSizeConstantIs76AfterDefaultBuilderConstruction() throws Throwable {
        Base58.Builder builder = new Base58.Builder();
        Base58 base58 = builder.get();
        assertEquals(76, BaseNCodec.MIME_CHUNK_SIZE);
    }
}
