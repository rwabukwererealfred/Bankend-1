package com.BankEnd1.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.BankEnd1.model.Users;
import com.BankEnd1.repository.UserRepository;
//import com.BankEnd1.repository.UserRepository;


@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepo;
	private static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	private List<Users>users = new ArrayList<>();
	
	
	 public static boolean hasExcelFormat(MultipartFile file) {

		    if (!TYPE.equals(file.getContentType())) {
		      return false;
		    }

		    return true;
		  }

	@Override
	public void saveToList(MultipartFile file) throws IOException {
		if(!hasExcelFormat(file)) throw new RuntimeException("Invalid file type");
		List<Users>list = parseExcelFile(file.getInputStream());
		users = new ArrayList<>();
		users.addAll(list);
		
	}

	@Override
	public List<Users> usersList() {
		return users.stream().filter(i->i.getErrors()!= null).collect(Collectors.toList());
	}
	
	public static List<Users> parseExcelFile(InputStream is) {
		try {
			List<Users> lstUsers = new ArrayList<Users>();
    		Workbook workbook = new XSSFWorkbook(is);
     
    		Sheet sheet = workbook.getSheetAt(0);
    		Iterator<Row> rows = sheet.iterator();
    		
    		
    		
    		int rowNumber = 0;
    		while (rows.hasNext()) {
    			Row currentRow = rows.next();
    			
    			// skip header
    			if(rowNumber == 0) {
    				rowNumber++;
    				continue;
    			}
    			
    			Iterator<Cell> cellsInRow = currentRow.iterator();

    			Users cust = new Users();
    			
    			String error = "";
    			int cellIndex = 0;
    			while (cellsInRow.hasNext()) {
    				
    				Cell currentCell = cellsInRow.next();
    				
    				if(cellIndex==0) { // ID
    					cust.setId((long) currentCell.getNumericCellValue());
    				} else if(cellIndex==1) { // NATIONAL ID
    					cust.setNID((long)currentCell.getNumericCellValue());
    				} else if(cellIndex==2) { // NAMES
    					cust.setNames(currentCell.getStringCellValue());
    				} else if(cellIndex==3) { // PHONE NUMBER
    					cust.setPhoneNumber((long)currentCell.getNumericCellValue());
    				} else if(cellIndex==4) { // GENDER
    					cust.setGender(currentCell.getStringCellValue());
    				} else if(cellIndex==5) { // EMAIL
    					cust.setEmail(currentCell.getStringCellValue());
    				}
    				
    				cellIndex++;
    			}
    			System.out.println(new Validation().validatePhone(String.valueOf(cust.getPhoneNumber())));
				   
    			if(cust.getNames().equals("") || cust.getNames() == null) {
    				error +="USER NAME MUST NOT BE EMPTY, ";
    			}
				if(!new Validation().validateId(String.valueOf(cust.getNID()))) {
					error +="NID IS NOT VALID,";
					cust.setErrors(error);
				} if(!new Validation().validatePhone(String.valueOf(cust.getPhoneNumber()))) {
					error +=" PHONE NUMBER IS INCORRECT,";
					cust.setErrors(error);
				}
    			
    			lstUsers.add(cust);
    		}
    		
    		// Close WorkBook
    		workbook.close();
    		
    		return lstUsers;
        } catch (IOException e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
	}

	@Override
	public void saveIntoDB() {
		if(users.size() == 0) throw new RuntimeException("Empty list");
		if(usersList().size() ==0) {
			userRepo.saveAll(users);
		}else {
			throw new RuntimeException("Please remove error on list and save it again");
		}
		
	}

}
