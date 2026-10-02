package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test0 extends LookupTranslator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        CharSequence[][] charSequenceArray0 = new CharSequence[2][6];
        CharSequence[] charSequenceArray1 = new CharSequence[4];
        charSequenceArray1[0] = (CharSequence) "FFFFFFFF";
        charSequenceArray1[1] = (CharSequence) "FFFFFFFF";
        charSequenceArray0[0] = charSequenceArray1;
        CharSequence[] charSequenceArray2 = new CharSequence[5];
        charSequenceArray2[0] = (CharSequence) "FFFFFFFF";
        charSequenceArray2[1] = (CharSequence) "FFFFFFFF";
        charSequenceArray2[3] = (CharSequence) "FFFFFCCE";
        charSequenceArray0[1] = charSequenceArray2;
        LookupTranslator lookupTranslator0 = new LookupTranslator(charSequenceArray0);
        String string0 = lookupTranslator0.translate(charSequenceArray2[3]);
        assertEquals("FFFFFCCE", string0);
    }
}
