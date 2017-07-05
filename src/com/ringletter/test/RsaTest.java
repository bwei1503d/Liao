package com.ringletter.test;

import com.ringletter.cipher.JNCryptorUtils;
import com.ringletter.cipher.SecurityUtils;

public class RsaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		

		try {
			

			System.out.println(JNCryptorUtils.getInstance().decryptData("LcmJxmzKMlmp0GfahT3+jQ==", "837eCi8010n54Pqc"));

//			System.out.println(SecurityUtils.decrypt("TQyLtHx1F5RJutb0qLIRRP0CiBz4Hg/2C5CXlivUQqfBFhCR43XLs9/u4FjxCvncCmyQSadR8dIxOY2iQWzL7bxy1pqZ7gX+Qcb78n2NVXQZmhNAkyDKsLrHZ6IZ3tIw5GKJ6oBcOnDCf3CcGAL0lPAMNbODZipFyIXv4oeK8FE="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
