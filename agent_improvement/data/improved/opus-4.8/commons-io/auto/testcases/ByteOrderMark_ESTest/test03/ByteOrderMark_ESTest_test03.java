package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test03 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteOrderMark#matches(int[])} returns {@code false}
     * when the array under test is {@code null}.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseForNullArray() throws Throwable {
        ByteOrderMark utf32BigEndian = ByteOrderMark.UTF_32BE;

        boolean matchesNull = utf32BigEndian.matches((int[]) null);

        assertFalse("A null array must never match a BOM", matchesNull);
    }
}
