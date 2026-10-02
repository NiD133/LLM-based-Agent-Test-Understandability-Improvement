package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.TransformerClosure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test4 extends BoundedIterator_ESTest_scaffolding {

    private static final long OFFSET_PAST_EMPTY_ITERATOR = 4124L;
    private static final long MAX_ELEMENTS_TO_RETURN = 4124L;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        Iterator<Object> emptyIterator = emptyList.iterator();
        BoundedIterator<Object> boundedIterator = new BoundedIterator<Object>(
                emptyIterator,
                OFFSET_PAST_EMPTY_ITERATOR,
                MAX_ELEMENTS_TO_RETURN);
        TransformerClosure<Object> closureWithNullTransformer =
                new TransformerClosure<Object>((Transformer<? super Object, ?>) null);

        boundedIterator.forEachRemaining(closureWithNullTransformer);
    }
}
