package com.androidmodule.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.androidmodule.utils.AMWeakList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * AMEvent
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMEvent {

	private static Map<String, AMEvent> eventMap = new HashMap<String, AMEvent>();
	private static String DEFAULT_EVENT = "AM_EVENT";

	public static AMEvent getDefault() {
		return getEvent(DEFAULT_EVENT);
	}

	public static AMEvent getEvent(String eventName) {
		if (eventMap.containsKey(eventName)) {
			return eventMap.get(eventName);
		}
		AMEvent event = new AMEvent();
		eventMap.put(eventName, event);
		return event;
	}

	private AMEvent() {
	}

	private final int WHAT = 0x01;
	private AMWeakList<Object> objects = new AMWeakList<Object>();

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT:
				for (Object observer : objects) {
					try {
						methoder(observer, "doEvent", EventMessage.class).invoke(msg.obj);
					} catch (Exception e) {
					}
				}
				break;
			default:
				break;
			}
		}
	};

	public void unregister(Object subscriber) {
		if (subscriber == null) {
			return;
		}
		if (objects.contains(subscriber)) {
			objects.remove(subscriber);
		}
	}

	public void register(Object subscriber) {
		if (subscriber == null) {
			return;
		}
		if (!objects.contains(subscriber)) {
			objects.add(subscriber);
		}
	}

	public void post(EventMessage message) {
		handler.obtainMessage(WHAT, message).sendToTarget();
	}

	public void postDelayed(final EventMessage message, int delayMillis) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				post(message);
			}
		}, delayMillis);
	}

	public void postAtTime(final EventMessage message, int uptimeMillis) {
		handler.postAtTime(new Runnable() {
			@Override
			public void run() {
				post(message);
			}
		}, uptimeMillis);
	}

	public static interface IEventMessage {
		void doEvent(EventMessage message);
	}

	public static abstract class EventReceiver implements IEventMessage {
	}

	public static class EventMessage {
		private int what = 0;
		private String action = "";
		private Object obj = null;
		private Bundle extras = new Bundle();

		public EventMessage(String action) {
			this.action = action;
		}

		public EventMessage(String action, int what) {
			this.action = action;
			this.what = what;
		}

		public EventMessage(String action, int what, Object obj) {
			this.action = action;
			this.what = what;
			this.obj = obj;
		}

		public String getAction() {
			return action;
		}

		public Object getObj() {
			return obj;
		}

		public int getWhat() {
			return what;
		}

		public void setExtras(Bundle extras) {
			this.extras = extras;
		}

		public Bundle getExtras() {
			return extras;
		}
	}

	private Methoder methoder(Object object, String method, Class<?>... parameterTypes) {
		return new Methoder(object.getClass(), object, method, parameterTypes);
	}

	private final class Methoder {

		private Method method;
		private Object object;

		private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
			Method method = null;
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					method = clazz.getDeclaredMethod(name, parameterTypes);
					return method;
				} catch (Exception e) {
				}
			}
			return null;
		}

		private Methoder(Class<?> cls, Object object, String method, Class<?>... parameterTypes) {
			try {
				this.object = object;
				this.method = getDeclaredMethod(cls, method, parameterTypes);
			} catch (Exception e) {
			}
		}

		public Object invoke(Object... args) throws Exception {
			if (method != null) {
				method.setAccessible(true);
				Object object = method.invoke(this.object, args);
				method = null;
				this.object = null;
				return object;
			}
			return null;
		}
	}

}
