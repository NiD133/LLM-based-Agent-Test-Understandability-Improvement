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

    // Shape parameters: 8 expected insertions, 8 bits of length
    private static final int SHAPE_ITEMS = 8;
    private static final int SHAPE_BITS  = 8;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Shape shape = Shape.fromNM(SHAPE_ITEMS, SHAPE_BITS);

        // Mock a LayerManager whose processBloomFilters always returns false,
        // so the layered filter contributes no indices during the merge.
        LayerManager<SparseBloomFilter> emptyLayerManager =
                (LayerManager<SparseBloomFilter>) mock(LayerManager.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(emptyLayerManager).processBloomFilters(any(java.util.function.Predicate.class));

        LayeredBloomFilter<SparseBloomFilter> layeredFilter =
                new LayeredBloomFilter<SparseBloomFilter>(shape, emptyLayerManager);

        SparseBloomFilter sparseFilter = new SparseBloomFilter(shape);

        // Merging a BloomFilter<?> into a SparseBloomFilter must always return true.
        boolean mergeResult = sparseFilter.merge((BloomFilter<?>) layeredFilter);
        assertTrue("merge(BloomFilter<?>) should return true", mergeResult);
    }
}
