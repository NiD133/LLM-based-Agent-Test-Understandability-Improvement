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
public class AlphabetConverter_ESTest_test00 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        HashMap<Integer, String> encodingMap = new HashMap<Integer, String>();

        AlphabetConverter converterFromEmptyMap = AlphabetConverter.createConverterFromMap(encodingMap);

        Integer codePoint = Integer.valueOf(-1147692044);
        encodingMap.put(codePoint, "A");
        AlphabetConverter converterFromSingleEntryMap = AlphabetConverter.createConverterFromMap(encodingMap);

        boolean convertersAreEqual = converterFromSingleEntryMap.equals(converterFromEmptyMap);
        assertFalse(convertersAreEqual);
        assertEquals(1, converterFromSingleEntryMap.getEncodedCharLength());
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());
    }
}
