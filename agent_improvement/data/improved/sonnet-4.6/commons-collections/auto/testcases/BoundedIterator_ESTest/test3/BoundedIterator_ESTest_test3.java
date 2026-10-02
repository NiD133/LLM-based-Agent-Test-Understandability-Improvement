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

    /**
     * Verifies that a BoundedIterator can be constructed with a large offset and max
     * on an empty iterator without throwing an exception. The constructor's init()
     * method tries to advance to the offset position, but stops immediately because
     * the underlying iterator has no elements (hasNext() returns false).
     */
    @Test(timeout = 4000)
    public void test_constructorWithLargeOffsetAndMaxOnEmptyIterator() throws Throwable {
        // Build an empty predicate-to-closure map and create a SwitchClosure from it
        Map<Predicate<InstanceofPredicate>, Closure<InstanceofPredicate>> emptyPredicateClosureMap =
                new HashMap<Predicate<InstanceofPredicate>, Closure<InstanceofPredicate>>();
        SwitchClosure.switchClosure(emptyPredicateClosureMap);

        // Create a mock iterator that reports no elements available
        Iterator<Closure<InstanceofPredicate>> emptyMockIterator =
                (Iterator<Closure<InstanceofPredicate>>) mock(Iterator.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(emptyMockIterator).hasNext();

        // Construct a BoundedIterator with offset=1357 and max=1357 over the empty iterator;
        // init() will attempt to skip to position 1357 but halts immediately since hasNext()=false
        BoundedIterator<Closure<InstanceofPredicate>> boundedIterator =
                new BoundedIterator<Closure<InstanceofPredicate>>(emptyMockIterator, 1357L, 1357L);
    }
}
