package network.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IpCheckerTest {

	private static final IpChecker ipChecker = new IpChecker();

	@Test
	public void test() {
		String ip = null;

		try {
			ip = ipChecker.getIp();
		} catch (Exception e) {e.printStackTrace(); fail("");};

		assertTrue(ip != null);

		assertTrue(ip.length() <= 15);

		boolean isIp = true;

		for (char c : ip.toCharArray())
			if ((c != '.') && (c < '0' || c > '9'))
				isIp = false;

		assertTrue(isIp);
	}
}

