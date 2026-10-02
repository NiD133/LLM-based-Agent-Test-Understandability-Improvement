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
public class StringUtils_ESTest_test09 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#equals(CharSequence, CharSequence)} returns
     * {@code false} when the first argument is {@code null} and the second is a
     * non-null {@link CharSequence}, rather than throwing a NullPointerException.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenFirstArgumentIsNull() throws Throwable {
        CharBuffer nonNullCharSequence = CharBuffer.allocate(561);

        boolean areEqual = StringUtils.equals((CharSequence) null, (CharSequence) nonNullCharSequence);

        assertFalse("null should not be considered equal to a non-null CharSequence", areEqual);
    }
}
