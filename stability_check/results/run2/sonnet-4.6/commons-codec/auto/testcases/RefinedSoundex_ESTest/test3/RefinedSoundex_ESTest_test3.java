package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test3 extends RefinedSoundex_ESTest_scaffolding {

    // encode(Object) should throw EncoderException when the argument is not a String
    @Test(timeout = 4000)
    public void test_encodeNonStringObject_throwsEncoderException() throws Throwable {
        RefinedSoundex refinedSoundex = new RefinedSoundex();
        Object nonStringObject = new Object();
        try {
            refinedSoundex.encode(nonStringObject);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Parameter supplied to RefinedSoundex encode is not of type java.lang.String
            //
            verifyException("org.apache.commons.codec.language.RefinedSoundex", e);
        }
    }
}
