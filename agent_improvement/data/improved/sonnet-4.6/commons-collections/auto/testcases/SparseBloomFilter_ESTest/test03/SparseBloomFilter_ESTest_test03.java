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

    /**
     * Verifies that merging a Hasher into a SparseBloomFilter and then merging
     * the populated filter with itself (as a BitMapExtractor) both succeed.
     *
     * After the first merge the filter contains the bits produced by the hasher.
     * The second merge re-adds those same bits (self-merge via BitMapExtractor),
     * which is an idempotent operation. Both calls must return {@code true}.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Arrange: a shape with 833 bits and 833 hash functions
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // A double hasher whose bit indices are derived from seed=-782 and increment=3371
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher((-782L), 3371L);

        // Act: populate the filter from the hasher, then merge the filter with itself
        boolean mergeHasherSucceeded = filter.merge((Hasher) hasher);
        boolean mergeSelfSucceeded   = filter.merge((BitMapExtractor) filter);

        // Assert: both merge operations must report success and agree on the result
        assertTrue("Merging a Hasher into the filter should succeed", mergeHasherSucceeded);
        assertTrue("Self-merging the filter as a BitMapExtractor should succeed", mergeSelfSucceeded);
        assertTrue("Both merge results should be equal", mergeSelfSucceeded == mergeHasherSucceeded);
    }
}
