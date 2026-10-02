package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test14 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * ByteOrderMark.equals() must satisfy reflexivity: a BOM instance is always
     * equal to itself (same reference, same byte sequence).
     */
    @Test(timeout = 4000)
    public void test_UTF16LE_equalsItself_isReflexive() throws Throwable {
        ByteOrderMark utf16le = ByteOrderMark.UTF_16LE;
        boolean isEqualToSelf = utf16le.equals(utf16le);
        assertTrue("ByteOrderMark.equals() must be reflexive: an instance must equal itself", isEqualToSelf);
    }
}
