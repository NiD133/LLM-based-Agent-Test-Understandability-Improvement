package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test05 extends Nysiis_ESTest_scaffolding {

    /**
     * Encoding an Object that is not a String must fail: {@link Nysiis#encode(Object)}
     * only accepts String input and throws an EncoderException for anything else.
     */
    @Test(timeout = 4000)
    public void encodeNonStringObjectThrowsException() throws Throwable {
        Nysiis nysiis = new Nysiis();
        Object nonStringInput = new Object();

        try {
            nysiis.encode(nonStringInput);
            fail("Expected an exception because the input is not a String");
        } catch (Exception e) {
            // Message: "Parameter supplied to Nysiis encode is not of type java.lang.String"
            verifyException("org.apache.commons.codec.language.Nysiis", e);
        }
    }
}
