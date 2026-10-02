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
public class NumericEntityUnescaper_ESTest_test4 extends NumericEntityUnescaper_ESTest_scaffolding {

    private static final String UNCHANGED_INCOMPLETE_HEX_ENTITY = "&#x\u0000\u0000\u0000\u0000\u0000\u0000\u0000";

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        NumericEntityUnescaper.OPTION[] noExplicitOptions = new NumericEntityUnescaper.OPTION[0];
        char[] incompleteHexEntity = new char[10];
        incompleteHexEntity[0] = '&';
        incompleteHexEntity[1] = '#';
        incompleteHexEntity[2] = 'x';
        CharBuffer input = CharBuffer.wrap(incompleteHexEntity);
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noExplicitOptions);

        String translated = unescaper.translate((CharSequence) input);

        assertEquals(UNCHANGED_INCOMPLETE_HEX_ENTITY, translated);
    }
}
