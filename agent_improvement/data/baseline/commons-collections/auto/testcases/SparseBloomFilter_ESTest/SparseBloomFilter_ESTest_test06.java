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
        Shape shape0 = Shape.fromNM(8, 8);
        LayerManager<SparseBloomFilter> layerManager0 = (LayerManager<SparseBloomFilter>) mock(LayerManager.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(layerManager0).processBloomFilters(any(java.util.function.Predicate.class));
        LayeredBloomFilter<SparseBloomFilter> layeredBloomFilter0 = new LayeredBloomFilter<SparseBloomFilter>(shape0, layerManager0);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        boolean boolean0 = sparseBloomFilter0.merge((BloomFilter<?>) layeredBloomFilter0);
        assertTrue(boolean0);
    }
}
