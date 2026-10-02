package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NumericEntityUnescaper_ESTest_test3 extends NumericEntityUnescaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        char[] incompleteHexEntity = new char[7];
        incompleteHexEntity[0] = '&';
        incompleteHexEntity[1] = '#';
        incompleteHexEntity[2] = 'X';

        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);
        CharBuffer input = CharBuffer.wrap(incompleteHexEntity);

        String translated = unescaper.translate((CharSequence) input);

        assertEquals("&#X\u0000\u0000\u0000\u0000", translated);
    }
}
