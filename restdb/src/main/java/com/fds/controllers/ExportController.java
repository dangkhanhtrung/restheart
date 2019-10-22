package com.fds.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fds.repository.coredb.DictItem;
import com.fds.repository.coredb.DictItemRepository;
import com.fds.repository.qlvtdb.BenXe;
import com.fds.repository.qlvtdb.BenXeSource;
import com.fds.repository.qlvtdb.CoreObject;
import com.fds.repository.qlvtdb.Tuyen;
import com.fds.repository.qlvtdb.TuyenRepository;

@RestController
@RequestMapping(value = "/api/v1/export")
public class ExportController {
    @Autowired
    TuyenRepository tuyenRepository;

    @Autowired
    DictItemRepository dictItemRepository;
    
    @PostMapping(value = "/tuyen", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] exportTuyenExcel(@RequestParam("query") String query) {
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
        	tempCell.setCellValue(t.getSTT_QG());
        	tempCell = dataRow.createCell(1);
        	tempCell.setCellValue(t.getShortName());
            String[] tuyenArr = t.getShortName().split("\\.");
            
            if (tuyenArr.length > 2) {
            	String soStr = tuyenArr[0];
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
        	tempCell = dataRow.createCell(10);
        	tempCell.setCellValue(t.getGhichu());
        	if (t.getStatus() != null) {
        		tempCell = dataRow.createCell(11);
        		tempCell.setCellValue(t.getStatus().get_source().getShortName());
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
        return bytes;
    }
}
