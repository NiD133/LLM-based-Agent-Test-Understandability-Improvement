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
public class IndexedCollection_ESTest_test13 extends IndexedCollection_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        LinkedList<Object> linkedList0 = new LinkedList<Object>();
        Transformer<Object, Integer> transformer0 = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection0 = IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) linkedList0, transformer0);
        linkedList0.add((Object) transformer0);
        boolean boolean0 = indexedCollection0.removeAll(linkedList0);
        assertEquals(0, linkedList0.size());
        assertTrue(boolean0);
    }
}
