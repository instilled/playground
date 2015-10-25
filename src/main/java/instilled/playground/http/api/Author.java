package instilled.playground.http.api;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import instilled.playground.Lang;
import instilled.playground.http.Resource;

@Path("/author")
public class Author implements Resource {

	// @Context //injected response proxy supporting multiple threads
	// private HttpServletResponse response;
	
	@Inject
    Event<RequestEvent> creditEvent;

	@Inject
	@CljFn("http.service.author/say-hello")
	private Lang.Clj.Fn sayHello;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String bySize(@DefaultValue("10") @QueryParam("size") int size) {
		creditEvent.fire(new RequestEvent("awesome!"));
		return sayHello.invoke(size);
	}

	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public AuthorJson json(@DefaultValue("10") @QueryParam("size") int size) {
		return new AuthorJson();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String byId(@PathParam("id") String id, @DefaultValue("10") @QueryParam("size") int size) {
		return "id: " + id;
	}
}