package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test06 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSetUtils#keep(String, String...)} returns {@code null}
     * when the source string is {@code null}, regardless of the character set provided.
     */
    @Test(timeout = 4000)
    public void keepReturnsNullWhenSourceStringIsNull() throws Throwable {
        String[] characterSet = new String[1];

        String result = CharSetUtils.keep((String) null, characterSet);

        assertNull(result);
    }
}
