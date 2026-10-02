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
public class StringUtils_ESTest_test10 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#equals(CharSequence, CharSequence)} returns
     * {@code true} when a non-String CharSequence is compared against itself. Passing
     * the same reference for both arguments exercises the reference-equality
     * short-circuit (cs1 == cs2) inside the method.
     */
    @Test(timeout = 4000)
    public void equalsReturnsTrueWhenCharSequenceComparedToItself() throws Throwable {
        CharBuffer sameCharSequence = CharBuffer.wrap(new char[3]);

        boolean isEqual = StringUtils.equals((CharSequence) sameCharSequence, (CharSequence) sameCharSequence);

        assertTrue(isEqual);
    }
}
