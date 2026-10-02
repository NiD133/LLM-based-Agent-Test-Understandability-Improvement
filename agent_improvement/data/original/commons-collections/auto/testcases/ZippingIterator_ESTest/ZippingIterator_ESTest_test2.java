package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.ChainedClosure;
import org.apache.commons.collections4.functors.IfClosure;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.apache.commons.collections4.functors.OnePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZippingIterator_ESTest_test2 extends ZippingIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        LinkedList<Object> linkedList0 = new LinkedList<Object>();
        Object object0 = new Object();
        linkedList0.add(object0);
        Iterator<Object> iterator0 = linkedList0.descendingIterator();
        ZippingIterator<Object> zippingIterator0 = new ZippingIterator<Object>(iterator0, iterator0);
        Predicate<Object>[] predicateArray0 = (Predicate<Object>[]) Array.newInstance(Predicate.class, 0);
        OnePredicate<Object> onePredicate0 = new OnePredicate<Object>(predicateArray0);
        Closure<Object>[] closureArray0 = (Closure<Object>[]) Array.newInstance(Closure.class, 0);
        Closure<Object> closure0 = ChainedClosure.chainedClosure((Closure<? super Object>[]) closureArray0);
        IfClosure<Object> ifClosure0 = new IfClosure<Object>(onePredicate0, closure0);
        zippingIterator0.forEachRemaining(ifClosure0);
        zippingIterator0.remove();
    }
}
