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
public class NumericEntityUnescaper_ESTest_test2 extends NumericEntityUnescaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        NumericEntityUnescaper.OPTION[] defaultOptions = new NumericEntityUnescaper.OPTION[0];

        char[] inputWithUnterminatedNumericEntity = new char[7];
        inputWithUnterminatedNumericEntity[4] = '&';
        inputWithUnterminatedNumericEntity[5] = '#';
        inputWithUnterminatedNumericEntity[6] = '3';
        CharBuffer input = CharBuffer.wrap(inputWithUnterminatedNumericEntity);

        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(defaultOptions);
        String translated = unescaper.translate((CharSequence) input);

        assertEquals("\u0000\u0000\u0000\u0000&#3", translated);
    }
}
