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
     * Verifies that {@link SparseBloomFilter#processBitMaps(java.util.function.LongPredicate)}
     * returns {@code false} as soon as the supplied consumer rejects a bit map.
     *
     * The filter is empty, so processBitMaps still emits its single (zero) bit map to the
     * consumer. The consumer here is a CountingLongPredicate backed by a LongBiPredicate
     * stubbed to always answer {@code false}, which makes processBitMaps short-circuit and
     * report failure.
     */
    @Test(timeout = 4000)
    public void processBitMapsReturnsFalseWhenConsumerRejects() throws Throwable {
        // An empty sparse filter shaped for 8 bits and 8 hash functions.
        Shape shape = Shape.fromNM(8, 8);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);

        // The empty filter's bit map representation: a single all-zero long.
        long[] bitMaps = emptyFilter.asBitMapArray();

        // A predicate that always rejects whatever bit map it is asked about.
        LongBiPredicate alwaysRejectingPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(alwaysRejectingPredicate).test(anyLong(), anyLong());
        CountingLongPredicate rejectingConsumer = new CountingLongPredicate(bitMaps, alwaysRejectingPredicate);

        boolean processedSuccessfully = emptyFilter.processBitMaps(rejectingConsumer);

        // Processing stops early because the consumer rejected the first bit map.
        assertFalse(processedSuccessfully);
    }
}
