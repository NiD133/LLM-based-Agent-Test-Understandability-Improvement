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

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Nysiis encoder = new Nysiis();
        Object nonStringInput = new Object();

        try {
            encoder.encode(nonStringInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Nysiis.encode(Object) accepts only String instances.
            verifyException("org.apache.commons.codec.language.Nysiis", e);
        }
    }
}
