package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.apache.commons.collections4.functors.SwitchClosure;
import org.apache.commons.collections4.functors.TransformerClosure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test1 extends BoundedIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
        Integer integer0 = new Integer(0);
        linkedList0.add(integer0);
        Iterator<Integer> iterator0 = linkedList0.iterator();
        BoundedIterator<Integer> boundedIterator0 = new BoundedIterator<Integer>(iterator0, 0L, 3226L);
        boundedIterator0.next();
        boundedIterator0.remove();
    }
}
