package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test14 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteOrderMark#equals(Object)} is reflexive:
     * a BOM constant is always equal to itself.
     */
    @Test(timeout = 4000)
    public void equalsReturnsTrueWhenComparedToItself() throws Throwable {
        ByteOrderMark utf16LeBom = ByteOrderMark.UTF_16LE;

        boolean isEqualToItself = utf16LeBom.equals(utf16LeBom);

        assertTrue("A BOM should be equal to itself", isEqualToItself);
    }
}
