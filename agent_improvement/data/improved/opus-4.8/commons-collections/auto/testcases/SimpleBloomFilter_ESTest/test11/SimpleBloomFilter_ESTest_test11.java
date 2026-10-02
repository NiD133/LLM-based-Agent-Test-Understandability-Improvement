package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test11 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that {@link SimpleBloomFilter#characteristics()} reports 0 (no
     * special characteristics) for a SimpleBloomFilter, even after the filter
     * has been cleared.
     */
    @Test(timeout = 4000)
    public void characteristicsAreZeroAfterClear() throws Throwable {
        Shape shape = Shape.fromNM(1110, 1110);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        bloomFilter.clear();

        assertEquals(0, bloomFilter.characteristics());
    }
}
