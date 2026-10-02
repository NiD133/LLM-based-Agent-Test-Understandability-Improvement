package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.ChainedClosure;
import org.apache.commons.collections4.functors.IfClosure;
import org.apache.commons.collections4.functors.OnePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZippingIterator_ESTest_test2 extends ZippingIterator_ESTest_scaffolding {

    /**
     * Drains a ZippingIterator with forEachRemaining and then removes the last
     * element it returned. Both child iterators are the SAME single-element
     * iterator, so the interleaved iteration yields that one element once and
     * remove() deletes it from the backing list.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // A list holding a single element, traversed via its descending iterator.
        LinkedList<Object> sourceList = new LinkedList<Object>();
        Object element = new Object();
        sourceList.add(element);
        Iterator<Object> sharedIterator = sourceList.descendingIterator();

        // Zip the same iterator with itself; it still backs the same one element.
        ZippingIterator<Object> zippingIterator =
                new ZippingIterator<Object>(sharedIterator, sharedIterator);

        // Build a no-op IfClosure to pass to forEachRemaining:
        //   - OnePredicate over an empty predicate array as the condition
        //   - ChainedClosure of an empty closure array as the body
        Predicate<Object>[] emptyPredicates =
                (Predicate<Object>[]) Array.newInstance(Predicate.class, 0);
        OnePredicate<Object> condition = new OnePredicate<Object>(emptyPredicates);

        Closure<Object>[] emptyClosures =
                (Closure<Object>[]) Array.newInstance(Closure.class, 0);
        Closure<Object> body =
                ChainedClosure.chainedClosure((Closure<? super Object>[]) emptyClosures);

        IfClosure<Object> noOpClosure = new IfClosure<Object>(condition, body);

        // Consume every remaining element, then remove the last one returned.
        zippingIterator.forEachRemaining(noOpClosure);
        zippingIterator.remove();
    }
}
