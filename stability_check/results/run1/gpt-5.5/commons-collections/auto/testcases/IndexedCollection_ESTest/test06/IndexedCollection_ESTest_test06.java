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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        LinkedList<LinkedList<Object>> emptyBackingCollection = new LinkedList<LinkedList<Object>>();
        ConstantTransformer<LinkedList<Object>, Object> constantKeyTransformer = new ConstantTransformer<LinkedList<Object>, Object>(emptyBackingCollection);
        IndexedCollection<Object, LinkedList<Object>> indexedCollection = IndexedCollection.nonUniqueIndexedCollection((Collection<LinkedList<Object>>) emptyBackingCollection, (Transformer<LinkedList<Object>, Object>) constantKeyTransformer);

        // The empty backing collection contributes no indexed values, even for the transformer's constant key.
        LinkedList<Object> valueForConstantKey = indexedCollection.get(constantKeyTransformer);

        assertNull(valueForConstantKey);
    }
}
