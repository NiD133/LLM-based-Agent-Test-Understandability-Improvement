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
public class NumericEntityUnescaper_ESTest_test1 extends NumericEntityUnescaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        char[] inputCharacters = new char[7];
        inputCharacters[0] = '&';
        inputCharacters[1] = '#';
        inputCharacters[2] = 'X';
        inputCharacters[3] = ';';

        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);
        CharBuffer input = CharBuffer.wrap(inputCharacters);

        String translated = unescaper.translate((CharSequence) input);

        assertEquals("&#X;\u0000\u0000\u0000", translated);
    }
}
