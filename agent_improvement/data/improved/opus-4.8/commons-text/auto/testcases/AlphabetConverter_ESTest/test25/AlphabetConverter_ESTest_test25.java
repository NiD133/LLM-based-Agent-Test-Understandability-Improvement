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
public class AlphabetConverter_ESTest_test25 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * A converter built from an empty mapping should default its encoded char
     * length to 1, and calling hashCode() on it must not affect that value.
     */
    @Test(timeout = 4000)
    public void converterFromEmptyMapHasDefaultEncodedCharLength() throws Throwable {
        HashMap<Integer, String> emptyOriginalToEncoded = new HashMap<Integer, String>();

        AlphabetConverter converter =
                AlphabetConverter.createConverterFromMap(emptyOriginalToEncoded);
        converter.hashCode();

        assertEquals(1, converter.getEncodedCharLength());
    }
}
