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
public class StringUtils_ESTest_test06 extends StringUtils_ESTest_scaffolding {

    /**
     * Reproduces the literal "[NQ.38g~9u=YGOxnW" with a null character (U+0000)
     * inserted between every pair of adjacent characters and appended at the end,
     * matching the EvoSuite-generated string exactly.
     */
    private static final String STRING_WITH_INTERLEAVED_NULLS = buildStringWithInterleavedNulls();

    private static String buildStringWithInterleavedNulls() {
        // The visible characters from the original EvoSuite literal,
        // separated by null characters.
        char[] visibleChars = {'[', 'N', 'Q', '.', '3', '8', 'g', '~', '9', 'u', '=', 'Y', 'G', 'O', 'x', 'n', 'W'};
        StringBuilder sb = new StringBuilder();
        sb.append(visibleChars[0]);
        for (int i = 1; i < visibleChars.length; i++) {
            sb.append((char) 0); // null character (U+0000)
            sb.append(visibleChars[i]);
        }
        sb.append((char) 0); // trailing null character
        return sb.toString();
    }

    @Test(timeout = 4000)
    public void test06_equalsReturnsTrueWhenStringAndCharBufferWrapSameContent() throws Throwable {
        // Wrap the same character sequence in a CharBuffer to get a non-String CharSequence.
        CharBuffer charBuffer = CharBuffer.wrap((CharSequence) STRING_WITH_INTERLEAVED_NULLS);

        // StringUtils.equals() must return true when a String and a CharBuffer hold the same characters.
        boolean result = StringUtils.equals((CharSequence) STRING_WITH_INTERLEAVED_NULLS, (CharSequence) charBuffer);

        assertTrue(result);
    }
}
