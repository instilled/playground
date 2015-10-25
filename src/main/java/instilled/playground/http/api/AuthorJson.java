package instilled.playground.http.api;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class AuthorJson implements Serializable {

	private static final long serialVersionUID = 1L;

	private Float value = 1.123456F;
	
	@XmlElement
	public Float getValue() {
		return value;
	}
	
	public void setValue(Float value) {
		this.value = value;
	}
	
}