package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test08 extends SegmentUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        int int0 = SegmentUtils.countArgs("(Ljava/lang/Object;)Ljava/lang/Object;");
        assertEquals(1, int0);
    }
}
