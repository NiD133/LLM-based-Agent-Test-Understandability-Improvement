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
public class SparseBloomFilter_ESTest_test02 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that {@link SparseBloomFilter#processBitMaps(java.util.function.LongPredicate)}
     * returns {@code false} (i.e. it stops early) when the predicate it is given
     * rejects one of the bit-map blocks.
     *
     * <p>The filter is first populated by merging a hasher, which always returns
     * {@code true}. The bit-map processing is then driven by a
     * {@link CountingLongPredicate} backed by a mock {@link LongBiPredicate} that
     * accepts the first block ({@code true}) but rejects the second ({@code false}),
     * causing {@code processBitMaps} to short-circuit and return {@code false}.</p>
     */
    @Test(timeout = 4000)
    public void processBitMapsReturnsFalseWhenPredicateRejectsBlock() throws Throwable {
        // A shape large enough (4135 bits) to span several 64-bit bit-map blocks.
        Shape shape = Shape.fromNM(8, 4135);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // Populate the filter; merging a hasher always succeeds.
        byte[] hasherSeed = new byte[7];
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(hasherSeed);
        boolean mergeResult = filter.merge((Hasher) hasher);

        // A predicate that accepts the first bit-map block, then rejects the next one.
        LongBiPredicate blockPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(blockPredicate).test(anyLong(), anyLong());
        long[] previousValues = new long[1];
        CountingLongPredicate countingPredicate = new CountingLongPredicate(previousValues, blockPredicate);

        // processBitMaps short-circuits and returns false once a block is rejected.
        boolean processResult = filter.processBitMaps(countingPredicate);

        assertFalse(processResult == mergeResult);
        assertFalse(processResult);
    }
}
