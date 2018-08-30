package v35;

public class Lektion2 {
	public static void main(String[] args) {
		
		System.out.println(sum(10, 100, 200, 300, 400));
	}
	
	public static int sum(int... numbers) {
		int sum = 0;
		for (int i : numbers) {
			sum += i;
		}
		return sum;
	}
}
