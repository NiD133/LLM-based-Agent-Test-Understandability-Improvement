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
        NumericEntityUnescaper.OPTION[] noExplicitOptions = new NumericEntityUnescaper.OPTION[0];

        char[] textWithUnterminatedNumericEntity = new char[7];
        textWithUnterminatedNumericEntity[4] = '&';
        textWithUnterminatedNumericEntity[5] = '#';
        textWithUnterminatedNumericEntity[6] = '3';
        CharBuffer input = CharBuffer.wrap(textWithUnterminatedNumericEntity);

        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noExplicitOptions);
        String translated = unescaper.translate((CharSequence) input);

        assertEquals("\u0000\u0000\u0000\u0000&#3", translated);
    }
}
