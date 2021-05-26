package com.revature.autosurvey.analytics.beans;

public class Data {
	private double data;
	private double delta;
	public double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}
	public double getDelta() {
		return delta;
	}
	public void setDelta(double delta) {
		this.delta = delta;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(data);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(delta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		if (Double.doubleToLongBits(data) != Double.doubleToLongBits(other.data))
			return false;
		if (Double.doubleToLongBits(delta) != Double.doubleToLongBits(other.delta))
			return false;
		return true;
	}
}
