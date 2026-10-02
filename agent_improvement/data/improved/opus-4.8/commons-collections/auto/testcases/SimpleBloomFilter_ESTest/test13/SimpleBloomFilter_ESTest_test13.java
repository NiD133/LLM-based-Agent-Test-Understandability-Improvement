package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test13 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Estimating the intersection of an empty filter with itself should succeed,
     * and the filter's characteristics() should remain 0 (SimpleBloomFilter
     * always reports no special characteristics).
     */
    @Test(timeout = 4000)
    public void estimateIntersectionWithSelf_keepsZeroCharacteristics() throws Throwable {
        Shape shape = Shape.fromKM(3132, 3132);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        emptyFilter.estimateIntersection(emptyFilter);

        assertEquals(0, emptyFilter.characteristics());
    }
}
