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
public class StringUtils_ESTest_test05 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        char[] twoNullCharacters = new char[2];
        CharBuffer wrappedNullCharacters = CharBuffer.wrap(twoNullCharacters);

        // The wrapped buffer contains two NUL chars, so it must not match this two-character sequence.
        boolean sequencesAreEqual = StringUtils.equals((CharSequence) wrappedNullCharacters, (CharSequence) "\u00DA\u0000");

        assertFalse(sequencesAreEqual);
    }
}
