package niazi.INTERFACES;

import niazi.CLASSES.WeatherData;
public interface Observer {

	public abstract void update(WeatherData wd);
}
