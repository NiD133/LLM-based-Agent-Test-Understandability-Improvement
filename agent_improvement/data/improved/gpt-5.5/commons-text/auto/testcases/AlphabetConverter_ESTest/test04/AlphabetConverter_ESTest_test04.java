package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test04 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Integer repeatedCodePoint = new Integer(2);
        Integer[] duplicatedAlphabet = new Integer[4];
        duplicatedAlphabet[0] = repeatedCodePoint;
        duplicatedAlphabet[1] = repeatedCodePoint;
        duplicatedAlphabet[2] = repeatedCodePoint;
        duplicatedAlphabet[3] = repeatedCodePoint;

        AlphabetConverter converter = AlphabetConverter.createConverter(
                duplicatedAlphabet,
                duplicatedAlphabet,
                duplicatedAlphabet);

        boolean equalsNull = converter.equals((Object) null);
        assertFalse(equalsNull);
    }
}
