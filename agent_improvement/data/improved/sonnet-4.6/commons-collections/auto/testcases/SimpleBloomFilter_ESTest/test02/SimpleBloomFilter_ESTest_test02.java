package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test02 extends SimpleBloomFilter_ESTest_scaffolding {

    // An empty filter vacuously contains itself because there are no bits set
    // that could be absent from itself.
    @Test(timeout = 4000)
    public void test02_emptyFilterContainsItself() throws Throwable {
        // Use a large shape (3157 hash functions, 3157 bits) to exercise the filter structure
        Shape largeShape = Shape.fromKM(3157, 3157);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(largeShape);

        // An empty Bloom filter must contain itself: vacuous containment holds
        // because every bit set in the contained filter (none) is also set in the container.
        boolean containsItself = emptyFilter.contains((BloomFilter<?>) emptyFilter);
        assertTrue(containsItself);

        // SimpleBloomFilter has no special characteristics (not SPARSE, not SPARSE|UNIQUE, etc.)
        assertEquals(0, emptyFilter.characteristics());
    }
}
