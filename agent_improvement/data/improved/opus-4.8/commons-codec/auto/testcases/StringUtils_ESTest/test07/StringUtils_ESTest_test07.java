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
public class StringUtils_ESTest_test07 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#equals(CharSequence, CharSequence)} returns
     * {@code false} when the two CharSequences differ.
     *
     * <p>The first sequence is a {@link CharBuffer} whose text interleaves a NUL
     * (\\u0000) character after every visible character, so it is twice as long
     * as the plain comparison string and therefore cannot be equal to it.</p>
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForSequencesOfDifferentLength() throws Throwable {
        // 16 visible characters, each followed by a NUL (\\u0000) -> 32 chars total.
        CharBuffer nulInterleavedSequence = CharBuffer.wrap(
                (CharSequence) "[\\u0000N\\u0000Q\\u0000.\\u00003\\u00008\\u0000g\\u0000~\\u00009\\u0000u\\u0000=\\u0000Y\\u0000G\\u0000O\\u0000x\\u0000n\\u0000W\\u0000");

        // The same 16 visible characters, but without the interleaved NULs.
        String plainSequence = "[NQ.38g~9u=YGOxnW";

        boolean sequencesEqual =
                StringUtils.equals((CharSequence) nulInterleavedSequence, (CharSequence) plainSequence);

        assertFalse(sequencesEqual);
    }
}
