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
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();
        String[] arrayWithOnlyNullEntries = new String[8];
        String missingKey = "";

        cache.indexesForArrayKey(arrayWithOnlyNullEntries, missingKey);
        List<Integer> indexesForMissingKey = cache.indexesForArrayKey(arrayWithOnlyNullEntries, missingKey);

        assertEquals(0, indexesForMissingKey.size());
    }
}
