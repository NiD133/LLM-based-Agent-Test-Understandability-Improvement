package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test03 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Soundex simplifiedSoundex = Soundex.US_ENGLISH_SIMPLIFIED;
        Object nonStringInput = simplifiedSoundex;

        try {
            simplifiedSoundex.encode(nonStringInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Parameter supplied to Soundex encode is not of type java.lang.String
            //
            verifyException("org.apache.commons.codec.language.Soundex", e);
        }
    }
}
