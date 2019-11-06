package com.fds.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fds.repository.coredb.DictItem;
import com.fds.repository.coredb.DictItemRepository;
import com.fds.repository.qlvtdb.Not;
import com.fds.repository.qlvtdb.NotRepository;
import com.fds.repository.qlvtdb.Tuyen;
import com.fds.repository.qlvtdb.TuyenRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.CellStyle;

@RestController
@RequestMapping(value = "/api/v1/export")
public class ExportController {
    @Autowired
    TuyenRepository tuyenRepository;

    @Autowired
    DictItemRepository dictItemRepository;
    
    @Autowired
    NotRepository notRepository;
    
    @PostMapping(value = "/tuyen", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<Resource> exportTuyenExcel(@RequestParam("query") String query) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Tuyến");
        Row titleRow = sheet.createRow(1);
        String[] titles = new String[] { "TT toàn quốc", "Mã tuyến", "Tỉnh nơi đi/đến (và ngược lại)", "Tỉnh nơi đi/đến (và ngược lại)", "BX nơi đi/đến (và ngược lại)",
        		"BX nơi đi/đến (và ngược lại)", "Hành trình chạy xe chính (dùng cho cả 2 chiều đi )", "Cự ly tuyến (km)", "Lưu lượng QH (xe xuất bến / tháng) 2015-2020",
        		"Phân loại tuyến QH", "Ghi chú", "Trạng thái" };
        for (int i = 0; i < titles.length; i++) {
            Cell tempCell = titleRow.createCell(i);
            tempCell.setCellValue(titles[i]);
        }
        Sort sort = new Sort(Sort.Direction.ASC, "STT_QG");
        
        List<Tuyen> lstTuyens = tuyenRepository.findByQuery(Document.parse(query), sort);
        int rowCount = 2;
        
        for (Tuyen t : lstTuyens) {
        	Row dataRow = sheet.createRow(rowCount++);
        	Cell tempCell = dataRow.createCell(0);
        	if (t.getSTT_QG() != null) {
            	tempCell.setCellValue(t.getSTT_QG());        		
        	}
        	tempCell = dataRow.createCell(1);
        	tempCell.setCellValue(t.getShortName());
            String[] tuyenArr = t.getShortName().split("\\.");
            
            if (tuyenArr.length > 2) {
            	String soStr = tuyenArr[0];
            	if (soStr.length() >= 4) {
                	String maSoDi = soStr.substring(0, 2);
                	String maSoDen = soStr.substring(2, 4);
                	DictItem tinhDi = dictItemRepository.findByShortNameAndCollection(maSoDi, "SO_GTVT");
                	
                	if (tinhDi != null) {
                		tempCell = dataRow.createCell(2);
                		tempCell.setCellValue(tinhDi.getTitle());
                	}
                	
                	DictItem tinhDen = dictItemRepository.findByShortNameAndCollection(maSoDen, "SO_GTVT");
                	
                	if (tinhDen != null) {
                		tempCell = dataRow.createCell(3);
                		tempCell.setCellValue(tinhDen.getTitle());
                	}            		
            	}
            }

        	tempCell = dataRow.createCell(4);
        	if (t.getBen_xe_di() != null) {
            	tempCell.setCellValue(t.getBen_xe_di().get_source().getTitle());        		
        	}
        	tempCell = dataRow.createCell(5);
        	if (t.getBen_xe_den() != null) {
            	tempCell.setCellValue(t.getBen_xe_den().get_source().getTitle());        		
        	}
        	tempCell = dataRow.createCell(6);
        	tempCell.setCellValue(t.getLotrinh_di());
        	tempCell = dataRow.createCell(7);
        	tempCell.setCellValue(t.getCu_ly());
        	tempCell = dataRow.createCell(8);
        	tempCell.setCellValue(t.getLl_quyhoach());
        	tempCell = dataRow.createCell(9);
        	if (t.getPl_quyhoach() != null && t.getPl_quyhoach().get_source() != null) {
            	tempCell.setCellValue(t.getPl_quyhoach().get_source().getTitle());        		
        	}
        	
        	tempCell = dataRow.createCell(10);
        	tempCell.setCellValue(t.getGhichu());
        	if (t.getStatus() != null) {
        		tempCell = dataRow.createCell(11);
        		tempCell.setCellValue(t.getStatus().get_source().getTitle());
        	}
        }
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = null;

        try {
            workbook.write(bos);
            bytes = bos.toByteArray();
            
            bos.close();
        } 
        catch (IOException e) {
        	
        }        
        try {
        	workbook.close();
        }
        catch (IOException e) {
        	
        }
        HttpHeaders headers = new HttpHeaders(); 
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tuyen_export.xlsx");
        
        ByteArrayResource resource = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .contentLength(bytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PostMapping(value = "/bieudo", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody ResponseEntity<Resource> exportBieuDoExcel(@RequestParam("maTuyen") String maTuyen) {
    	XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Biểu đồ");
        
        Tuyen tuyen = tuyenRepository.findByShortNameAndStorage(maTuyen, "regular");
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

        if (tuyen != null) {
            Row currentRow = sheet.createRow(0);
            currentRow.createCell(1).setCellValue("Tên tuyến:");
            currentRow.createCell(4).setCellValue(tuyen.getTitle());
            
            currentRow = sheet.createRow(1);
            currentRow.createCell(1).setCellValue("Bến xe nơi đi:");
            currentRow.createCell(4).setCellValue(tuyen.getBen_xe_di().get_source().getTitle());
            
            currentRow = sheet.createRow(2);
            currentRow.createCell(1).setCellValue("Bến xe nơi đến:");
            currentRow.createCell(4).setCellValue(tuyen.getBen_xe_den().get_source().getTitle());
            
            currentRow = sheet.createRow(3);
            currentRow.createCell(1).setCellValue("Mã số tuyến:");
            currentRow.createCell(4).setCellValue(tuyen.getShortName());
            
            currentRow = sheet.createRow(4);
            currentRow.createCell(1).setCellValue("Hành trình tuyến:");
            currentRow.createCell(4).setCellValue(tuyen.getLotrinh_di());
            
            currentRow = sheet.createRow(5);
            currentRow.createCell(1).setCellValue("Cự ly Tuyến:");
            currentRow.createCell(4).setCellValue(tuyen.getCu_ly());
            currentRow.createCell(5).setCellValue("km");
            
            currentRow = sheet.createRow(6);
            currentRow.createCell(1).setCellValue("Tổng số chuyến xe/tháng:");
            currentRow.createCell(4).setCellValue(tuyen.getLl_quyhoach());
            currentRow.createCell(5).setCellValue("chuyến/tháng");
            
            currentRow = sheet.createRow(7);
            currentRow.createCell(1).setCellValue("Ghi chú: Ô màu xanh là có doang nghiệp đang khai thác; Ô màu trắng: là chưa có DN, HTX khai thác; Ô màu vàng: là có DN, HTX đã đăng ký khai thác Sở đang kiểm tra hồ sơ");
            
            currentRow = sheet.createRow(8);
            Cell ttCell = currentRow.createCell(0);
            ttCell.setCellValue("TT");
            currentRow.createCell(1).setCellValue("Tên DN");
                        
            currentRow.createCell(2).setCellValue("Giờ xe xuất bến các ngày trong tháng");
            
            currentRow = sheet.createRow(9);
            for (int i = 1; i <= 15; i++) {
            	currentRow.createCell((i - 1)* 2 + 2).setCellValue("Ngày " + i);
            }
            currentRow = sheet.createRow(10);
            for (int i = 1; i <= 15; i++) {
            	currentRow.createCell((i - 1) * 2 + 2).setCellValue("Đi");
            	currentRow.createCell((i - 1) * 2 + 3).setCellValue("Đến");            	
            }
            
            XSSFCellStyle style = workbook.createCellStyle();
            
            List<Not> nots = notRepository.findByTuyen(maTuyen);
            for (int i = 1; i <= nots.size(); i++) {
            	currentRow = sheet.createRow(10 + i);

            	currentRow.createCell(0).setCellValue(i);
            		Not n = nots.get(i - 1);
            		for (int t = 1; t <= 15; t++) {
            			if (n.getGio_bendi() != 0) {
            				Date gioDi = new Date(n.getGio_bendi());
            				c.setTime(gioDi);
            				
                   		 	style = workbook.createCellStyle();
            				if (n.getNot_ngay()[t - 1].get_source().getGio_bendi().getStatus().contentEquals("1")) {
                       		 	style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            					
            				}
            				else if (n.getNot_ngay()[t - 1].get_source().getGio_bendi().getStatus().contentEquals("2")) {
            					XSSFColor myColor = new XSSFColor(new java.awt.Color (0, 255, 0)); // #f2dcdb
            					
                       		 	style.setFillForegroundColor(myColor);              					
            				}
            				else if (n.getNot_ngay()[t - 1].get_source().getGio_bendi().getStatus().contentEquals("3")) {
            					XSSFColor myColor = new XSSFColor(new java.awt.Color (146, 208, 80)); // #f2dcdb
            					
                       		 	style.setFillForegroundColor(myColor);            					
            				}
                   		 	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            				Cell cell = currentRow.createCell((t - 1) * 2 + 2);
                   		 	cell.setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));
                   		 	cell.setCellStyle(style);

//            				currentRow.createCell((t - 1) * 2 + 2).setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));
            			}
            			else {
            				currentRow.createCell((t - 1)* 2 + 2).setCellValue("");
            			}
            			if (n.getGio_benden() != 0) {
            				Date gioDen = new Date(n.getGio_benden());
            				c.setTime(gioDen);
            				if (n.getNot_ngay()[t - 1].get_source().getGio_benden().getStatus().contentEquals("1")) {
                       		 	style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            					
            				}
            				else if (n.getNot_ngay()[t - 1].get_source().getGio_benden().getStatus().contentEquals("2")) {
            					XSSFColor myColor = new XSSFColor(new java.awt.Color (0, 255, 0)); // #f2dcdb
            					
                       		 	style.setFillForegroundColor(myColor);            					
            				}
            				else if (n.getNot_ngay()[t - 1].get_source().getGio_benden().getStatus().contentEquals("3")) {
            					XSSFColor myColor = new XSSFColor(new java.awt.Color (146, 208, 80)); // #f2dcdb
            					
                       		 	style.setFillForegroundColor(myColor);            					
            				}
                   		 	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            				Cell cell = currentRow.createCell((t - 1) * 2 + 3);
                   		 	cell.setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));
                   		 	cell.setCellStyle(style);

//            				currentRow.createCell((t - 1) * 2 + 3).setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));            				
            			}
            			else {
            				currentRow.createCell((t - 1)* 2 + 3).setCellValue("");            				
            			}
            	}
            }
            
            int startRow = 10 + nots.size() + 2;
            currentRow = sheet.createRow(startRow);
            currentRow.createCell(0).setCellValue("TT");
            currentRow.createCell(1).setCellValue("Tên DN");         
            currentRow.createCell(2).setCellValue("Giờ xe xuất bến các ngày trong tháng");
            
            currentRow = sheet.createRow(startRow + 1);
            for (int i = 1; i <= 15; i++) {
            	currentRow.createCell((i - 1)* 2 + 2).setCellValue("Ngày " + (i + 15));
            }
            currentRow = sheet.createRow(startRow + 2);
            for (int i = 1; i <= 15; i++) {
            	currentRow.createCell((i - 1) * 2 + 2).setCellValue("Đi");
            	currentRow.createCell((i - 1) * 2 + 3).setCellValue("Đến");            	
            }
               
            for (int i = 1; i <= nots.size(); i++) {
            	currentRow = sheet.createRow(startRow + 2 + i);
            	currentRow.createCell(0).setCellValue(i);
            	Not n = nots.get(i - 1);
            	
        		for (int t = 1; t <= 15; t++) {
        			if (n.getGio_bendi() != 0) {
        				Date gioDi = new Date(n.getGio_bendi());
        				c.setTime(gioDi);
               		 	style = workbook.createCellStyle();
        				if (n.getNot_ngay()[t - 1 + 15].get_source().getGio_bendi().getStatus().contentEquals("1")) {
                   		 	style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        					
        				}
        				else if (n.getNot_ngay()[t - 1 + 15].get_source().getGio_bendi().getStatus().contentEquals("2")) {
        					XSSFColor myColor = new XSSFColor(new java.awt.Color (0, 255, 0)); // #f2dcdb
        					
                   		 	style.setFillForegroundColor(myColor);              					
        					
        				}
        				else if (n.getNot_ngay()[t - 1 + 15].get_source().getGio_bendi().getStatus().contentEquals("3")) {
        					XSSFColor myColor = new XSSFColor(new java.awt.Color (146, 208, 80)); // #f2dcdb
        					
                   		 	style.setFillForegroundColor(myColor);            					
        				}
               		 	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        				Cell cell = currentRow.createCell((t - 1) * 2 + 2);
               		 	cell.setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));
               		 	cell.setCellStyle(style);
 
//        				currentRow.createCell((t - 1) * 2 + 2).setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));
        			}
        			else {
        				currentRow.createCell((t - 1)* 2 + 2).setCellValue("");
        			}
        			if (n.getGio_benden() != 0) {
        				Date gioDen = new Date(n.getGio_benden());
        				c.setTime(gioDen);
        				if (n.getNot_ngay()[t - 1 + 15].get_source().getGio_benden().getStatus().contentEquals("1")) {
                   		 	style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        					
        				}
        				else if (n.getNot_ngay()[t - 1 + 15].get_source().getGio_benden().getStatus().contentEquals("2")) {
        					XSSFColor myColor = new XSSFColor(new java.awt.Color (0, 255, 0)); // #f2dcdb
        					
                   		 	style.setFillForegroundColor(myColor);              					
        					
        				}
        				else if (n.getNot_ngay()[t - 1 + 15].get_source().getGio_benden().getStatus().contentEquals("3")) {
        					XSSFColor myColor = new XSSFColor(new java.awt.Color (146, 208, 80)); // #f2dcdb
        					
                   		 	style.setFillForegroundColor(myColor);            					
        				}
               		 	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        				Cell cell = currentRow.createCell((t - 1) * 2 + 3);
               		 	cell.setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));
               		 	cell.setCellStyle(style);
        				
//        				currentRow.createCell((t - 1) * 2 + 3).setCellValue(c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE));            				
        			}
        			else {
        				currentRow.createCell((t - 1)* 2 + 3).setCellValue("");            				
        			}
        		}
            }            
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = null;

        try {
            workbook.write(bos);
            bytes = bos.toByteArray();
            
            bos.close();
        } 
        catch (IOException e) {
        	
        }        
        try {
        	workbook.close();
        }
        catch (IOException e) {
        	
        }
        HttpHeaders headers = new HttpHeaders(); 
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tuyen_export.xlsx");
        
        ByteArrayResource resource = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .contentLength(bytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    
    @PostMapping(value = "/test", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody byte[] exportTestExcel() {
    	Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Biểu đồ");
		 // Create a row and put some cells in it. Rows are 0 based.
		 Row row = sheet.createRow(1);
		 CellStyle style = workbook.createCellStyle();
		 style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		 style.setFillPattern(FillPatternType.BIG_SPOTS);
		 Cell cell = row.createCell(1);
		 cell.setCellValue("X");
		 cell.setCellStyle(style);
		 style = workbook.createCellStyle();
		 style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		 cell = row.createCell(2);
		 cell.setCellValue("X");
		 cell.setCellStyle(style);
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = null;

        try {
            workbook.write(bos);
            bytes = bos.toByteArray();
            
            bos.close();
        } 
        catch (IOException e) {
        	
        }        
        try {
        	workbook.close();
        }
        catch (IOException e) {
        	
        }
        return bytes;
    }
}
