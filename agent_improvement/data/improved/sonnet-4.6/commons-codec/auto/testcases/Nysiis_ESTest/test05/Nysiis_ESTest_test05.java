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
     * Nysiis.encode(Object) must reject non-String arguments with an EncoderException.
     * The generic Object encoder interface only accepts String values; passing any
     * other type is a programming error that should be signalled immediately.
     */
    @Test(timeout = 4000)
    public void test05_encodeNonStringObject_throwsEncoderException() throws Throwable {
        Nysiis nysiis = new Nysiis();
        Object nonStringInput = new Object();

        try {
            nysiis.encode(nonStringInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // EncoderException: "Parameter supplied to Nysiis encode is not of type java.lang.String"
            verifyException("org.apache.commons.codec.language.Nysiis", e);
        }
    }
}
