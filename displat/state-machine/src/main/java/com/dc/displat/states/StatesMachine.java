/**  

 * Copyright © 2018pact. All rights reserved.

 *

 * @Title: StatesMachine.java

 * @Prject: state-machine

 * @Package: com.dc.displat.states

 * @Description: TODO

 * @author: gavin.lyu  

 * @date: 2018年12月25日 下午4:33:08

 * @version: V1.0  

 */
package com.dc.displat.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatesMachine<S,E>{
	
	
	private final StateMonitor monitor;	
	
	private final Map<S,E> stateEvent ;
	
	private final Map<E,EventAction> eventAction ;
	
	private final State<S> state;
	
	private final List<StateChain<S>> stateChains;
	
	private final ScheduledExecutorService scheduleExcutor = Executors.newScheduledThreadPool(8);
	
	
	private StatesMachine(Registry<S,E> registry) {
		this.monitor=registry.monitor;
		this.stateEvent=registry.stateEvent;
		this.eventAction=registry.eventAction;
		this.state=registry.state;
		this.stateChains=registry.stateChains;
	}
	
	public void changeState(S s) {
		
		state.changeState(s);
		
		E e = stateEvent.get(s);
		
		if(e!=null) {
			EventAction action = eventAction.get(e);
			if(action!=null) {
				action.execute();
			}
		}
		
		for(StateChain<S> c:stateChains) {
			if(c.getFromState().equals(s)) {
				scheduleExcutor.schedule(new Runnable() {
					@Override
					public void run() {
						changeState(c.getToState());
					}
				}, c.getCapcity(), TimeUnit.MILLISECONDS);
				return;
			}
		}
		
	}
	
	public long getStateCapcity(S currentState) {
		for(StateChain<S> c:stateChains) {
			if(c.getFromState().equals(currentState)) {
				return c.getCapcity();
			}
		}
		return 0;
	}
	
	public void fire() {
		monitor.run();
	}
	
	
	
	public static class Registry<S,E>{
		
		private StateMonitor monitor;
		
		private State<S> state;
		
		private final Map<S,E> stateEvent = new HashMap<>();
		
		private final Map<E,EventAction> eventAction = new HashMap<>();
		
		private final List<StateChain<S>> stateChains = new ArrayList<>();
		
		public Registry<S,E> registerMonitor(StateMonitor monitor) {
		    this.monitor = monitor;
		    return this;
		}
		
		public Registry<S,E> register(S s,E e,EventAction action) {
			stateEvent.put(s, e);
			eventAction.put(e, action);
			return this;
		}
		
		public Registry<S,E> registerState(State<S> state){
			this.state=state;
			return this;
		}
		
		public Registry<S,E> fromTo(S beginState,S endState,long delay){
			StateChain<S> chain = new StateChain<>(beginState,endState,delay);
			this.stateChains.add(chain);
			return this;
		}
		
	}
	
	

	private static class StateChain<S>{
		private S fromState;
		private S toState;
		private long capcity;
		
		public StateChain (S fromState,S toState,long capcity){
			this.fromState=fromState;
			this.toState=toState;
			this.capcity=capcity;
		}
		
		/**
		 * @return the fromState
		 */
		public S getFromState() {
			return fromState;
		}
		
		/**
		 * @return the toState
		 */
		public S getToState() {
			return toState;
		}
		
		/**
		 * @return the capcity
		 */
		public long getCapcity() {
			return capcity;
		}
		
	}
	
}
