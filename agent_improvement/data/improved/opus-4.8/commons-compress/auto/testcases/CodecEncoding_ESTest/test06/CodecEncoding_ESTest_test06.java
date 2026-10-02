package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test06 extends CodecEncoding_ESTest_scaffolding {

    /**
     * The CHAR3 codec is not one of the canonical codecs, so its specifier is
     * not a simple index. Instead it is encoded as a BHSD codec, whose first
     * specifier byte is the marker value 116.
     */
    @Test(timeout = 4000)
    public void specifierForNonCanonicalChar3CodecIsBhsdMarker() throws Throwable {
        BHSDCodec char3Codec = Codec.CHAR3;

        int specifier = CodecEncoding.getSpecifierForDefaultCodec(char3Codec);

        assertEquals(116, specifier);
    }
}
