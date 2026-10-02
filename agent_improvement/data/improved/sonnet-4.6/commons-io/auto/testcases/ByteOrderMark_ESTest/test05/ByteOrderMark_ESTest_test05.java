package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test05 extends ByteOrderMark_ESTest_scaffolding {

    // UTF_16BE BOM requires two bytes (0xFE, 0xFF); an array with fewer elements cannot match
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        ByteOrderMark utf16beBom = ByteOrderMark.UTF_16BE;
        int[] arrayTooShortForBom = new int[1];
        boolean matches = utf16beBom.matches(arrayTooShortForBom);
        assertFalse(matches);
    }
}
