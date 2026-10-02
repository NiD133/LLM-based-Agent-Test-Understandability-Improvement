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
public class SimpleBloomFilter_ESTest_test12 extends SimpleBloomFilter_ESTest_scaffolding {

    private static final int EXPECTED_INSERTIONS = 2490;
    private static final int NUMBER_OF_BITS = 2490;

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Shape shape = Shape.fromNM(EXPECTED_INSERTIONS, NUMBER_OF_BITS);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(EXPECTED_INSERTIONS, NUMBER_OF_BITS);

        boolean mergeSucceeded = bloomFilter.merge((Hasher) hasher);
        boolean isFilterEmpty = bloomFilter.isEmpty();

        assertFalse(isFilterEmpty == mergeSucceeded);
        assertFalse(isFilterEmpty);
    }
}
