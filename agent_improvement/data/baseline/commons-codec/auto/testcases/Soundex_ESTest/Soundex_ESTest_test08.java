package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test08 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Soundex soundex0 = Soundex.US_ENGLISH_GENEALOGY;
        int int0 = soundex0.US_ENGLISH.difference("01230120022455012623010202", "01230120022455012623010202");
        assertEquals(0, int0);
    }
}
