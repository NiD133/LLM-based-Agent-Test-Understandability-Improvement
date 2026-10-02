package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test03 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that merging a Hasher and then merging the filter's own bit maps
     * both report success (return {@code true}).
     */
    @Test(timeout = 4000)
    public void mergeHasherThenSelfBitMaps_bothReturnTrue() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // Populate the filter via a hasher-based merge.
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(-782L, 3371L);
        boolean hasherMergeResult = filter.merge((Hasher) hasher);

        // Merge the filter's own bit maps back into itself.
        boolean selfMergeResult = filter.merge((BitMapExtractor) filter);

        assertTrue(hasherMergeResult);
        assertTrue(selfMergeResult);
        assertEquals(hasherMergeResult, selfMergeResult);
    }
}
