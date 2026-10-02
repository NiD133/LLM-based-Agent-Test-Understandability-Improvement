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
public class SegmentConstantPoolArrayCache_ESTest_test3 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        SegmentConstantPoolArrayCache segmentConstantPoolArrayCache0 = new SegmentConstantPoolArrayCache();
        String[] stringArray0 = new String[7];
        stringArray0[0] = "org.apache.commons.compress.harmony.unpack200.SegmentConstantPoolArrayCache$CachedArray";
        SegmentConstantPoolArrayCache.CachedArray segmentConstantPoolArrayCache_CachedArray0 = segmentConstantPoolArrayCache0.new CachedArray(stringArray0);
        List<Integer> list0 = segmentConstantPoolArrayCache_CachedArray0.indexesForKey("org.apache.commons.compress.harmony.unpack200.SegmentConstantPoolArrayCache$CachedArray");
        assertFalse(list0.isEmpty());
    }
}
