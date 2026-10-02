package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test10 extends ByteOrderMark_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_equalsNull_returnsFalse() throws Throwable {
        // ByteOrderMark.equals(null) must return false — null is never equal to a BOM instance
        ByteOrderMark utf32LeBom = ByteOrderMark.UTF_32LE;
        boolean isEqualToNull = utf32LeBom.equals((Object) null);
        assertFalse(isEqualToNull);
    }
}
