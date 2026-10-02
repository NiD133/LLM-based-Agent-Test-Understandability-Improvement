package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test01 extends SegmentUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        long[] longArray0 = new long[8];
        longArray0[3] = (-1L);
        AttributeLayout attributeLayout0 = new AttributeLayout("7.kMp'Z", 2, "> AQHR!W+eH?K(U", 2);
        int int0 = SegmentUtils.countMatches(longArray0, (IMatcher) attributeLayout0);
        assertEquals(1, int0);
    }
}
