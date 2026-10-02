package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test07 extends Base16_ESTest_scaffolding {

    /**
     * Decodes a slice of valid Base16 alphabet characters and verifies that a
     * default {@link Base16} instance uses the LENIENT decoding policy.
     *
     * <p>The encode table ('0'..'9','A'..'F') is reused here purely as a
     * convenient source of valid Base16 characters to feed into decode.</p>
     */
    @Test(timeout = 4000)
    public void decodeValidCharactersKeepsLenientPolicy() throws Throwable {
        Base16 base16 = new Base16();

        // The encode table holds only valid Base16 characters: 0-9 and A-F.
        byte[] validBase16Characters = base16.encodeTable;

        // Decode a 16-character window starting at offset 3.
        BaseNCodec.Context context = new BaseNCodec.Context();
        base16.decode(validBase16Characters, 3, 16, context);

        assertEquals(CodecPolicy.LENIENT, base16.getCodecPolicy());
    }
}
