package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.CloneTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.NullPredicate;
import org.apache.commons.collections4.functors.TruePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test02 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that removeIf with a NullPredicate does not remove any elements
     * when the collection contains only non-null elements, and returns false.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Build a backing list whose sole element is the list itself (non-null)
        LinkedList<Object> backingList = new LinkedList<Object>();
        backingList.add((Object) backingList);

        // Use a transformer that always produces a null key, allowing duplicate keys
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, nullKeyTransformer);

        // NullPredicate evaluates to true only for null elements; the self-referential list is not null
        Predicate<Object> nullOnlyPredicate = NullPredicate.nullPredicate();
        boolean elementsWereRemoved = indexedCollection.removeIf(nullOnlyPredicate);

        // No elements matched the predicate, so the backing list is unchanged
        assertEquals(1, backingList.size());
        assertFalse(elementsWereRemoved);
    }
}
