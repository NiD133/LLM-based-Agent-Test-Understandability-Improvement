package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test10 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that a BOM is never equal to {@code null}, as mandated by the
     * {@link Object#equals(Object)} contract.
     */
    @Test(timeout = 4000)
    public void equals_withNull_returnsFalse() throws Throwable {
        ByteOrderMark utf32LeBom = ByteOrderMark.UTF_32LE;

        boolean isEqualToNull = utf32LeBom.equals((Object) null);

        assertFalse("A BOM must never be equal to null", isEqualToNull);
    }
}
