package instilled.playground.http.api;

public class RequestEvent {
	private String _n;

	public RequestEvent(String n) {
		_n = n;
	}

	public String n() {
		return _n;
	}
}
