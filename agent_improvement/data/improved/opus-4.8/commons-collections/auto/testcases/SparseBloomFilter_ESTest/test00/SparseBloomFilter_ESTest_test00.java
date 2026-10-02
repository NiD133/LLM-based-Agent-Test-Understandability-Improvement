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
public class SparseBloomFilter_ESTest_test00 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that {@link SparseBloomFilter#processBitMaps(java.util.function.LongPredicate)}
     * returns {@code false} when the supplied predicate rejects one of the bit-map blocks.
     *
     * The predicate used here is a {@link CountingLongPredicate} backed by a mocked
     * {@link LongBiPredicate} that is stubbed to answer {@code true} on its first call and
     * {@code false} on the second. Because the filter's shape spans more than one bit-map
     * block, the second (rejecting) answer is reached and {@code processBitMaps} short-circuits
     * to {@code false}.
     */
    @Test(timeout = 4000)
    public void processBitMapsReturnsFalseWhenPredicateRejectsBlock() throws Throwable {
        // An empty source filter used only to obtain a zero-filled bit-map array.
        Shape sourceShape = Shape.fromNM(8, 8);
        SparseBloomFilter sourceFilter = new SparseBloomFilter(sourceShape);
        long[] sourceBitMaps = sourceFilter.asBitMapArray();

        // The filter under test: its shape (2 items, 775 bits) spans multiple bit-map blocks.
        Shape filterShape = Shape.fromNM(2, 775);
        SparseBloomFilter filterUnderTest = new SparseBloomFilter(filterShape);

        // Mock predicate: first comparison returns true, the next returns false.
        LongBiPredicate blockComparison = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(blockComparison).test(anyLong(), anyLong());

        CountingLongPredicate bitMapConsumer = new CountingLongPredicate(sourceBitMaps, blockComparison);

        boolean allBlocksAccepted = filterUnderTest.processBitMaps(bitMapConsumer);

        assertFalse(allBlocksAccepted);
    }
}
