package instilled.playground;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

import instilled.playground.http.api.CljFn;

@Singleton
public class CljInjector {

	@Produces
	@CljFn
	public Lang.Clj.Fn createFn(InjectionPoint injectionPoint) {
		CljFn a = injectionPoint.getAnnotated().getAnnotation(CljFn.class);
		String[] fqfn = a.value().split("/");
		if (fqfn.length < 2) {
			throw new IllegalStateException("Invalid Clj fn name. Expecting 'ns/fn' got " + a.value());
		}

		return Lang.Clj.requireNs(fqfn[0]).fn(fqfn[1]);
	}
}
