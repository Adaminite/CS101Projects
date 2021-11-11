package niazi.MAIN;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;

import niazi.CLASSES.*;


public class niaziWeatherStation {
	
	// relative path and filename to store data obtained from the web
	public static String DIR = ".\\src\\niazi\\MAIN\\";
	public static String fileName = "WeatherSource" ;
	
	// write weather data from the national weather service's website (for Baltimore, MD area) to file
	public static void getWeatherSource() throws Exception {
		URL weatherGov = new URL("https://forecast.weather.gov/MapClick.php?lat=39.20792430000006&lon=-76.93355979999995#.YJNhTcCSlPY");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(weatherGov.openStream()));
		BufferedWriter writer = new BufferedWriter(new FileWriter(DIR + fileName));
		
		String line;
		while( (line = reader.readLine()) != null) {
			writer.write(line);
			writer.write("\n");
		}
		reader.close();
		writer.close();
		
	}
	
	
	// split the data string so each respective data value is in its own string
	public static String[] splitString(String s) {
		
		String[] splitS = s.split("</tr>");
		return splitS;
		
	}
	
	// extract numeric data values for temp, humidity, and pressure from the HTML 
	public static double[] getData(String[] data) {
		
		DecimalFormat df = new DecimalFormat("####.##");
		
		String tempHumid = data[0];
		String[] th = tempHumid.split("<p class=\"myforecast-current-lrg\">");
		

		String[] newData = th[1].split("<td>");
		
		String temp = newData[0].substring(0, 2);
		temp = df.format(Double.parseDouble(temp));
		
		String humidity = newData[1].substring(0,2);
		humidity = df.format(Double.parseDouble(humidity));
		
		
		String pressureString = data[2];
		String[] p = pressureString.split("<td>");
		p = p[1].split(" in");
		String press = p[0];
		String pressure = df.format(Double.parseDouble(press) * 33.90666667);
		
		
		double[] finalData = {Integer.parseInt(temp), Double.parseDouble(humidity), Double.parseDouble(pressure)};
		
		return finalData;
		
	}
	
	// get the data from the file
	public static double[] parseWeatherSource() throws Exception {
		
		BufferedReader reader = new BufferedReader(new FileReader(DIR + fileName));
		
		String line;
		String info = "";
		while( (line = reader.readLine()) != null ) {
			info += line;
		}
		reader.close();
		
		String[] temp = splitString(info);
		
		double[] data = getData(temp);
		
		return data;
		
	}
	
	public static void main(String[] args) throws Exception {
		
		
		// create subjects and observers
		WeatherData wd = new WeatherData(0, 0, 0);
		CurrentConditions cc = new CurrentConditions();
		WeatherStatistics ws = new WeatherStatistics();
		SimpleForecast sf = new SimpleForecast();
		
		wd.registerObserver(cc);
		wd.registerObserver(ws);
		wd.registerObserver(sf);
		
		// run the program for 24 hours, since the website updates its weather hourly
		int startTime = (int) System.currentTimeMillis();
		int numUpdates = 1;
		int duration = 0;
		
		do {

			try {
				
				// retrieve data from the website 
				getWeatherSource();
				double[] weatherData = parseWeatherSource();
				double temperature = weatherData[0];
				double humidity = weatherData[1];
				double pressure = weatherData[2];
			
				// see if the measurements have changed from the previous update
				boolean measurementsChanged = wd.setMeasurements(temperature, humidity, pressure);
			
				// if they have, then notifyObservers (update them)
				if(measurementsChanged) {
					System.out.println("\t\tUpdate #" + numUpdates);
					System.out.println("------------------------------------------------");
					
					wd.notifyObserver();
					
					System.out.println();
					numUpdates++;
				}
			} 
			
			// this catches array index errors that occasionally arise from the updating HTML
			// of the web page
			catch(ArrayIndexOutOfBoundsException e){}
			
			// check to see if 24 hours have elapsed in this program
			int endTime = (int) System.currentTimeMillis();
			duration = endTime - startTime;
			
		} while(duration < 86400000);
	}

}
