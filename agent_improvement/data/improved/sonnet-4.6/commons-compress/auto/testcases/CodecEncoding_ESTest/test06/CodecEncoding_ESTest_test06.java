package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test06 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Codec.CHAR3 is not in the canonical codec table, so its specifier
        // is 116 — the escape byte indicating an arbitrary BHSDCodec encoding.
        BHSDCodec char3Codec = Codec.CHAR3;
        int specifier = CodecEncoding.getSpecifierForDefaultCodec(char3Codec);
        assertEquals(116, specifier);
    }
}
