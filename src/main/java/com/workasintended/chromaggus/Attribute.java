package com.workasintended.chromaggus;

public class Attribute<T> {
	private T value;
	private T max;
	public Attribute(T value, T max) {
		super();
		this.value = value;
		this.max = max;
	}

	

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}



	public T getMax() {
		return max;
	}



	public void setMax(T max) {
		this.max = max;
	}
}
