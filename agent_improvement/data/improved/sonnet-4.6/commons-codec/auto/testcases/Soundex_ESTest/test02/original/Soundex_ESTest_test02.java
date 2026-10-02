package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test02 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Soundex soundex0 = new Soundex("'NY8Wa^[4");
        // Undeclared exception!
        try {
            soundex0.encode("'NY8Wa^[4");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // The character is not mapped: N (index=13)
            //
            verifyException("org.apache.commons.codec.language.Soundex", e);
        }
    }
}
