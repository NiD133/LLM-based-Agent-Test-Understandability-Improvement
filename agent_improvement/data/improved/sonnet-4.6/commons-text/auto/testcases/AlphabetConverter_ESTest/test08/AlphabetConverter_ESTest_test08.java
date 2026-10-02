package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test08 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // A converter built from an empty map has no character mappings
        HashMap<Integer, String> emptyEncodingMap = new HashMap<Integer, String>();
        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(emptyEncodingMap);

        // Encoding a null input returns null without throwing an exception
        converter.encode((String) null);

        // With an empty mapping, the default encoded character length is 1
        assertEquals(1, converter.getEncodedCharLength());
    }
}
