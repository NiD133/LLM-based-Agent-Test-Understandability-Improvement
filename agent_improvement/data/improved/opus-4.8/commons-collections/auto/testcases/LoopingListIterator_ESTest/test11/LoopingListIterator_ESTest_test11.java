package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test11 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * hasPrevious() returns false when the iterator wraps an empty list,
     * because there are no elements to loop over in either direction.
     */
    @Test(timeout = 4000)
    public void hasPreviousReturnsFalseForEmptyList() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(emptyList);

        boolean hasPrevious = iterator.hasPrevious();

        assertFalse(hasPrevious);
    }
}
