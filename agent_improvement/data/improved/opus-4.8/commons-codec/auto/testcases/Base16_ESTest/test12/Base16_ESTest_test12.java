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

    /**
     * Verifies that a lower-case, lenient Base16 codec can be constructed, and
     * confirms the shared PEM chunk size constant defined by BaseNCodec.
     */
    @Test(timeout = 4000)
    public void constructLowerCaseLenientCodecAndCheckPemChunkSize() throws Throwable {
        final boolean useLowerCaseAlphabet = true;
        final CodecPolicy lenientDecodingPolicy = CodecPolicy.LENIENT;

        new Base16(useLowerCaseAlphabet, lenientDecodingPolicy);

        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
