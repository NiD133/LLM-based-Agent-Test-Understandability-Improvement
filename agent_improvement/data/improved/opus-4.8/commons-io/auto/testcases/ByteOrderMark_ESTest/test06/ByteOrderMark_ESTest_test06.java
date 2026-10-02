package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test06 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that calling {@link ByteOrderMark#hashCode()} on a predefined
     * BOM constant executes without throwing an exception.
     */
    @Test(timeout = 4000)
    public void hashCodeOnUtf16BeConstantDoesNotThrow() throws Throwable {
        ByteOrderMark utf16BeBom = ByteOrderMark.UTF_16BE;

        utf16BeBom.hashCode();
    }
}
