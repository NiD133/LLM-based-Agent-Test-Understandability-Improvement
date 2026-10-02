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
public class BoundedIterator_ESTest_test3 extends BoundedIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        HashMap<Predicate<InstanceofPredicate>, Closure<InstanceofPredicate>> emptyClosureMap =
                new HashMap<Predicate<InstanceofPredicate>, Closure<InstanceofPredicate>>();
        SwitchClosure.switchClosure((Map<Predicate<InstanceofPredicate>, Closure<InstanceofPredicate>>) emptyClosureMap);

        Iterator<Closure<InstanceofPredicate>> exhaustedIterator =
                (Iterator<Closure<InstanceofPredicate>>) mock(Iterator.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(exhaustedIterator).hasNext();

        BoundedIterator<Closure<InstanceofPredicate>> boundedIterator =
                new BoundedIterator<Closure<InstanceofPredicate>>(exhaustedIterator, 1357L, 1357L);
    }
}
