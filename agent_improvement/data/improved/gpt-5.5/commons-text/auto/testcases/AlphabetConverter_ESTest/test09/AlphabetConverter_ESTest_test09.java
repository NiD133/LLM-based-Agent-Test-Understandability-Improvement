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
public class AlphabetConverter_ESTest_test09 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        HashMap<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        Integer originalCodePoint = new Integer(1);
        String encodedValue = "qor_5~yr2yEtVdG{";

        originalToEncoded.put(originalCodePoint, encodedValue);
        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(originalToEncoded);

        assertEquals(1, originalToEncoded.size());
        String decodedValue = converter.decode(encodedValue);
        assertEquals("\u0001", decodedValue);
    }
}
