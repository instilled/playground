package instilled.playground.http;

import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;

import instilled.playground.http.api.TestEvent;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

public class EmbeddedServer {

	private final UndertowJaxrsServer _server = new UndertowJaxrsServer();
	private String _contextPath = "/app-name";
	private String _deploymentName = "app-name";
	private String _appPath = "/api";
	private Class<? extends Application> _resourcesClass;

	public EmbeddedServer(final String host, final Integer port) {
		Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
		_server.start(serverBuilder);
	}

	public EmbeddedServer contextPath(final String contextPath) {
		this._contextPath = contextPath;
		return this;
	}

	public EmbeddedServer deploymentName(final String deploymentName) {
		_deploymentName = deploymentName;
		return this;
	}

	public EmbeddedServer appPath(final String appPath) {
		_appPath = appPath;
		return this;
	}

	public EmbeddedServer resourcesClass(final Class<? extends Application> resourcesClass) {
		_resourcesClass = resourcesClass;
		return this;
	}

	private DeploymentInfo deployApplication() {
		final ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());
		deployment.setApplicationClass(_resourcesClass.getName());
		return _server.undertowDeployment(deployment, _appPath);
	}

	public DeploymentInfo deployApplication2() {
		DeploymentInfo di = deployment()//
				.setClassLoader(MyGenericServlet.class.getClassLoader()).setContextPath(_contextPath)//
				.setDeploymentName("w.war") //
				.setContextPath("/abc") //
				.addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class))//
				.addServlets(//
						servlet("MyGenericServlet", //
								MyGenericServlet.class) //
										.addInitParam("message", "Hello World")//
										.addMapping("/mygeneric")//
										.setLoadOnStartup(1));//
		
		return di;
	}

	public void start() {
		final DeploymentInfo deploymentInfo = deployApplication() //
				.setClassLoader(EmbeddedServer.class.getClassLoader()) //
				.setContextPath(_contextPath) //
				.setDeploymentName(_deploymentName) //
				.addListeners(Servlets.listener(Listener.class));

		_server.deploy(deploymentInfo);
		_server.deploy(deployApplication2());
	}

	private static class MyGenericServlet extends GenericServlet {

		private static final long serialVersionUID = 6516214582202883633L;

		@Inject
		Event<TestEvent> testEvent;

		@Override
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			System.out.println("init param: " + config.getInitParameter("message"));
			testEvent.fire(new TestEvent("100"));
		}

		@Override
		public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
			System.out.println(req);
			res.setContentType("text/plain");

			PrintWriter out = res.getWriter();
			out.println("Hello.");
		}

	}
}