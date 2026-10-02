package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test1 extends BoundedIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Set up a single-element list as the backing collection
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(Integer.valueOf(0));
        Iterator<Integer> listIterator = list.iterator();

        // Wrap with BoundedIterator starting at offset 0, allowing up to 3226 elements
        BoundedIterator<Integer> boundedIterator = new BoundedIterator<Integer>(listIterator, 0L, 3226L);

        // Advance past the first element, then remove it via the bounded iterator
        boundedIterator.next();
        boundedIterator.remove();
    }
}
