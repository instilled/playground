package instilled.playground.http;

public class Main {
	public static void main(String[] args) {
		new EmbeddedServer("localhost", 8080) //
				.contextPath("/myapp") //
				.deploymentName("myapp") //
				.appPath("/api") //
				.resourcesClass(ResourceFactory.class) //
				.start();
	}
}
