package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test08 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that comparing a non-null CharSequence against a {@code null}
     * CharSequence returns {@code false}, as documented by
     * {@code StringUtils.equals(null, "abc") = false}.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenSecondArgumentIsNull() throws Throwable {
        CharBuffer nonNullSequence = CharBuffer.allocate(561);

        boolean result = StringUtils.equals((CharSequence) nonNullSequence, (CharSequence) null);

        assertFalse("A non-null CharSequence must not equal null", result);
    }
}
