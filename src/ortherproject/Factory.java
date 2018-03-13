package ortherproject;

public class Factory {

	public static IHumen Instence(String className){
			if ("Person".equals(className)) {
				return new Person();
			}
		return null;
	}
}
