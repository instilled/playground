package instilled.playground.http.api;

import javax.enterprise.event.Observes;
import javax.inject.Named;

@Named("RequestEventHandler")
public class RequestEventHandler {

	public void onRequestEvent(@Observes TestEvent event) {
		
		System.out.println("Processing event: " + event.n());
	}
}
