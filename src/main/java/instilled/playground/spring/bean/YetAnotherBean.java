package instilled.playground.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YetAnotherBean {


	private IBean _bean;

	@Autowired
	public YetAnotherBean(IBean bean) {
		_bean = bean;
		_bean.doSomething(this);
	}
}
