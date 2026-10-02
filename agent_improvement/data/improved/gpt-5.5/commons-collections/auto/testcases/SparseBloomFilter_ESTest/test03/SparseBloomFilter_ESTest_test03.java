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
public class SparseBloomFilter_ESTest_test03 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Shape filterShape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(filterShape);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher((-782L), 3371L);

        boolean mergedHasher = filter.merge((Hasher) hasher);
        boolean mergedOwnBitMap = filter.merge((BitMapExtractor) filter);

        assertTrue(mergedOwnBitMap == mergedHasher);
        assertTrue(mergedOwnBitMap);
    }
}
