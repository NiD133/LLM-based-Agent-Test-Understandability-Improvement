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
public class IndexedCollection_ESTest_test12 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that calling clear() on an empty IndexedCollection completes
     * without throwing an exception.
     *
     * The ConstantTransformer always returns the same key (the empty list itself),
     * so every element would map to the same index entry. The backing collection
     * starts empty, so clear() simply empties both the collection and the index.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        LinkedList<LinkedList<Object>> emptyBackingList = new LinkedList<LinkedList<Object>>();

        // Key transformer that maps every element to the same constant key
        ConstantTransformer<LinkedList<Object>, Object> constantKeyTransformer =
                new ConstantTransformer<LinkedList<Object>, Object>(emptyBackingList);

        // Build a non-unique IndexedCollection backed by the empty list
        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection(emptyBackingList, constantKeyTransformer);

        // Clearing an already-empty IndexedCollection should succeed without error
        indexedCollection.clear();
    }
}
