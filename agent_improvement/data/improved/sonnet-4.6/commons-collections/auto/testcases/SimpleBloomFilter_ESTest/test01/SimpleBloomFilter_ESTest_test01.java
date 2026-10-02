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
public class SimpleBloomFilter_ESTest_test01 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that processBitMapPairs returns false when the paired predicate rejects a comparison.
     *
     * The filter has a non-empty internal bit map (allocated by the shape), but the
     * BitMapExtractor supplied to processBitMapPairs wraps an empty array.  After the
     * extractor is exhausted, CountingLongPredicate.processRemaining() iterates the
     * filter's own longs and invokes the LongBiPredicate for each one.  Because the mock
     * is configured to always return false, the very first invocation short-circuits and
     * the overall call returns false.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // A large shape so the filter's internal bit map contains multiple longs
        Shape shape = Shape.fromNM(1082, 1082);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        // An empty extractor contributes no bit-map longs to the pair walk
        long[] emptyBitMaps = new long[0];
        BitMapExtractor emptyExtractor = BitMapExtractor.fromBitMapArray(emptyBitMaps);

        // Predicate that always rejects — causes processBitMapPairs to return false
        LongBiPredicate rejectingPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(rejectingPredicate).test(anyLong(), anyLong());

        boolean result = filter.processBitMapPairs(emptyExtractor, rejectingPredicate);
        assertFalse(result);
    }
}
