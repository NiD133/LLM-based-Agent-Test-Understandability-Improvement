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
public class SimpleBloomFilter_ESTest_test13 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that calling estimateIntersection on an empty filter with itself
     * does not alter the filter's characteristics (which must remain 0 for SimpleBloomFilter).
     */
    @Test(timeout = 4000)
    public void testEstimateIntersectionWithSelfDoesNotChangeCharacteristics() throws Throwable {
        Shape largeShape = Shape.fromKM(3132, 3132);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(largeShape);

        // Estimate intersection of empty filter with itself — result is discarded;
        // the important post-condition is that characteristics() is unchanged.
        emptyFilter.estimateIntersection(emptyFilter);

        assertEquals(0, emptyFilter.characteristics());
    }
}
