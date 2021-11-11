package niazi.CLASSES;

import niazi.INTERFACES.*;

public class SimpleForecast implements Observer, Displayable {
	
	private static double prevPressure = 0;
	private static boolean hasNotForecasted = true;
	
	// get a forecast based on temperature
	private String getTempStatement(double temperature) {
		String s = "";
		if(temperature > 75) {
			s = "It is hot outside";
		}
		else if(temperature <= 75 && temperature >= 60) {
			s = "It is warm outside";
		}
		else if(temperature >= 45 && temperature < 60){
			s = "It is cool outside";
		}
		else {
			s = "It is cold outside";
		}
		s = s + " with a temperature of " + temperature + " degrees Fahrenheit. ";
		return s;
	}
	
	// get a forecast based on humidity
	private String getHumidityStatement(double humidity) {
		String s = "";
		if(humidity > 75) {
			s = "It is very humid outside with a humidity of " + humidity + "%. There may be a higher risk of getting sick. ";
		}
		else if(humidity >= 50 && humidity <= 75) {
			s = "It is mildly humid outside with a humidity of " + humidity + "%. Limited physical activity recommended. ";
		}
		else if (humidity > 30 && humidity < 50) {
			s = "It is perfectly humid with a humidity of " + humidity + "%. ";
		}
		else {
			s = "It is very dry with a humidity of " + humidity + "%. Please do not go outdoors. ";
		}
		
		return s;
	}
	// get a forecast based on pressure
	private String getBarometricStatement(double pressure) {
		String s = "";

		if(pressure > prevPressure) {
			s = "Atmospheric pressure increase. Weather will improve.";
		}
		else if( (pressure - prevPressure > -50) && (pressure - prevPressure <= 0) ) {
			s = "Weather change not likely.";
		}
		else {
			s = "High atmospheric pressure drop. High chance of wind, storms, and rain.";
		}
		
		return s;
	}
	@Override
	public void update(WeatherData wd) {
		display(wd);	
	}
	
	// display the new forecast
	@Override
	public void display(WeatherData wd) {
		double temp = wd.getTemperature();
		double humid = wd.getHumidity();
		double press = wd.getPressure();
		
		if(hasNotForecasted) {
			prevPressure = press;
			hasNotForecasted = false;
		}
		
		String tempStatement = this.getTempStatement(temp);
		String humStatement = this.getHumidityStatement(humid);
		String pressStatement = this.getBarometricStatement(press);
		
		System.out.println("_______________SIMPLE FORECAST__________________");
		System.out.println(tempStatement);
		System.out.println(humStatement); 
		System.out.println(pressStatement);
		System.out.println();
	}
	
	
	
}
