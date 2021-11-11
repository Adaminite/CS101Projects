package niazi.CLASSES;

import niazi.INTERFACES.*;
import java.util.ArrayList;

public class WeatherStatistics implements Displayable, Observer {
	
	private static double maxTemp = 0;
	private static double minTemp = 100000;
	private static double averageTemp = 0;
	private static ArrayList <Double> temps = new ArrayList<>();
	
	@Override
	public void update(WeatherData wd) {
		
		temps.add(wd.getTemperature());
		double sum = 0;
		for(double temp: temps) {
			sum += temp;
		}
		averageTemp = sum / temps.size();
		
		if(wd.getTemperature() > maxTemp) {
			maxTemp = wd.getTemperature();
		}
		
		if(wd.getTemperature() < minTemp) {
			minTemp = wd.getTemperature();
		}
		
		display(wd);
	}
	
	// display the weather statistics from the website-obtained data
	@Override
	public void display(WeatherData wd) {
		System.out.println("_______________Weather Statistics_______________");
		System.out.println("Highest Temp: " + maxTemp);
		System.out.println("Lowest Temp: " + minTemp);
		System.out.println("Average Temp: " + averageTemp);
		
	}
}
