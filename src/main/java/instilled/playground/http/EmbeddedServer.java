package instilled.playground.http;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;

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
		this._server.start(serverBuilder);
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

	public void start() {
		final DeploymentInfo deploymentInfo = deployApplication() //
				.setClassLoader(EmbeddedServer.class.getClassLoader()) //
				.setContextPath(_contextPath) //
				.setDeploymentName(_deploymentName) //
				.addListeners(Servlets.listener(Listener.class));

		_server.deploy(deploymentInfo);
	}
}