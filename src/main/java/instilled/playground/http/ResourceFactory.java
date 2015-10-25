package instilled.playground.http;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Application;

@Named("ResourceFactory")
public class ResourceFactory extends Application {

	@Inject
	private Instance<Resource> resources;

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> resourceList = new LinkedHashSet<Class<?>>();
		resources.forEach(resource -> resourceList.add(resource.getClass()));
		return resourceList;
	}

}