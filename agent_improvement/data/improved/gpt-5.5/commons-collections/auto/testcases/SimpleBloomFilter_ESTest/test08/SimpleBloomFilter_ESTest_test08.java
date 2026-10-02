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
public class SimpleBloomFilter_ESTest_test08 extends SimpleBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Shape denseShape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(denseShape);

        boolean mergeResult = emptyFilter.merge((IndexExtractor) emptyFilter);
        boolean filterIsEmptyAfterSelfMerge = emptyFilter.isEmpty();

        assertTrue(filterIsEmptyAfterSelfMerge == mergeResult);
        assertTrue(filterIsEmptyAfterSelfMerge);
    }
}
