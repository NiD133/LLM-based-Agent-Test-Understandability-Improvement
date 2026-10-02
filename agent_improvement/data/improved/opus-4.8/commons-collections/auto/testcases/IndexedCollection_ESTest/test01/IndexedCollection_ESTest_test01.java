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
public class IndexedCollection_ESTest_test01 extends IndexedCollection_ESTest_scaffolding {

    /**
     * retainAll() over an empty collection should report no change.
     *
     * The decorated collection is empty, so retaining the same (empty) collection
     * removes nothing; retainAll() must therefore return false.
     */
    @Test(timeout = 4000)
    public void retainAllOnEmptyCollectionReturnsFalse() throws Throwable {
        final LinkedList<Object> backingCollection = new LinkedList<Object>();
        final Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();
        final IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingCollection, keyTransformer);

        final boolean changed = indexedCollection.retainAll(backingCollection);

        assertFalse(changed);
    }
}
