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
public class AlphabetConverter_ESTest_test11 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Integer[] singleNullCodePointAlphabet = new Integer[1];
        Integer nullCodePoint = Integer.valueOf(0);
        singleNullCodePointAlphabet[0] = nullCodePoint;

        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleNullCodePointAlphabet,
                singleNullCodePointAlphabet,
                singleNullCodePointAlphabet);

        try {
            converter.decode("\u0000 -> 0\r\n");
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            //
            // Unexpected string without decoding ( ) in \u0000 -> 0\r
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
