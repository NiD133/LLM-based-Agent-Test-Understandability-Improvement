package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Verifies that forEachRemaining completes without error when the underlying
     * collection is empty, even when a large offset and max are specified.
     * The closure is never invoked because there are no elements to consume.
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        Iterator<Object> emptyIterator = emptyList.iterator();

        long largeOffset = 4124L;
        long largeMax = 4124L;
        BoundedIterator<Object> boundedIterator = new BoundedIterator<Object>(emptyIterator, largeOffset, largeMax);

        // Closure with a null transformer — safe here because no elements exist to process
        TransformerClosure<Object> nullTransformerClosure = new TransformerClosure<Object>((Transformer<? super Object, ?>) null);

        boundedIterator.forEachRemaining(nullTransformerClosure);
    }
}
