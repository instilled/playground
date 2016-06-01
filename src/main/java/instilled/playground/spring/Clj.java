package instilled.playground.spring;

import java.util.HashMap;
import java.util.Map;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

/**
 * A builder for Clojure interop.
 */
public class Clj {

	static {
		try {
			Class.forName("clojure.java.api.Clojure");
		} catch (Exception e) {
			throw new IllegalStateException("Failed to start system.", e);
		}
	}

	final static private Var REQUIRE = RT.var("clojure.core", "require");
	final static private Symbol NS_CLOJURE_CORE = Symbol.intern("clojure.core");

	public static InNsBuilder requireNs(String ns) {
		Symbol nsSym = Symbol.intern(ns);
		REQUIRE.invoke(nsSym);
		return new InNsBuilder(nsSym);
	}

	public static InNsBuilder ns(String ns) {
		return new InNsBuilder(ns);
	}

	public static IFnBuilder nsFn(String ns, String name) {
		return new InNsBuilder(ns).fn(name);
	}

	public static IFnBuilder coreFn(String name) {
		return new InNsBuilder(NS_CLOJURE_CORE).fn(name);
	}

	public static class InNsBuilder {

		private Symbol _ns;

		public InNsBuilder(String ns) {
			_ns = Symbol.intern(ns);
		}

		public InNsBuilder(Symbol ns) {
			_ns = ns;
		}

		public IFnBuilder fn(String fn) {
			return new IFnBuilder(_ns, fn);
		}
	}

	public static class IFnBuilder {

		private IFn _var;

		public IFnBuilder(Symbol ns, String name) {
			_var = Clojure.var(ns, name);
		}

		public Object invoke() {
			return _var.invoke();
		}

		public IFn iFn() {
			return _var;
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

		@SuppressWarnings("unchecked")
		public <T> T invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
			return (T) _var.invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		};

		@SuppressWarnings("unchecked")
		public <T> T invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) {
			return (T) _var.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
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

	/**
	 * Explode <code>my.ns/fn</code> into parts <code>ns</code> and
	 * <code>fn</code> to be used with {@link Clj#nsFn(String, String)}.
	 *
	 * @param decl
	 *            matching <code>my.ns/fn</code>
	 * @return a String[] with exactly two entries:
	 *         <code>[0] = ns [1] = fn</code>
	 * @throws IllegalStateException
	 *             if pattern doesn't match
	 */
	public static String[] explodeNsFnDecl(String decl) {
		String[] parts = decl.split("/");
		if (parts.length != 2) {
			throw new IllegalStateException("Expecting declaration to match pattern 'my.ns/fn' but was " + decl);
		}
		return parts;
	}

	/**
	 * @See {@link #explodeNsFnDecl(String)}
	 *
	 * @param decl
	 * @return {@link IFnBuilder}
	 */
	public static IFnBuilder explodeNsFnDeclAsFn(String decl) {
		String parts[] = explodeNsFnDecl(decl);
		return requireNs(parts[0]).fn(parts[1]);
	}
}