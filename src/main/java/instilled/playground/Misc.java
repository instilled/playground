package instilled.playground;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

public class Misc {

	@SuppressWarnings("unused")
	public static void callableSupplierAndFnsByRef() throws Exception {
		Ftt s = MyFactory.create(Ftt::new);
		String john = MyFactory.create2(String::new);

		Callable<Ftt> fn = Ftt::new;
		fn.call();

		Function<Void, Ftt> fn2 = Ftt::new;
		fn2.apply(null);

		System.out.println();
	}

	public static interface MyFactory {
		static Ftt create(Supplier<Ftt> supplier) {
			return supplier.get();
		}

		static String create2(Supplier<String> supplier) {
			return supplier.get();
		}
	}

	public static class Ftt {

		public Ftt() {
			System.out.println("in no-args constructor");
		}

		public Ftt(Void v) {
			System.out.println("in void constructor");
		}
	}
}
