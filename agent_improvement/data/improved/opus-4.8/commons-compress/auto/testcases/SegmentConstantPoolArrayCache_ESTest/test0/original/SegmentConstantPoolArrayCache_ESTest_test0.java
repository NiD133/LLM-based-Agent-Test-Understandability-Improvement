package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.IdentityHashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentConstantPoolArrayCache_ESTest_test0 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        SegmentConstantPoolArrayCache segmentConstantPoolArrayCache0 = new SegmentConstantPoolArrayCache();
        String[] stringArray0 = new String[8];
        segmentConstantPoolArrayCache0.indexesForArrayKey(stringArray0, "");
        List<Integer> list0 = segmentConstantPoolArrayCache0.indexesForArrayKey(stringArray0, "");
        assertEquals(0, list0.size());
    }
}
