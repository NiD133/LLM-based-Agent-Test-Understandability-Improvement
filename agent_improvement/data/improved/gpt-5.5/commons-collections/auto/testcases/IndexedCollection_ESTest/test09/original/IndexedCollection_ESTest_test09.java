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
public class IndexedCollection_ESTest_test09 extends IndexedCollection_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        LinkedList<Object> linkedList0 = new LinkedList<Object>();
        Transformer<Object, Object> transformer0 = ConstantTransformer.nullTransformer();
        linkedList0.offerFirst(transformer0);
        IndexedCollection<Object, Object> indexedCollection0 = IndexedCollection.uniqueIndexedCollection((Collection<Object>) linkedList0, transformer0);
        // Undeclared exception!
        try {
            indexedCollection0.add(linkedList0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Duplicate key in uniquely indexed collection.
            //
            verifyException("org.apache.commons.collections4.collection.IndexedCollection", e);
        }
    }
}
