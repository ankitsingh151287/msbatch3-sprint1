package com.sl.ms.sprint1.superleaguesprint1.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sl.ms.sprint1.superleaguesprint1.util.InventoryBean;

@Service
public class InventoryService {
	public void getInventoryDetails() throws Exception {
		List<InventoryBean> inventoryBeanList = new ArrayList<InventoryBean>();
		MultipartFile multipartFile = new MockMultipartFile("Inventory_Summary.xlsx", new FileInputStream(new File(""
				+ "C:\\Spring_Workspace\\Git Checkin\\msbatch3-sprint1\\src\\main\\resources\\Inventory_Summary.xlsx")));
		
		XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
		for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
			InventoryBean tempInventoryBean = new InventoryBean();
			XSSFRow row = worksheet.getRow(i);
			tempInventoryBean.setId((int) row.getCell(0).getNumericCellValue());
			tempInventoryBean.setName(row.getCell(1).getStringCellValue());
			tempInventoryBean.setPrice(row.getCell(2).getNumericCellValue());
			tempInventoryBean.setStock((int)row.getCell(3).getNumericCellValue());
			tempInventoryBean.setSales((int)row.getCell(4).getNumericCellValue());
			tempInventoryBean.setOrderDate(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(row.getCell(5).getDateCellValue()) ));
			inventoryBeanList.add(tempInventoryBean);
		}

		System.out.println( "Report Answer 1 : Stock Summary Per Day");
		Map<LocalDate, List<InventoryBean>> StockSummaryPerDay=
				inventoryBeanList.stream().collect(Collectors.groupingBy(InventoryBean::getOrderDate));
		StockSummaryPerDay.forEach((k, v) -> System.out.println("For Date : " + k + ", Order Summary is : " + v));
		System.out.println("   End: report 1   ");

		System.out.println( "   Report 2 : Sales Leader Board: top 5 items in demand   ");

		Collection<InventoryBean> leaderBoardollection = inventoryBeanList.stream().sorted(comparing(InventoryBean::getSales, comparing(Math::abs)).reversed())
				.limit(5).collect(toList());

		leaderBoardollection.forEach(name -> {
			System.out.println("Item Name:"+name.getName()+" , Sales: "+name.getSales());
		});

		System.out.println( "   Report 3 : Summary of total items sold today     ");
		Collection<InventoryBean> itemCollectionToday =  inventoryBeanList.stream().filter(e-> LocalDate.of(e.OrderDate.getYear(), e.OrderDate.getMonth(), e.OrderDate.getDayOfMonth()).isEqual(LocalDate.now())).collect(Collectors.toList());
		itemCollectionToday.forEach(name -> {
			System.out.println("Item Id :"+name.getId()+" , Name: "+name.getName()+" , Price: "+name.getPrice()+" , Stock: "+name.getStock()+" , Sales: "+name.getSales()+ ", Order Date: "+name.getOrderDate());
		});

		System.out.println( "   Report 4 : Summary of total items sold per month   ");
		Map<LocalDate, List<InventoryBean>> soldPerMonth = inventoryBeanList.stream().collect(Collectors.groupingBy(d -> d.getOrderDate().withDayOfMonth(1)));
		System.out.println("Answer: Summary of total items sold per month: = "+soldPerMonth);

		System.out.println( "   Report 5 : Summary of Quantity of sale for one particular item   ");
		Map<String, IntSummaryStatistics> salesPerItem=
				inventoryBeanList.stream().collect(Collectors.groupingBy(InventoryBean::getName, Collectors.summarizingInt(InventoryBean::getSales)));
		salesPerItem.forEach((k, v) -> System.out.println("For Item : " + k + ", total sale is : " + v.getSum()));
		System.out.println("End: report 5");
	}
}
