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
public class SparseBloomFilter_ESTest_test01 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that processBitMaps returns false when the underlying LongBiPredicate
     * (wrapped by CountingLongPredicate) always returns false for every bit map entry.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Build a shape for 8 expected insertions with 8 bits per item
        Shape shape = Shape.fromNM(8, 8);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);

        // Retrieve the bit map representation of the empty filter (all zero longs)
        long[] bitMapArray = emptyFilter.asBitMapArray();

        // Mock a LongBiPredicate that unconditionally returns false
        LongBiPredicate alwaysFalsePredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(alwaysFalsePredicate).test(anyLong(), anyLong());

        // CountingLongPredicate pairs each consumed bit map with its counterpart from bitMapArray
        CountingLongPredicate countingPredicate = new CountingLongPredicate(bitMapArray, alwaysFalsePredicate);

        // processBitMaps must propagate the false return value from the predicate
        boolean result = emptyFilter.processBitMaps(countingPredicate);
        assertFalse(result);
    }
}
