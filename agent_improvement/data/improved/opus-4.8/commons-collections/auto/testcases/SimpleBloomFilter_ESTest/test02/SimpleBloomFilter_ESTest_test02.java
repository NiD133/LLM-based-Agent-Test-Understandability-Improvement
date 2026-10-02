package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test02 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * A filter always contains itself, and a SimpleBloomFilter reports no
     * special characteristics (its {@link SimpleBloomFilter#characteristics()}
     * is always 0).
     */
    @Test(timeout = 4000)
    public void filterContainsItselfAndHasNoCharacteristics() throws Throwable {
        Shape shape = Shape.fromKM(3157, 3157);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        boolean containsItself = filter.contains((BloomFilter<?>) filter);

        assertTrue("A filter should always contain itself", containsItself);
        assertEquals("SimpleBloomFilter exposes no characteristics", 0, filter.characteristics());
    }
}
