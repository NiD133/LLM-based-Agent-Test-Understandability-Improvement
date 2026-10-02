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
     * Verifies that {@link AlphabetConverter#equals(Object)} is reflexive:
     * a converter is always equal to itself.
     */
    @Test(timeout = 4000)
    public void equalsReturnsTrueWhenComparedToItself() throws Throwable {
        // Use the same single-element alphabet ({0}) for the original, encoding
        // and "do not encode" code points so the converter can be created.
        Integer[] alphabet = { Integer.valueOf(0) };
        AlphabetConverter converter =
                AlphabetConverter.createConverter(alphabet, alphabet, alphabet);

        boolean isEqualToItself = converter.equals(converter);

        assertTrue(isEqualToItself);
    }
}
