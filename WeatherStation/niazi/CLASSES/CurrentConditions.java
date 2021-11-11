package niazi.CLASSES;

import niazi.INTERFACES.*;

public class CurrentConditions implements Displayable, Observer {

	@Override
	public void update(WeatherData wd) {
		display(wd);	
	}
	
	// display current measurements
	@Override
	public void display(WeatherData wd) {
		System.out.println("_______________Current Data_____________________");
		System.out.println("Temperature: " + wd.getTemperature());
		System.out.println("Humidity: " + wd.getHumidity() + "%");
		System.out.println("Barometric Pressure: " + wd.getPressure() + " mb");
	}

}
