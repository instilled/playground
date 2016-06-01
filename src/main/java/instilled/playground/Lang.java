package instilled.playground;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jboss.weld.exceptions.UnsupportedOperationException;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.RT;
import clojure.lang.Symbol;

public final class Lang {

	/**
	 * Deep merge two data structures. Works on {@link List}s and {@link Map}s.
	 * 
	 * @param left
	 * @param right
	 * @return a copy of <code>left</code> with values merged from
	 *         <code>right</code>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deepMerge(T left, T right) {

		if (left instanceof Map && right instanceof Map) {
			Map<Object, Object> l = new HashMap<>((Map<Object, Object>) left);
			Map<Object, Object> r = (Map<Object, Object>) right;
			for (Map.Entry<Object, Object> e : r.entrySet()) {
				Object k = e.getKey();
				if (r.containsKey(k)) {
					l.put(k, deepMerge(l.get(k), r.get(k)));
				}
			}
			return (T) l;
		} else if (left instanceof List && right instanceof List) {
			List<Object> l = new ArrayList<>((List<Object>) left);
			List<Object> r = (List<Object>) right;
			for (int i = 0; i < r.size(); i++) {
				if (r.get(i) != null) {
					l.set(i, deepMerge(l.get(i), r.get(i)));
				}
			}

			return (T) l;
		}

		return right;
	}

	/**
	 * Syntactic sugar to avoid handling checked {@link Exception} in
	 * {@link Stream} operations.
	 * 
	 * @param url
	 * @return
	 */
	public static InputStream openStream(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new IllegalStateException("Failed to read from URL!", e);
		}
	}

	public static URL resource(String resource) {
		return resource(resource, Lang.class.getClassLoader());
	}

	public static URL resource(String resource, ClassLoader context) {
		return context.getResource(resource);
	}

	/**
	 * A builder for Clojure interop.
	 */
	public static class Clj {

		static {
			try {
				Class.forName("clojure.java.api.Clojure");
			} catch (Exception e) {
				throw new IllegalStateException("Failed to start system.", e);
			}
		}

		final static private clojure.lang.Var REQUIRE = RT.var("clojure.core", "require");
		final static private Symbol NS_CLOJURE_CORE = Symbol.intern("clojure.core");

		public static Ns requireNs(String ns) {
			Symbol nsSym = Symbol.intern(ns);
			REQUIRE.invoke(nsSym);
			return new Ns(nsSym);
		}

		public static Ns ns(String ns) {
			return new Ns(ns);
		}

		public static Var nsVar(String ns, String name) {
			return new Ns(ns).var(name);
		}

		public static Fn nsFn(String ns, String name) {
			return new Ns(ns).fn(name);
		}

		public static Fn coreFn(String name) {
			return new Fn(NS_CLOJURE_CORE, name, false);
		}

		public static class Ns {
			private Symbol _ns;

			public Ns(String ns) {
				_ns = Symbol.intern(ns);
			}

			public Ns(Symbol ns) {
				_ns = ns;
			}

			public Var var(String var) {
				return new Var(_ns, var);
			}

			public Fn fn(String fn) {
				return new Fn(_ns, fn);
			}
		}

		public static class Var {
			protected IFn _var;

			public Var(Symbol ns, String name) {
				_var = Clojure.var(ns, name);
			}

			public void value() {
				throw new UnsupportedOperationException();
			}
		}

		public static class Fn extends Var {
			public Fn(Symbol ns, String name) {
				this(ns, name, true);
			}

			public Fn(Symbol ns, String name, boolean assertExists) {
				super(ns, name);
				// if (assertExists && !(boolean)
				// Clj.coreFn("fn?").invoke(_var)) {
				// throw new IllegalStateException(format("Expecting var to be a
				// function: %s/%s", ns, name));
				// }
			}

			public Object invoke() {
				return _var.invoke();
			}

			@SuppressWarnings("unchecked")
			public <T> T invoke(Object arg1) {
				return (T) _var.invoke(arg1);
			}

			@SuppressWarnings("unchecked")
			public <T> T invoke(Object arg1, Object arg2) {
				return (T) _var.invoke(arg1, arg2);
			}

			@SuppressWarnings("unchecked")
			public <T> T invoke(Object arg1, Object arg2, Object arg3) {
				return (T) _var.invoke(arg1, arg2, arg3);
			}

			@SuppressWarnings("unchecked")
			public <T> T invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
				return (T) _var.invoke(arg1, arg2, arg3, arg4);
			}

			@SuppressWarnings("unchecked")
			public <T> T invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
				return (T) _var.invoke(arg1, arg2, arg3, arg4, arg5);
			};
		}

		@SuppressWarnings("unchecked")
		public static Map<Object, Object> asMap(Object m) {
			return new HashMap<Object, Object>(
					(Map<Object, Object>) requireNs("clojure.walk").fn("stringify-keys").invoke(m));
		}

		public static Object get(Object obj, String cljKw) {
			Object kw = Clojure.var("clojure.core", "keyword").invoke(cljKw);
			return Clojure.var("clojure.core", "get").invoke(obj, kw);
		}
	}
}
