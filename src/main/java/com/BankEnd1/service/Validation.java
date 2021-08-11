package com.BankEnd1.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Validation {

	public boolean validatePhone(String phone) {
		if (phone.length() == 12) {
			if (phone.startsWith("25078") || phone.startsWith("25072") || phone.startsWith("25073")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	public boolean validateId(final String id) {
		if (!id.isEmpty() || id.length() > 0 || id != null || id != "") {
			if (id.startsWith("1")) {
				if (id.trim().replace(" ", "").length() == 16) {
					String check = id.trim().replace(" ", "").substring(1, 5);
					String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
					String val[] =  today.split("/");
					int res = Integer.valueOf(val[2])-Integer.valueOf(check);
					if (res>=16) {
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}
			} else {
				return false;
			}
		} else
			return false;
	}
	
}
