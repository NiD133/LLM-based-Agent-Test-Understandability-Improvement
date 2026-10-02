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
public class IndexedCollection_ESTest_test04 extends IndexedCollection_ESTest_scaffolding {

    /**
     * removeIf(null) must return false immediately without modifying the collection,
     * as documented by the null-guard in IndexedCollection.removeIf.
     */
    @Test(timeout = 4000)
    public void test_removeIfWithNullPredicate_returnsFalseWithoutModifyingCollection() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) emptyList, nullKeyTransformer);

        // Passing a null predicate: the CUT returns false immediately (Objects.isNull guard)
        boolean collectionWasModified = indexedCollection.removeIf((java.util.function.Predicate<? super Object>) null);

        assertFalse(collectionWasModified);
    }
}
