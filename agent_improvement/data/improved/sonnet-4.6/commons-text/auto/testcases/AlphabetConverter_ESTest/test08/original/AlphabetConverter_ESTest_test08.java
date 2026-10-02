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
public class AlphabetConverter_ESTest_test08 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        HashMap<Integer, String> hashMap0 = new HashMap<Integer, String>();
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverterFromMap(hashMap0);
        alphabetConverter0.encode((String) null);
        assertEquals(1, alphabetConverter0.getEncodedCharLength());
    }
}
