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
public class IndexedCollection_ESTest_test07 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that containsAll reflects the index, not the decorated collection.
     *
     * The IndexedCollection is built around an initially empty list, so its index
     * starts empty. We then add an element directly to the underlying list, which
     * (per the class contract) does NOT update the index. Because containsAll uses
     * the index for lookups, it cannot find the out-of-sync element and returns false.
     */
    @Test(timeout = 4000)
    public void containsAllReturnsFalseWhenElementMissingFromIndex() throws Throwable {
        // Decorate an empty list with a non-unique index keyed by a constant null.
        LinkedList<Object> backingList = new LinkedList<Object>();
        Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, keyTransformer);

        // Mutate the backing list directly, bypassing the index update.
        backingList.add((Object) indexedCollection);

        // containsAll consults the (still empty) index, so the element is not found.
        boolean containsAll = indexedCollection.containsAll(backingList);

        assertFalse(containsAll);
    }
}
