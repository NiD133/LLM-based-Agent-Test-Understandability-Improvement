package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test12 extends StringUtils_ESTest_scaffolding {

    /**
     * Encodes an ASCII string as UTF-16LE, then decodes the resulting bytes as UTF-8.
     *
     * In UTF-16LE every ASCII character is stored as its low byte followed by a
     * zero (high) byte. When those same bytes are read back as UTF-8, each pair
     * decodes to the original character followed by a U+0000 (NUL) character, so
     * the round-trip deliberately does NOT reproduce the original string: the
     * result is the input with a NUL inserted after every character (including a
     * trailing NUL after the final 'W').
     */
    @Test(timeout = 4000)
    public void testUtf16LeBytesDecodedAsUtf8InterleavesNulCharacters() throws Throwable {
        String asciiInput = "[NQ.38g~9u=YGOxnW";

        byte[] utf16LeBytes = StringUtils.getBytesUtf16Le(asciiInput);
        String decodedAsUtf8 = StringUtils.newStringUtf8(utf16LeBytes);

        // Expected = each input character followed by the NUL that came from the
        // UTF-16LE high byte.
        char nul = (char) 0;
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i < asciiInput.length(); i++) {
            expected.append(asciiInput.charAt(i)).append(nul);
        }
        assertEquals(expected.toString(), decodedAsUtf8);
    }
}
