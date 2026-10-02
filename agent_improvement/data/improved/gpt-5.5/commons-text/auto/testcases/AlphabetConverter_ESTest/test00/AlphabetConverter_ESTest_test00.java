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
public class AlphabetConverter_ESTest_test00 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        HashMap<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        Integer originalCodePoint = new Integer((-1147692044));

        AlphabetConverter emptyConverter = AlphabetConverter.createConverterFromMap(originalToEncoded);

        originalToEncoded.put(originalCodePoint, "A");
        AlphabetConverter converterWithSingleMapping = AlphabetConverter.createConverterFromMap(originalToEncoded);

        boolean convertersAreEqual = converterWithSingleMapping.equals(emptyConverter);

        assertFalse(convertersAreEqual);
        assertEquals(1, converterWithSingleMapping.getEncodedCharLength());
        assertEquals(1, emptyConverter.getEncodedCharLength());
    }
}
