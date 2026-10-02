package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test16 extends Frequency_ESTest_scaffolding {

    /**
     * A newly created Frequency has not recorded any values yet,
     * so its unique-value count should be zero.
     */
    @Test(timeout = 4000)
    public void newFrequencyHasZeroUniqueValues() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();

        int uniqueCount = emptyFrequency.getUniqueCount();

        assertEquals(0, uniqueCount);
    }
}
