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

    // StringUtils.equals returns false when the second argument is null,
    // even if the first argument is a non-null CharSequence (here a CharBuffer).
    @Test(timeout = 4000)
    public void test08_equalsReturnsFalseWhenSecondArgIsNull() throws Throwable {
        CharBuffer nonNullCharSequence = CharBuffer.allocate(561);

        boolean result = StringUtils.equals((CharSequence) nonNullCharSequence, (CharSequence) null);

        assertFalse(result);
    }
}
