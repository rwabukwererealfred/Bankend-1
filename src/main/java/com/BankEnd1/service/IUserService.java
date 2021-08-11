package com.BankEnd1.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.BankEnd1.model.Users;

public interface IUserService {

	public void saveToList(MultipartFile file)throws IOException;
	public List<Users>usersList();
	public void saveIntoDB();
}
