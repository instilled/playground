package instilled.playground;

import static java.util.Arrays.stream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Configuration {

	@SuppressWarnings("unchecked")
	public static final <T> T load(Class<T> clazz, URL... urls) {
		Yaml yaml = new Yaml();
		Object obj = stream(urls) //
				.map(Lang::openStream) //
				.map(yaml::load) //
				.reduce(Lang::deepMerge) //
				.orElseThrow(IllegalStateException::new);
		return proxy(clazz, (Map<String, Object>) obj);
	}

	@SuppressWarnings("unchecked")
	private static <T> T proxy(Class<T> clazz, Object obj) {
		if (obj instanceof Map && clazz.isInterface()) {
			Map<String, Object> t = (Map<String, Object>) obj;
			InvocationHandler handler = new YamlBackedInvocationHandler(t);
			Class<?> proxyClass = Proxy.getProxyClass(clazz.getClassLoader(), new Class[] { clazz });
			for (Method m : clazz.getDeclaredMethods()) {
				if (t.containsKey(m.getName())) {
					t.put(m.getName(), proxy(m.getReturnType(), t.get(m.getName())));
				}
			}
			try {
				return (T) proxyClass.getConstructor(new Class[] { InvocationHandler.class })
						.newInstance(new Object[] { handler });
			} catch (Exception e) {
				throw new IllegalStateException("Failed to load configuration!", e);
			}
		} else {
			return (T) obj;
		}
	}

	private static class YamlBackedInvocationHandler implements InvocationHandler {
		private final Map<String, Object> _backingMap;

		public YamlBackedInvocationHandler(Map<String, Object> backingMap) {
			_backingMap = backingMap;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// if (method.isDefault() &&
			// !_backingMap.containsKey(method.getName())) {
			// final Class<?> declaringClass = method.getDeclaringClass();
			// return
			// MethodHandles.lookup().in(declaringClass).unreflectSpecial(method,
			// declaringClass).bindTo(proxy)
			// .invokeWithArguments(args);
			// }
			return _backingMap.get(method.getName());
		}
	}
}
