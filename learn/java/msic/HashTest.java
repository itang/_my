import java.util.HashMap;
import java.util.Map;

public class HashTest {
	public static void main(String[] args) {
		@SuppressWarnings("serial")
		Map<String, String> map = new HashMap<String, String>() {
			public void p(String a, String b) {
				put(a, b);
			}

			{
				p("a", "itang");
				p("b", "live.tang");
			}
		};

		for (Map.Entry<String, String> it : map.entrySet()) {
			System.out.println(it.getKey() + ":" + it.getValue());
		}
	}
}
