/**  

 * Copyright © 2018pact. All rights reserved.

 *

 * @Title: State.java

 * @Prject: state-machine

 * @Package: com.dc.displat.states

 * @Description: TODO

 * @author: gavin.lyu  

 * @date: 2018年12月25日 下午5:18:35

 * @version: V1.0  

 */
package com.dc.displat.states;

public interface State<S>{

	
	public void changeState(S s);
	
	public S getCurrentState();
	
	
}
