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
public class SparseBloomFilter_ESTest_test06 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that merging another (empty) BloomFilter into a SparseBloomFilter
     * reports success by returning {@code true}.
     *
     * The "other" filter is a LayeredBloomFilter backed by a mocked LayerManager
     * whose {@code processBloomFilters} returns false, so the layered filter
     * contributes no indices. Per the contract of
     * {@link SparseBloomFilter#merge(BloomFilter)}, a merge always returns
     * {@code true}.
     */
    @Test(timeout = 4000)
    public void merge_withEmptyLayeredBloomFilter_returnsTrue() throws Throwable {
        // A shape sized for 8 items spread across 8 bits.
        Shape shape = Shape.fromNM(8, 8);

        // Mock the layer manager so the layered filter holds no enabled bits.
        LayerManager<SparseBloomFilter> layerManager =
                (LayerManager<SparseBloomFilter>) mock(LayerManager.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(layerManager).processBloomFilters(any(java.util.function.Predicate.class));

        // The "other" filter that will be merged in.
        LayeredBloomFilter<SparseBloomFilter> otherFilter =
                new LayeredBloomFilter<SparseBloomFilter>(shape, layerManager);

        // The filter under test.
        SparseBloomFilter sparseBloomFilter = new SparseBloomFilter(shape);

        boolean mergeSucceeded = sparseBloomFilter.merge((BloomFilter<?>) otherFilter);

        assertTrue(mergeSucceeded);
    }
}
