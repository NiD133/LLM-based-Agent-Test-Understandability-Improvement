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
     * Verifies that {@link RefinedSoundex#encode(Object)} rejects an argument
     * that is not a {@link String}. The contract states it must throw an
     * EncoderException whose message reports the invalid parameter type.
     */
    @Test(timeout = 4000)
    public void encodeNonStringObjectThrowsException() throws Throwable {
        RefinedSoundex refinedSoundex = new RefinedSoundex();
        Object nonStringArgument = new Object();

        try {
            refinedSoundex.encode(nonStringArgument);
            fail("Expected an exception because the argument is not a String");
        } catch (Exception e) {
            // Message: "Parameter supplied to RefinedSoundex encode is not of type java.lang.String"
            verifyException("org.apache.commons.codec.language.RefinedSoundex", e);
        }
    }
}
