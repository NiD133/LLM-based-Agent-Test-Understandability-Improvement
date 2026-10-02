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
     * Verifies cross-encoding behaviour: encoding an ASCII string as UTF-16LE bytes,
     * then reinterpreting those raw bytes as UTF-8 produces a string where every
     * original character is followed by a null byte (U+0000).
     *
     * UTF-16LE stores each ASCII character as two bytes: the character byte followed by
     * 0x00. When these bytes are passed to a UTF-8 decoder, each ASCII byte decodes as
     * itself, while each 0x00 byte decodes as the null character U+0000.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        String input = "[NQ.38g~9u=YGOxnW";

        // Encode using UTF-16LE: each ASCII character becomes (char_byte, 0x00)
        byte[] utf16LeBytes = StringUtils.getBytesUtf16Le(input);

        // Reinterpret the UTF-16LE byte sequence as UTF-8.
        // The 0x00 padding bytes from UTF-16LE survive as U+0000 null characters.
        String reinterpretedAsUtf8 = StringUtils.newStringUtf8(utf16LeBytes);

        // UTF-16LE turns "[NQ.38g~9u=YGOxnW" (18 chars) into 36 bytes.
        // Each original character survives, interleaved with a null character ('\0').
        // Build the expected value with explicit null chars to make the pattern clear.
        char NULL = '\0';
        String expectedWithNullsInterleaved = ""
                + '[' + NULL + 'N' + NULL + 'Q' + NULL + '.' + NULL
                + '3' + NULL + '8' + NULL + 'g' + NULL + '~' + NULL
                + '9' + NULL + 'u' + NULL + '=' + NULL + 'Y' + NULL
                + 'G' + NULL + 'O' + NULL + 'x' + NULL + 'n' + NULL
                + 'W' + NULL;
        assertEquals(expectedWithNullsInterleaved, reinterpretedAsUtf8);
    }
}
