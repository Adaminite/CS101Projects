package niazi.INTERFACES;

public interface Subject {

	public abstract void registerObserver(Observer o);
	public abstract void removeObserver(Observer o);
	public abstract void notifyObserver();
}
