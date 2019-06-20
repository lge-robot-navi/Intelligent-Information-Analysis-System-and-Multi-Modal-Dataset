import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer, Boolean> cards = new HashMap<>();
		for (int i = 1; i <= 100; i++) {
			cards.put(i, false);
		}
		
		int step = 2;
		
		while (step-1 < 100) {
			for (int i = step; i <= 100; i+=step) {
				if (i != step) {
					cards.put(i, !cards.get(i));
				} else {
					cards.put(i, !cards.get(i));
				}
			}
			step++;
		}
		
		Set key = cards.keySet();
		
		for (Iterator iterator = key.iterator(); iterator.hasNext();) {
			Integer keyName = (Integer) iterator.next();
			Boolean keyValue = (Boolean) cards.get(keyName);
			if (!keyValue) {
				System.out.println(keyName);
			}
		}
	}

}
