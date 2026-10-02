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
        Nysiis nysiis0 = new Nysiis();
        Object object0 = new Object();
        try {
            nysiis0.encode(object0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Parameter supplied to Nysiis encode is not of type java.lang.String
            //
            verifyException("org.apache.commons.codec.language.Nysiis", e);
        }
    }
}
