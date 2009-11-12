package org.bindgen.example.methods;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.bindgen.Binding;
import org.bindgen.ContainerBinding;
import org.bindgen.example.methods.MethodExample;
import org.bindgen.example.methods.Wildcards;

import bindgen.org.bindgen.example.methods.MethodExampleBinding;

public class MethodExampleTest extends TestCase {

	public void testReadWrite() {
		MethodExample e = new MethodExample("1", "fred");
		MethodExampleBinding b = new MethodExampleBinding(e);

		Assert.assertEquals("fred", b.name().get());

		b.name().set("bob");
		Assert.assertEquals("bob", e.getName());
	}

	public void testReadOnly() {
		MethodExample e = new MethodExample("1", "name");
		MethodExampleBinding b = new MethodExampleBinding(e);

		Assert.assertEquals("1", b.id().get());

		try {
			b.id().set("name1");
			Assert.fail();
		} catch (RuntimeException re) {
			Assert.assertEquals("id is read only", re.getMessage());
		}
	}

	public void testBoolean() {
		MethodExample e = new MethodExample("1", "name");
		MethodExampleBinding b = new MethodExampleBinding(e);
		Assert.assertEquals(false, b.good().get().booleanValue());

		b.good().set(true);
		Assert.assertEquals(true, e.isGood());
	}

	public void testToString() {
		MethodExample e = new MethodExample("1", "name");
		MethodExampleBinding b = new MethodExampleBinding(e);
		Assert.assertEquals("method", b.string().get());
	}

	public void testHasMethod() {
		MethodExample e = new MethodExample("1", "name");
		MethodExampleBinding b = new MethodExampleBinding(e);
		Assert.assertEquals(false, b.stuff().get().booleanValue());
	}

	public void testBooleanThatIsAKeywordFallsBackOnMethodName() {
		MethodExample e = new MethodExample("1", "name");
		MethodExampleBinding b = new MethodExampleBinding(e);
		Assert.assertEquals(false, b.isNew().get().booleanValue());
	}

	public void testGetBindings() {
		MethodExample e = new MethodExample("1", "name");
		MethodExampleBinding b = new MethodExampleBinding(e);
		Assert.assertEquals(13, b.getChildBindings().size());

		boolean foundName = false;
		for (Binding<?> sub : b.getChildBindings()) {
			if (sub.getName().equals("name")) {
				foundName = true;
			}
		}
		Assert.assertTrue(foundName);
	}

	public void testList() {
		MethodExampleBinding b = new MethodExampleBinding();
		Assert.assertEquals(String.class, ((ContainerBinding) b.list()).getContainedType());
	}

	public void testNull() {
		MethodExample e = new MethodExample("1", "name");
		MethodExampleBinding b = new MethodExampleBinding(e);
		Assert.assertEquals(false, b.isNull().get().booleanValue());
	}

	@SuppressWarnings("unchecked")
	public void testWildcards() {
		MethodExample e = new MethodExample("1", "name");
		e.setWildcards(new Wildcards());
		MethodExampleBinding b = new MethodExampleBinding(e);
		b.wildcards().a().set("a string");
		// needs casting b.wildcards().b().set("anything");
	}
}