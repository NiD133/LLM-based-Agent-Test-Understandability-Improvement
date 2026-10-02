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
public class IndexedCollection_ESTest_test03 extends IndexedCollection_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_removeIf_withAlwaysTruePredicate_removesAllElementsAndReturnsTrue() throws Throwable {
        // Build a backing list containing one element (the null-transformer itself)
        LinkedList<Object> backingList = new LinkedList<Object>();
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();
        backingList.add((Object) nullKeyTransformer);

        // Wrap the list in a non-unique IndexedCollection using the null-key transformer
        IndexedCollection<Integer, Object> indexedCollection = IndexedCollection.nonUniqueIndexedCollection(
                (Collection<Object>) backingList, nullKeyTransformer);

        // removeIf with a predicate that always returns true should remove every element
        Predicate<Object> alwaysTruePredicate = TruePredicate.truePredicate();
        boolean wasModified = indexedCollection.removeIf(alwaysTruePredicate);

        // The backing list must now be empty and the method must have reported a change
        assertEquals(0, backingList.size());
        assertTrue(wasModified);
    }
}
