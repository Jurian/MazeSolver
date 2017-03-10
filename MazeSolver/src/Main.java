import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		String[] test = new String[]{
			"1011",
			"1001",
			"1101",
			"1001",
			"1011"
		};
		Boolean[][] data = Arrays.stream(test).map(line -> {
			return line.chars()
			.boxed()
			.map(c -> c == 1 ? true : false)
            .toArray(Boolean[]::new);
		}).toArray(Boolean[][]::new);
		
		System.out.println(Arrays.deepToString(data));
		
	}

}
