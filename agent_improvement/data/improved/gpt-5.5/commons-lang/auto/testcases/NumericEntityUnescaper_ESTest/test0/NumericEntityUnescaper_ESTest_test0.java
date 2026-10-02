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
public class NumericEntityUnescaper_ESTest_test0 extends NumericEntityUnescaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        NumericEntityUnescaper.OPTION[] noExplicitOptions = new NumericEntityUnescaper.OPTION[0];

        char[] entityFollowedByDefaultNulls = new char[7];
        entityFollowedByDefaultNulls[0] = '&';
        entityFollowedByDefaultNulls[1] = '#';
        entityFollowedByDefaultNulls[2] = '2';
        entityFollowedByDefaultNulls[3] = ';';

        CharBuffer wrappedInput = CharBuffer.wrap(entityFollowedByDefaultNulls);
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noExplicitOptions);

        String translated = unescaper.translate((CharSequence) wrappedInput);

        assertEquals("\u0002\u0000\u0000\u0000", translated);
    }
}
