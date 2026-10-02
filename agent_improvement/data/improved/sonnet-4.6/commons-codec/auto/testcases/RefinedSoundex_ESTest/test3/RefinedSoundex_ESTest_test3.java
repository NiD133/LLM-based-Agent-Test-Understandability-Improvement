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

    /**
     * encode(Object) must reject non-String inputs with an EncoderException.
     * RefinedSoundex implements the generic Encoder interface but only handles
     * String values; passing any other Object type is a contract violation.
     */
    @Test(timeout = 4000)
    public void encode_withNonStringObject_throwsEncoderException() throws Throwable {
        RefinedSoundex refinedSoundex = new RefinedSoundex();
        Object nonStringInput = new Object();
        try {
            refinedSoundex.encode(nonStringInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Parameter supplied to RefinedSoundex encode is not of type java.lang.String
            //
            verifyException("org.apache.commons.codec.language.RefinedSoundex", e);
        }
    }
}
