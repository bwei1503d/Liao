package com.ringletter.test;

import com.ringletter.cipher.JNCryptorUtils;
import com.ringletter.cipher.SecurityUtils;

public class RsaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		

		try {
			

//			System.out.println(JNCryptorUtils.getInstance().decryptData("BEEst6ob8z+BlaDYJo95bQ==", "251JnB9F98rL8847"));

			System.out.println(SecurityUtils.decrypt("z+Csvb8CrK6ogV4R+bNsmYlKhis0kfUe+U8Bw4i4VXwE6g4sIGcjoJooOr6VihbRmFxyjZLL0OHH2pbgCv8O1f/HgtFUmGxzLjzb33R7n1Dz0QcRfsblD3CHxnkDLlDESL85eQsktpJsxyvIa/rI7Wv+MXz+QSAM7OGYAy9w87g="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
