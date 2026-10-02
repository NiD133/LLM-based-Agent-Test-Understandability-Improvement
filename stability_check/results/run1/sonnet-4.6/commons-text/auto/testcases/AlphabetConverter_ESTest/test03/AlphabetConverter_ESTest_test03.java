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
public class AlphabetConverter_ESTest_test03 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that AlphabetConverter.equals() is reflexive:
     * a converter instance must equal itself.
     */
    @Test(timeout = 4000)
    public void test03_equalsIsReflexive() throws Throwable {
        // Create a minimal single-character alphabet where source, encoding,
        // and do-not-encode sets all contain just the code point 0.
        Integer codePoint = new Integer(0);
        Integer[] singleCodePointAlphabet = new Integer[] { codePoint };

        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleCodePointAlphabet,   // original alphabet
                singleCodePointAlphabet,   // encoding alphabet
                singleCodePointAlphabet);  // characters to leave unencoded

        // An object must equal itself (reflexivity requirement of equals contract)
        boolean equalsItself = converter.equals(converter);
        assertTrue(equalsItself);
    }
}
