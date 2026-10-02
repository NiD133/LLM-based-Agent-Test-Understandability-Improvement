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
public class IndexedCollection_ESTest_test06 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that looking up any key in an IndexedCollection backed by an
     * empty collection returns null, because no elements have been indexed yet.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Start with an empty backing list — no elements will be indexed
        LinkedList<LinkedList<Object>> emptyBackingList = new LinkedList<LinkedList<Object>>();

        // A ConstantTransformer always produces the same key regardless of its input
        ConstantTransformer<LinkedList<Object>, Object> constantKeyTransformer =
                new ConstantTransformer<LinkedList<Object>, Object>(emptyBackingList);

        // Build a non-unique IndexedCollection wrapping the empty list
        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection(
                        (Collection<LinkedList<Object>>) emptyBackingList,
                        (Transformer<LinkedList<Object>, Object>) constantKeyTransformer);

        // Querying any key in an empty indexed collection must return null
        LinkedList<Object> result = indexedCollection.get(constantKeyTransformer);
        assertNull(result);
    }
}
