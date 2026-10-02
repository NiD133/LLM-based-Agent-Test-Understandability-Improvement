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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Shape shape = Shape.fromNM(8, 8);

        LayerManager<SparseBloomFilter> layerManager = (LayerManager<SparseBloomFilter>) mock(LayerManager.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(layerManager).processBloomFilters(any(java.util.function.Predicate.class));

        LayeredBloomFilter<SparseBloomFilter> layeredBloomFilter = new LayeredBloomFilter<SparseBloomFilter>(shape, layerManager);
        SparseBloomFilter sparseBloomFilter = new SparseBloomFilter(shape);

        boolean mergeResult = sparseBloomFilter.merge((BloomFilter<?>) layeredBloomFilter);

        assertTrue(mergeResult);
    }
}
