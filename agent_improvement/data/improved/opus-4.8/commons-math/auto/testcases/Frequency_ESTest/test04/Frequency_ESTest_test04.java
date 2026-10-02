package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test04 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that merging a collection of Frequency instances does not
     * consume or otherwise alter the source collection: the collection still
     * holds exactly the one element it started with after the merge call.
     */
    @Test(timeout = 4000)
    public void mergeDoesNotModifySourceCollection() throws Throwable {
        // Build a Frequency that records the value 0 (with a zero-count increment).
        Frequency<Integer> frequency = new Frequency<Integer>();
        Integer observedValue = Integer.valueOf(0);
        frequency.incrementValue(observedValue, 0L);

        // Put that single Frequency into a collection to merge from.
        LinkedList<Frequency<Integer>> frequenciesToMerge = new LinkedList<Frequency<Integer>>();
        frequenciesToMerge.add(frequency);

        // Merge the collection into the Frequency.
        frequency.merge((Collection<Frequency<Integer>>) frequenciesToMerge);

        // The source collection is left untouched and still contains its one element.
        assertEquals(1, frequenciesToMerge.size());
    }
}
