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
        HashMap<Integer, String> hashMap0 = new HashMap<Integer, String>();
        Integer integer0 = new Integer(1);
        hashMap0.put(integer0, "qor_5~yr2yEtVdG{");
        AlphabetConverter alphabetConverter0 = AlphabetConverter.createConverterFromMap(hashMap0);
        assertEquals(1, hashMap0.size());
        String string0 = alphabetConverter0.decode("qor_5~yr2yEtVdG{");
        assertEquals("\u0001", string0);
    }
}
