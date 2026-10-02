package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test19 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that {@link AlphabetConverter#createConverter} rejects a
     * 'do not encode' code point that is absent from the original alphabet.
     *
     * <p>Here the original alphabet contains only {@code null} entries, while
     * the 'do not encode' list contains the code point 2 (''). Since the
     * original alphabet does not contain 2, an IllegalArgumentException is
     * expected.</p>
     */
    @Test(timeout = 4000)
    public void createConverterRejectsDoNotEncodeMissingFromOriginal() throws Throwable {
        // Original alphabet: three (null) code points -> effectively no usable letters.
        Integer[] original = new Integer[3];

        // Encoding and 'do not encode' arrays both contain the code point 2 at index 0.
        Integer[] encodingAndDoNotEncode = new Integer[8];
        encodingAndDoNotEncode[0] = Integer.valueOf(2);

        try {
            AlphabetConverter.createConverter(
                    original, encodingAndDoNotEncode, encodingAndDoNotEncode);
            fail("Expected IllegalArgumentException because the original alphabet "
                    + "does not contain the 'do not encode' code point '\\u0002'");
        } catch (IllegalArgumentException e) {
            // Message: "Can not use 'do not encode' list because original alphabet does not contain ''"
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
