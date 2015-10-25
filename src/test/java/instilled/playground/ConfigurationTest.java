package instilled.playground;

import static instilled.playground.Configuration.load;
import static instilled.playground.Lang.resource;

import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {
	@Test
	public void testConfigLoader() {
		MyConfig cfg = load(MyConfig.class, resource("instilled/playground/my-config.yml"));
		Assert.assertThat(cfg.stringProperty(), CoreMatchers.is("abc"));
		Assert.assertThat(cfg.intProperty(), CoreMatchers.is(100));
		Assert.assertThat(cfg.doubleProperty(), CoreMatchers.is(100.5));
		Assert.assertThat(cfg.sub().stringProperty(), CoreMatchers.is("def"));
		Assert.assertThat(cfg.sub().list().get(3), CoreMatchers.is(4));
		Assert.assertThat(cfg.list().get(3), CoreMatchers.is(4));
		Assert.assertThat(cfg.listComplex().get(3).get("stringProperty"), CoreMatchers.is("4"));
		// Assert.assertThat(cfg.withDefault().stringProperty(),
		// CoreMatchers.is("defaulted"));
	}

	public interface MyConfig {
		String stringProperty();

		int intProperty();

		double doubleProperty();

		MySubConfig sub();

		List<Integer> list();

		List<Map<String, String>> listComplex();

		// Defaults doesn't work as I've issues
		// calling the default method through a proxy
		// instance.
		//default MySubConfig2 withDefault() {
		//  return new MySubConfig2() {
		//    @Override
		//	  public String stringProperty() {
		//	    return "defaulted";
		//    }
		//  };
		//}
	}

	public interface MySubConfig {
		String stringProperty();

		List<Integer> list();
	}

	public interface MySubConfig2 {
		String stringProperty();
	}
}
