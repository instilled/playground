package instilled.playground;

import static instilled.playground.Lang.deepMerge;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LangTest {
	@Test
	@SuppressWarnings({ "serial" })
	public void testDeepMerge() {
		Map<String, Object> m1 = new HashMap<String, Object>() {
			{
				put("a", "m1");
				put("b", new HashMap<String, Object>() {
					{
						put("a", "m1");
						put("b", "m1");
					}
				});
				put("c", "m1");
				put("d", new ArrayList<Object>() {
					{
						add("l11");
						add("l12");
						add(new HashMap<String, Object>() {
							{
								put("a", "m1");
								put("b", "m1");
							}
						});
					}
				});
			}
		};
		Map<String, Object> m2 = new HashMap<String, Object>() {
			{
				put("a", "m2");
				put("b", new HashMap<String, Object>() {
					{
						put("a", "m2");
					}
				});
				// put("c", "m2");
				put("d", new ArrayList<Object>() {
					{
						add("l21");
						add("l22");
						add(new HashMap<String, Object>() {
							{
								put("b", "m2");
							}
						});
					}
				});
			}
		};

		assertThat(deepMerge(m1, m2), is(new HashMap<String, Object>() {
			{
				put("a", "m2");
				put("b", new HashMap<String, Object>() {
					{
						put("a", "m2");
						put("b", "m1");
					}
				});
				put("c", "m1");
				put("d", new ArrayList<Object>() {
					{
						add("l21");
						add("l22");
						add(new HashMap<String, Object>() {
							{
								put("a", "m1");
								put("b", "m2");
							}
						});
					}
				});
			}
		}));
	}
}
