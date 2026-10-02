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
public class SegmentConstantPoolArrayCache_ESTest_test2 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        SegmentConstantPoolArrayCache segmentConstantPoolArrayCache0 = new SegmentConstantPoolArrayCache();
        String[] stringArray0 = new String[7];
        String[] stringArray1 = new String[4];
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> identityHashMap0 = segmentConstantPoolArrayCache0.knownArrays;
        SegmentConstantPoolArrayCache.CachedArray segmentConstantPoolArrayCache_CachedArray0 = segmentConstantPoolArrayCache0.new CachedArray(stringArray1);
        identityHashMap0.put(stringArray0, segmentConstantPoolArrayCache_CachedArray0);
        assertEquals(4, segmentConstantPoolArrayCache_CachedArray0.lastKnownSize());
        boolean boolean0 = segmentConstantPoolArrayCache0.arrayIsCached(stringArray0);
        assertFalse(boolean0);
    }
}
