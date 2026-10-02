package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test09 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * A freshly constructed SimpleBloomFilter has no bits set,
     * so isEmpty() should report true.
     */
    @Test(timeout = 4000)
    public void newFilterIsEmpty() throws Throwable {
        Shape shape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        boolean empty = filter.isEmpty();

        assertTrue("A newly created filter should be empty", empty);
    }
}
