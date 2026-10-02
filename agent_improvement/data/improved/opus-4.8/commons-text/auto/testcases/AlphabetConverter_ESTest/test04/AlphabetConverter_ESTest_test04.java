package org.apache.commons.text;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test04 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that a converter is never considered equal to {@code null},
     * matching the {@link Object#equals(Object)} contract.
     */
    @Test(timeout = 4000)
    public void equalsNullReturnsFalse() throws Throwable {
        // A single-letter alphabet (the same code point repeated) is enough to
        // build a valid converter for this comparison.
        Integer codePoint = Integer.valueOf(2);
        Integer[] alphabet = { codePoint, codePoint, codePoint, codePoint };

        AlphabetConverter converter =
                AlphabetConverter.createConverter(alphabet, alphabet, alphabet);

        boolean equalsNull = converter.equals((Object) null);

        assertFalse("A converter must not be equal to null", equalsNull);
    }
}
