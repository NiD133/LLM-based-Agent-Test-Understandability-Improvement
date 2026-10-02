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
public class IndexedCollection_ESTest_test00 extends IndexedCollection_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
        Integer integer0 = new Integer(0);
        ConstantTransformer<Integer, Integer> constantTransformer0 = new ConstantTransformer<Integer, Integer>(integer0);
        IndexedCollection<Integer, Integer> indexedCollection0 = IndexedCollection.uniqueIndexedCollection((Collection<Integer>) linkedList0, (Transformer<Integer, Integer>) constantTransformer0);
        indexedCollection0.add(integer0);
        LinkedList<Object> linkedList1 = new LinkedList<Object>();
        boolean boolean0 = indexedCollection0.retainAll(linkedList1);
        assertTrue(boolean0);
    }
}
