package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.apache.commons.collections4.functors.SwitchClosure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test3 extends BoundedIterator_ESTest_scaffolding {

    /**
     * Verifies that a BoundedIterator can be constructed with a large, equal
     * offset and max. Because the decorated iterator is empty (hasNext() is
     * mocked to return false), the constructor's internal advance-to-offset
     * loop performs no element access, so construction completes without error.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // SwitchClosure with an empty case map is accepted (no behavioural effect here).
        Map<Predicate<InstanceofPredicate>, Closure<InstanceofPredicate>> emptyCaseMap =
                new HashMap<Predicate<InstanceofPredicate>, Closure<InstanceofPredicate>>();
        SwitchClosure.switchClosure(emptyCaseMap);

        // An empty decorated iterator: hasNext() always reports no remaining elements.
        @SuppressWarnings("unchecked")
        Iterator<Closure<InstanceofPredicate>> emptyIterator =
                (Iterator<Closure<InstanceofPredicate>>) mock(Iterator.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(emptyIterator).hasNext();

        final long offset = 1357L;
        final long max = 1357L;
        BoundedIterator<Closure<InstanceofPredicate>> boundedIterator =
                new BoundedIterator<Closure<InstanceofPredicate>>(emptyIterator, offset, max);

        assertNotNull(boundedIterator);
    }
}
