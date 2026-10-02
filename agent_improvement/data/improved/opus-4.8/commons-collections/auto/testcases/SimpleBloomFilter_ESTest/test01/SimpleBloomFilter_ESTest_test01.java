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
     * Verifies that processBitMapPairs returns false when the supplied
     * LongBiPredicate rejects (returns false for) the pairs it is tested with.
     *
     * The "other" extractor is empty, so processing falls through to the
     * filter's own remaining bit maps, which are passed to the predicate.
     * Since the predicate always answers false, the whole operation is false.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // A filter shape sized for 1082 items and 1082 bits.
        Shape shape = Shape.fromNM(1082, 1082);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        // An extractor backed by an empty bit-map array (no bit maps to emit).
        long[] emptyBitMaps = new long[0];
        BitMapExtractor emptyExtractor = BitMapExtractor.fromBitMapArray(emptyBitMaps);

        // A predicate that always rejects the pair it receives.
        LongBiPredicate alwaysFalsePredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(alwaysFalsePredicate).test(anyLong(), anyLong());

        boolean allPairsAccepted = bloomFilter.processBitMapPairs(emptyExtractor, alwaysFalsePredicate);

        assertFalse(allPairsAccepted);
    }
}
