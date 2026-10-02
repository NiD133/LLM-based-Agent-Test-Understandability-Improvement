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
public class IndexedCollection_ESTest_test08 extends IndexedCollection_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LinkedList<Object> sourceElements = new LinkedList<Object>();
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();
        sourceElements.add((Object) nullKeyTransformer);

        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) sourceElements, nullKeyTransformer);

        boolean containsEverySourceElement = indexedCollection.containsAll(sourceElements);
        assertTrue(containsEverySourceElement);
    }
}
