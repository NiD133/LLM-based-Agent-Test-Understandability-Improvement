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
public class AlphabetConverter_ESTest_test05 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that equals(Object) returns false when the argument is not an
     * AlphabetConverter. Here the converter is compared against an Integer, so
     * the instanceof check in equals(...) should fail and yield false.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForNonConverterArgument() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];
        AlphabetConverter emptyConverter =
                AlphabetConverter.createConverter(emptyAlphabet, emptyAlphabet, emptyAlphabet);

        Object nonConverterValue = Integer.valueOf(2408);
        boolean isEqual = emptyConverter.equals(nonConverterValue);

        assertFalse(isEqual);
    }
}
