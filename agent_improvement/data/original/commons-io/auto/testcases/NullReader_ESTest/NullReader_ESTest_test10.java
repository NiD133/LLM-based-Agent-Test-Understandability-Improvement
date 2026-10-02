package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test10 extends NullReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        NullReader nullReader0 = new NullReader((-2742L), true, true);
        char[] charArray0 = new char[4];
        nullReader0.read(charArray0);
        try {
            nullReader0.skip((-2742));
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
