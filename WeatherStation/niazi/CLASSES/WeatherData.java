package niazi.CLASSES;

import niazi.INTERFACES.*;
import java.util.ArrayList;

public class WeatherData implements Subject {
	
	private ArrayList<Observer> observers;
	private double temperature;
	private double humidity;
	private double pressure;
	
	public WeatherData(double temperature, double humidity, double pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		this.observers = new ArrayList<Observer>();
	}
	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);
	}

	@Override
	public void notifyObserver() {
		for(Observer o: this.observers) {
			o.update(this);
		}
	}
	public double getTemperature() {
		return temperature;
	}
	public double getHumidity() {
		return humidity;
	}
	public double getPressure() {
		return pressure;
	}
	
	public boolean setMeasurements(double temperature, double humidity, double pressure) {
		
		boolean hasChanged = this.measurementsChanged(temperature, humidity, pressure);
		
		if(hasChanged) {
			this.temperature = temperature;
			this.humidity = humidity;
			this.pressure = pressure;
			return true;
		}
		else {
			return false;
		}	
	}
	
	private boolean measurementsChanged(double temperature, double humidity, double pressure) {
		if(this.temperature != temperature || this.humidity != humidity || this.pressure != pressure) {
			return true;
		}
		else {
			return false;
		}
	}
}
