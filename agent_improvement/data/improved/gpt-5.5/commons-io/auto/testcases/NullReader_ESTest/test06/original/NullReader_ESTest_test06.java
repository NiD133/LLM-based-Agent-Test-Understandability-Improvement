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
public class NullReader_ESTest_test06 extends NullReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        NullReader nullReader0 = new NullReader();
        char[] charArray0 = new char[3];
        nullReader0.skip(531L);
        try {
            nullReader0.read(charArray0);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Read after end of file
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
