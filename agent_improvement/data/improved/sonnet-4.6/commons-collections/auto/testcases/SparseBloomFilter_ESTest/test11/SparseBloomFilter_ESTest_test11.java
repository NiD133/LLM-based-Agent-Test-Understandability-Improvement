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
public class SparseBloomFilter_ESTest_test11 extends SparseBloomFilter_ESTest_scaffolding {

    // BloomFilter.SPARSE == 1, indicating the filter uses an index-based (sparse) representation
    private static final int SPARSE_CHARACTERISTIC = 1;

    @Test(timeout = 4000)
    public void test11_estimateIntersectionWithSelfPreservesSparseCharacteristic() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);

        // Estimating intersection of an empty filter with itself is a valid no-op operation
        emptyFilter.estimateIntersection(emptyFilter);

        // The filter must still report itself as sparse after the intersection estimate
        assertEquals(SPARSE_CHARACTERISTIC, emptyFilter.characteristics());
    }
}
