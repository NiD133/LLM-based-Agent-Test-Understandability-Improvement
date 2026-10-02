package org.apache.commons.collections4.iterators;

import org.junit.Test;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.TransformerClosure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test4 extends BoundedIterator_ESTest_scaffolding {

    /**
     * forEachRemaining over an empty bounded iterator performs no action,
     * so the supplied closure is never invoked and no exception is thrown.
     */
    @Test(timeout = 4000)
    public void forEachRemainingOnEmptyIteratorDoesNothing() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        Iterator<Object> emptyIterator = emptyList.iterator();

        long offset = 4124L;
        long max = 4124L;
        BoundedIterator<Object> boundedIterator =
                new BoundedIterator<Object>(emptyIterator, offset, max);

        TransformerClosure<Object> closure =
                new TransformerClosure<Object>((Transformer<? super Object, ?>) null);

        // No elements remain, so the closure is never applied.
        boundedIterator.forEachRemaining(closure);
    }
}
