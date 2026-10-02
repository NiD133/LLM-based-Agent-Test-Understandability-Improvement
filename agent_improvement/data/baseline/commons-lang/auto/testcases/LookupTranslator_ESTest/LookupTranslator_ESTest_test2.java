package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test2 extends LookupTranslator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        LookupTranslator lookupTranslator0 = new LookupTranslator((CharSequence[][]) null);
    }
}
