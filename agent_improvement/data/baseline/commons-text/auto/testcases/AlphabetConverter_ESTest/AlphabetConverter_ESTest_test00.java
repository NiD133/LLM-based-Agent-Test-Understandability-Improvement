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
        HashMap<Integer, String> hashMap0 = new HashMap<Integer, String>();
        Integer integer0 = new Integer((-1147692044));
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverterFromMap(hashMap0);
        hashMap0.put(integer0, "A");
        AlphabetConverter alphabetConverter1 = AlphabetConverter.createConverterFromMap(hashMap0);
        boolean boolean0 = alphabetConverter1.equals(alphabetConverter0);
        assertFalse(boolean0);
        assertEquals(1, alphabetConverter1.getEncodedCharLength());
        assertEquals(1, alphabetConverter0.getEncodedCharLength());
    }
}
