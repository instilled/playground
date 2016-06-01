package instilled.playground;

import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;

public class Configuration {

    @SuppressWarnings("unchecked")
    public static final <T> T load(Class<T> clazz, URL... urls) {
        return load(defaultConverters(), clazz, urls);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T load(Map<Class<?>, IConverter<?, Object>> converters, Class<T> clazz, URL... urls) {
        Yaml yaml = new Yaml();
        Object obj = stream(urls) //
                .map(Lang::openStream) //
                .map(yaml::load) //
                .reduce(Lang::deepMerge) //
                .orElseThrow(IllegalStateException::new);
        return proxy(converters, clazz, obj);
    }

    @SuppressWarnings("unchecked")
    private static <T> T proxy(Map<Class<?>, IConverter<?, Object>> converters, Class<T> clazz, Object obj) {
        if (obj instanceof Map && clazz.isInterface()) {
            Map<String, Object> t = (Map<String, Object>) obj;
            InvocationHandler handler = new YamlBackedInvocationHandler(t);
            Class<?> proxyClass = Proxy.getProxyClass(clazz.getClassLoader(), new Class[]{clazz});
            for (Method m : clazz.getDeclaredMethods()) {
                if (t.containsKey(m.getName())) {
                    t.put(m.getName(), proxy(converters, m.getReturnType(), t.get(m.getName())));
                }
            }
            try {
                return (T) proxyClass.getConstructor(new Class[]{InvocationHandler.class})
                        .newInstance(new Object[]{handler});
            } catch (Exception e) {
                throw new IllegalStateException("Failed to load configuration!", e);
            }
        } else if (converters.containsKey(clazz)) {
            return (T) converters.get(clazz).convert(obj);
        } else {
            return (T) obj;
        }

    }

    /**
     * Default converter registry.
     */
    public static Map<Class<?>, IConverter<?, Object>> defaultConverters() {
        Map<Class<?>, IConverter<?, Object>> converters = new HashMap<>();

        // Character types
        converters.put(Character.class, c -> ((String) c).charAt(0));

        // Numeric types
        converters.put(Long.class, c -> new Long((int) c));

        return converters;
    }

    public interface IConverter<T, S> {
        T convert(S source);
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
