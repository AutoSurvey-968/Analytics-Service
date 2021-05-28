package com.revature.autosurvey.analytics.beans;

public class Data {
	private double datum;
	private double delta;
	public double getDatum() {
		return datum;
	}
	public void setDatum(double datum) {
		this.datum = datum;
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
		temp = Double.doubleToLongBits(datum);
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
		if (Double.doubleToLongBits(datum) != Double.doubleToLongBits(other.datum))
			return false;
		return Double.doubleToLongBits(delta) == Double.doubleToLongBits(other.delta);
	}
	@Override
	public String toString() {
		return "Data [data=" + datum + ", delta=" + delta + "]";
	}
}
