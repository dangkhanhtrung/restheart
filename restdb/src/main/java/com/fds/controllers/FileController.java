package com.fds.controllers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fds.repository.coredb.DictCollection;
import com.fds.repository.coredb.DictCollectionRepository;
import com.fds.repository.coredb.DictItem;
import com.fds.repository.coredb.DictItemRepository;
import com.fds.repository.qlvtdb.*;
import com.fds.payload.UploadFileResponse;
import com.fds.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/import")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    TuyenRepository tuyenRepository;
    
    @Autowired
    CuaKhauRepository cuaKhauRepository;

    @Autowired
    DoanhNghiepRepository doanhNghiepRepository;

    @Autowired
    PhuongTienRepository phuongTienRepository;

    @Autowired
    BenXeRepository benXeRepository;

    @Autowired
    DictCollectionRepository dictCollectionRepository;
    
    @Autowired
    DictItemRepository dictItemRepository;
    
    @PostMapping("/tuyen")
    public UploadFileResponse importTuyen(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = new HSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                if (currentRow.getRowNum() <= 1) continue;
                double thuTuToanQuoc = 0;
                double thuTuTrongTinh = 0;
                String maSoTuyen = "";
                String tenTuyen = "";
                String tinhDi = "";
                String tinhDen = "";
                String bxDi = "";
                String bxDen = "";
                String loTrinhDi = "";
                String loTrinhDen = "";
                double cuLy = 0;
                double luuLuong = 0;
                String phanLoai = "";
                double llKhaiThac = 0;
                String gianCach = "";
                String ghiChu = "";
                
                if (CellType.NUMERIC == currentRow.getCell(0).getCellTypeEnum()) {
                    thuTuToanQuoc = currentRow.getCell(0).getNumericCellValue();
                    System.out.println("TQ: " + thuTuToanQuoc);                	
                }
                if (CellType.NUMERIC == currentRow.getCell(1).getCellTypeEnum()) {
                    thuTuTrongTinh = currentRow.getCell(1).getNumericCellValue();
                    System.out.println("TINH: " + thuTuTrongTinh);                	
                }
                if (CellType.STRING == currentRow.getCell(2).getCellTypeEnum()) {
                	maSoTuyen = currentRow.getCell(2).getStringCellValue();
                	System.out.println("Ma so tuyen: " + maSoTuyen);
                }
                if (CellType.STRING == currentRow.getCell(3).getCellTypeEnum()) {
                	tenTuyen = currentRow.getCell(3).getStringCellValue();
                	System.out.println("Ten tuyen: " + tenTuyen);
                }
                if (CellType.STRING == currentRow.getCell(4).getCellTypeEnum()) {
                	tinhDi = currentRow.getCell(4).getStringCellValue();
                	System.out.println("Tinh di: " + tinhDi);
                }
                if (CellType.STRING == currentRow.getCell(5).getCellTypeEnum()) {
                	tinhDen = currentRow.getCell(5).getStringCellValue();
                	System.out.println("Tinh den: " + tinhDen);
                }
                if (CellType.STRING == currentRow.getCell(6).getCellTypeEnum()) {
                	bxDi = currentRow.getCell(6).getStringCellValue();
                	System.out.println("Bx di: " + bxDi);
                }
                if (CellType.STRING == currentRow.getCell(7).getCellTypeEnum()) {
                	bxDen = currentRow.getCell(7).getStringCellValue();
                	System.out.println("Bx den: " + bxDen);
                }
                if (CellType.STRING == currentRow.getCell(8).getCellTypeEnum()) {
                	loTrinhDi = currentRow.getCell(8).getStringCellValue();
                	System.out.println("Lo trinh di: " + loTrinhDi);
                }
                if (CellType.STRING == currentRow.getCell(9).getCellTypeEnum()) {
                	loTrinhDen = currentRow.getCell(9).getStringCellValue();
                	System.out.println("Lo trinh den: " + loTrinhDen);
                }
                if (CellType.NUMERIC == currentRow.getCell(10).getCellTypeEnum()) {
                	cuLy = currentRow.getCell(10).getNumericCellValue();
                	System.out.println("Cu ly: " + cuLy);
                }
                if (CellType.NUMERIC == currentRow.getCell(11).getCellTypeEnum()) {
                	luuLuong = currentRow.getCell(11).getNumericCellValue();
                	System.out.println("Luu luong: " + luuLuong);
                }
                if (CellType.STRING == currentRow.getCell(12).getCellTypeEnum()) {
                	phanLoai = currentRow.getCell(12).getStringCellValue();
                	System.out.println("Phan loai: " + phanLoai);
                }
                if (CellType.NUMERIC == currentRow.getCell(13).getCellTypeEnum()) {
                	llKhaiThac = currentRow.getCell(13).getNumericCellValue();
                	System.out.println("Luu luong khai thac: " + llKhaiThac);
                }
                if (CellType.STRING == currentRow.getCell(14).getCellTypeEnum()) {
                	gianCach = currentRow.getCell(14).getStringCellValue();
                	System.out.println("Gian cach: " + gianCach);
                }
                if (CellType.STRING == currentRow.getCell(15).getCellTypeEnum()) {
                	ghiChu = currentRow.getCell(15).getStringCellValue();
                	System.out.println("Ghi chu: " + ghiChu);
                }
               
                Tuyen tuyen = new Tuyen();
                
                tuyen.setId(ObjectId.get().toString());
                tuyen.setStt_qg((int)thuTuToanQuoc);
                tuyen.setMaso_tuyen(maSoTuyen);
                tuyen.setCu_ly(cuLy);
                tuyen.setTen_tuyen(tenTuyen);
                tuyen.setGhichu(ghiChu);
                
        	    tuyenRepository.save(tuyen);
        	    
                System.out.println();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/benxe")
    public UploadFileResponse importBenXe(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = new HSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                if (currentRow.getRowNum() <= 1) continue;
                double thuTuToanQuoc = 0;
                double thuTuTrongTinh = 0;
                String maSoTuyen = "";
                String tenTuyen = "";
                String tinhDi = "";
                String tinhDen = "";
                String bxDi = "";
                String bxDen = "";
                String loTrinhDi = "";
                String loTrinhDen = "";
                double cuLy = 0;
                double luuLuong = 0;
                String phanLoai = "";
                double llKhaiThac = 0;
                String gianCach = "";
                String ghiChu = "";
                
                if (CellType.NUMERIC == currentRow.getCell(0).getCellTypeEnum()) {
                    thuTuToanQuoc = currentRow.getCell(0).getNumericCellValue();
                    System.out.println("TQ: " + thuTuToanQuoc);                	
                }
                if (CellType.NUMERIC == currentRow.getCell(1).getCellTypeEnum()) {
                    thuTuTrongTinh = currentRow.getCell(1).getNumericCellValue();
                    System.out.println("TINH: " + thuTuTrongTinh);                	
                }
                if (CellType.STRING == currentRow.getCell(2).getCellTypeEnum()) {
                	maSoTuyen = currentRow.getCell(2).getStringCellValue();
                	System.out.println("Ma so tuyen: " + maSoTuyen);
                }
                if (CellType.STRING == currentRow.getCell(3).getCellTypeEnum()) {
                	tenTuyen = currentRow.getCell(3).getStringCellValue();
                	System.out.println("Ten tuyen: " + tenTuyen);
                }
                if (CellType.STRING == currentRow.getCell(4).getCellTypeEnum()) {
                	tinhDi = currentRow.getCell(4).getStringCellValue();
                	System.out.println("Tinh di: " + tinhDi);
                }
                if (CellType.STRING == currentRow.getCell(5).getCellTypeEnum()) {
                	tinhDen = currentRow.getCell(5).getStringCellValue();
                	System.out.println("Tinh den: " + tinhDen);
                }
                if (CellType.STRING == currentRow.getCell(6).getCellTypeEnum()) {
                	bxDi = currentRow.getCell(6).getStringCellValue();
                	System.out.println("Bx di: " + bxDi);
                }
                if (CellType.STRING == currentRow.getCell(7).getCellTypeEnum()) {
                	bxDen = currentRow.getCell(7).getStringCellValue();
                	System.out.println("Bx den: " + bxDen);
                }
                if (CellType.STRING == currentRow.getCell(8).getCellTypeEnum()) {
                	loTrinhDi = currentRow.getCell(8).getStringCellValue();
                	System.out.println("Lo trinh di: " + loTrinhDi);
                }
                if (CellType.STRING == currentRow.getCell(9).getCellTypeEnum()) {
                	loTrinhDen = currentRow.getCell(9).getStringCellValue();
                	System.out.println("Lo trinh den: " + loTrinhDen);
                }
                if (CellType.NUMERIC == currentRow.getCell(10).getCellTypeEnum()) {
                	cuLy = currentRow.getCell(10).getNumericCellValue();
                	System.out.println("Cu ly: " + cuLy);
                }
                if (CellType.NUMERIC == currentRow.getCell(11).getCellTypeEnum()) {
                	luuLuong = currentRow.getCell(11).getNumericCellValue();
                	System.out.println("Luu luong: " + luuLuong);
                }
                if (CellType.STRING == currentRow.getCell(12).getCellTypeEnum()) {
                	phanLoai = currentRow.getCell(12).getStringCellValue();
                	System.out.println("Phan loai: " + phanLoai);
                }
                if (CellType.NUMERIC == currentRow.getCell(13).getCellTypeEnum()) {
                	llKhaiThac = currentRow.getCell(13).getNumericCellValue();
                	System.out.println("Luu luong khai thac: " + llKhaiThac);
                }
                if (CellType.STRING == currentRow.getCell(14).getCellTypeEnum()) {
                	gianCach = currentRow.getCell(14).getStringCellValue();
                	System.out.println("Gian cach: " + gianCach);
                }
                if (CellType.STRING == currentRow.getCell(15).getCellTypeEnum()) {
                	ghiChu = currentRow.getCell(15).getStringCellValue();
                	System.out.println("Ghi chu: " + ghiChu);
                }
               
                Tuyen tuyen = new Tuyen();
                
                tuyen.setId(ObjectId.get().toString());
                tuyen.setMaso_tuyen(maSoTuyen);
                tuyen.setCu_ly(cuLy);
                tuyen.setTen_tuyen(tenTuyen);
                tuyen.setGhichu(ghiChu);
                
        	    tuyenRepository.save(tuyen);
        	    
                System.out.println();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = new HSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                if (currentRow.getRowNum() <= 1) continue;
                double thuTuToanQuoc = 0;
                double thuTuTrongTinh = 0;
                String maSoTuyen = "";
                String tenTuyen = "";
                String tinhDi = "";
                String tinhDen = "";
                String bxDi = "";
                String bxDen = "";
                String loTrinhDi = "";
                String loTrinhDen = "";
                double cuLy = 0;
                double luuLuong = 0;
                String phanLoai = "";
                double llKhaiThac = 0;
                String gianCach = "";
                String ghiChu = "";
                
                if (CellType.NUMERIC == currentRow.getCell(0).getCellTypeEnum()) {
                    thuTuToanQuoc = currentRow.getCell(0).getNumericCellValue();
                    System.out.println("TQ: " + thuTuToanQuoc);                	
                }
                if (CellType.NUMERIC == currentRow.getCell(1).getCellTypeEnum()) {
                    thuTuTrongTinh = currentRow.getCell(1).getNumericCellValue();
                    System.out.println("TINH: " + thuTuTrongTinh);                	
                }
                if (CellType.STRING == currentRow.getCell(2).getCellTypeEnum()) {
                	maSoTuyen = currentRow.getCell(2).getStringCellValue();
                	System.out.println("Ma so tuyen: " + maSoTuyen);
                }
                if (CellType.STRING == currentRow.getCell(3).getCellTypeEnum()) {
                	tenTuyen = currentRow.getCell(3).getStringCellValue();
                	System.out.println("Ten tuyen: " + tenTuyen);
                }
                if (CellType.STRING == currentRow.getCell(4).getCellTypeEnum()) {
                	tinhDi = currentRow.getCell(4).getStringCellValue();
                	System.out.println("Tinh di: " + tinhDi);
                }
                if (CellType.STRING == currentRow.getCell(5).getCellTypeEnum()) {
                	tinhDen = currentRow.getCell(5).getStringCellValue();
                	System.out.println("Tinh den: " + tinhDen);
                }
                if (CellType.STRING == currentRow.getCell(6).getCellTypeEnum()) {
                	bxDi = currentRow.getCell(6).getStringCellValue();
                	System.out.println("Bx di: " + bxDi);
                }
                if (CellType.STRING == currentRow.getCell(7).getCellTypeEnum()) {
                	bxDen = currentRow.getCell(7).getStringCellValue();
                	System.out.println("Bx den: " + bxDen);
                }
                if (CellType.STRING == currentRow.getCell(8).getCellTypeEnum()) {
                	loTrinhDi = currentRow.getCell(8).getStringCellValue();
                	System.out.println("Lo trinh di: " + loTrinhDi);
                }
                if (CellType.STRING == currentRow.getCell(9).getCellTypeEnum()) {
                	loTrinhDen = currentRow.getCell(9).getStringCellValue();
                	System.out.println("Lo trinh den: " + loTrinhDen);
                }
                if (CellType.NUMERIC == currentRow.getCell(10).getCellTypeEnum()) {
                	cuLy = currentRow.getCell(10).getNumericCellValue();
                	System.out.println("Cu ly: " + cuLy);
                }
                if (CellType.NUMERIC == currentRow.getCell(11).getCellTypeEnum()) {
                	luuLuong = currentRow.getCell(11).getNumericCellValue();
                	System.out.println("Luu luong: " + luuLuong);
                }
                if (CellType.STRING == currentRow.getCell(12).getCellTypeEnum()) {
                	phanLoai = currentRow.getCell(12).getStringCellValue();
                	System.out.println("Phan loai: " + phanLoai);
                }
                if (CellType.NUMERIC == currentRow.getCell(13).getCellTypeEnum()) {
                	llKhaiThac = currentRow.getCell(13).getNumericCellValue();
                	System.out.println("Luu luong khai thac: " + llKhaiThac);
                }
                if (CellType.STRING == currentRow.getCell(14).getCellTypeEnum()) {
                	gianCach = currentRow.getCell(14).getStringCellValue();
                	System.out.println("Gian cach: " + gianCach);
                }
                if (CellType.STRING == currentRow.getCell(15).getCellTypeEnum()) {
                	ghiChu = currentRow.getCell(15).getStringCellValue();
                	System.out.println("Ghi chu: " + ghiChu);
                }
               
                Tuyen tuyen = new Tuyen();
                
                tuyen.setId(ObjectId.get().toString());
                tuyen.setMaso_tuyen(maSoTuyen);
                tuyen.setCu_ly(cuLy);
                tuyen.setTen_tuyen(tenTuyen);
                tuyen.setGhichu(ghiChu);
                
        	    tuyenRepository.save(tuyen);
        	    
                System.out.println();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/bieudo")
    @ResponseBody
    public UploadFileResponse importBieuDo(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            
            Iterator<Row> rowIterator = datatypeSheet.rowIterator();
            Row startRow = null;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            
            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                Cell checkCell = row.getCell(0);
//                System.out.println("Row: " + row.getRowNum());
//                try {
//                	System.out.println("Check cell: " + checkCell.getStringCellValue());
//                }
//                catch (Exception e) {
//                	System.out.println("Check cell not string: " + checkCell.getNumericCellValue());                	
//                }
//                if (checkBlankRow(row)) {
//                	System.out.println("Blank row: " + row.getRowNum());
//                }
                Row tenTuyenRow = startRow == null ? rowIterator.next() : startRow;
                Cell tenTuyenCell = tenTuyenRow.getCell(1);
                System.out.println("Ten tuyen: " + tenTuyenCell.getStringCellValue());
                Row benXeDiRow = rowIterator.next();
                Cell tenBenDi = benXeDiRow.getCell(1);
                System.out.println("Ten ben xe di: " + tenBenDi.getStringCellValue());
                
                Row benXeDenRow = rowIterator.next();
                Cell tenBenDen = benXeDenRow.getCell(1);
                System.out.println("Ten ben xe den: " + tenBenDen.getStringCellValue());
                
                Row benXeMaSoTuyenRow = rowIterator.next();
                Cell maSoTuyenCell = benXeMaSoTuyenRow.getCell(1);
                System.out.println("Ma so tuyen: " + maSoTuyenCell.getStringCellValue());            

                Row loTrinhTuyenRow = rowIterator.next();
                Cell loTrinhTuyenCell = loTrinhTuyenRow.getCell(1);
                System.out.println("Lo trinh tuyen: " + loTrinhTuyenCell.getStringCellValue());            

                Row cuLyTuyenRow = rowIterator.next();
                Cell cuLyTuyenCell = cuLyTuyenRow.getCell(1);
                System.out.println("Cu ly tuyen: " + cuLyTuyenCell.getStringCellValue());            
                
                Row tongSoChuyenRow = rowIterator.next();
                Cell tongSoChuyenCell = tongSoChuyenRow.getCell(1);
                System.out.println("Tong so chuyen: " + tongSoChuyenCell.getStringCellValue());            

                Row startTableRow = tongSoChuyenRow;
                		
                Row prevRow = rowIterator.next();
                while (rowIterator.hasNext()) {
                	Row lastRow = rowIterator.next();
                	if ((lastRow.getRowNum() - startTableRow.getRowNum()) >= 5
                			&& prevRow.getRowNum() == lastRow.getRowNum() - 1) {
                		Cell sttCell = lastRow.getCell(0);
                		for (int i = 1; i <= 31; i++) {
                			Cell gioDiCell = lastRow.getCell((i - 1) * 2 + 1);
                			Cell gioDenCell = lastRow.getCell((i - 1) * 2 + 2);
                			
                			System.out.println("Gio di: " + sdf.format(gioDiCell.getDateCellValue()));
                			System.out.println("Gio den: " + sdf.format(gioDenCell.getDateCellValue()));                			
                		}
                		System.out.println("STT: " + sttCell.getNumericCellValue());		
                	}
                	if (prevRow.getRowNum() != lastRow.getRowNum() - 1) {
                		startRow = lastRow;
                		break;
                	}
                	prevRow = lastRow;
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/cuakhaus")
    public UploadFileResponse importCuaKhaus(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
            
    private boolean checkBlankRow(Row row) {
    	Iterator<Cell> it = row.cellIterator();
    	while (it.hasNext()) {
    		Cell c = it.next();
    		if (c.getCellTypeEnum() != CellType.BLANK) {
    			if (c.getCellTypeEnum() == CellType.STRING) {
    				if (!"".contentEquals(c.getStringCellValue())) {
    					return false;
    				}
    			}
    			else if (c.getCellTypeEnum() == CellType.NUMERIC) {
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/old/benxe")
    public UploadFileResponse importOldBenXe(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row titleRow = datatypeSheet.getRow(0);

            // while (iterator.hasNext()) {

            // }

            Iterator<Cell> cellIterator = titleRow.iterator();
            while (cellIterator.hasNext()) {
                Cell tempCell = cellIterator.next();
                mapColumns.put(tempCell.getStringCellValue(), tempCell.getColumnIndex());
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);
                    if (valueCell == null) continue;
                    if ("tinh".equals(key)) {
                        ObjectNode tinhNode = mapper.createObjectNode();
                        tinhNode.put("shortName", valueCell.getStringCellValue());
                        node.put("tinh", tinhNode);
                    }
                    else if ("huyen".equals(key)) {
                        ObjectNode huyenNode = mapper.createObjectNode();
                        huyenNode.put("shortName", valueCell.getStringCellValue());
                        node.put("huyen", huyenNode);
                    }
                    else if ("xa".equals(key)) {
                        ObjectNode xaNode = mapper.createObjectNode();
                        xaNode.put("shortName", valueCell.getStringCellValue());
                        node.put("xa", xaNode);
                    }
                    else {
                        if (valueCell.getCellTypeEnum() == CellType.STRING) {
                            node.put(key, valueCell.getStringCellValue());
                        }
                        else if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, valueCell.getNumericCellValue());
                        }
                    }
                }

                node.put("site", "guest");
                node.put("storage", "regular");
                node.put("accessRoles", mapper.createArrayNode());
                node.put("accessUsers", mapper.createArrayNode());
                node.put("accessEmails", mapper.createArrayNode());
                node.put("openAccess", 1);
                node.put("createdAt", (new Date().getTime()));
                node.put("modifiedAt", (new Date().getTime()));
                node.put("coquan_ql", mapper.createObjectNode());

                BenXe benXe = mapper.readValue(node.toString(), BenXe.class);
                BenXe oldBenXe = benXeRepository.findByShortName(benXe.getShortName());
                if (oldBenXe != null) {
                }
                else {
                    benXeRepository.save(benXe);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/old/tuyen")
    public UploadFileResponse importOldTuyen(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row titleRow = datatypeSheet.getRow(0);

            // while (iterator.hasNext()) {

            // }

            Iterator<Cell> cellIterator = titleRow.iterator();
            while (cellIterator.hasNext()) {
                Cell tempCell = cellIterator.next();
                mapColumns.put(tempCell.getStringCellValue(), tempCell.getColumnIndex());
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);
                    ObjectNode loaiTuyenNode = mapper.createObjectNode();
                    ObjectNode cuaKhauNode = mapper.createObjectNode();
                    ObjectNode benXeNode = mapper.createObjectNode();

                    if (valueCell == null) continue;
                    if ("loai_tuyen".equals(key)) {
                        int loai_tuyen = (int)valueCell.getNumericCellValue();
                        if (loai_tuyen == 1) {
                            loaiTuyenNode.put("shortName", "2");
                            loaiTuyenNode.put("title", "Tuyến quy hoạch mới đã công bố");
                        }
                        node.put("loai_tuyen", loaiTuyenNode);
                    }
                    else if ("cua_khau".equals(key)) {
                        node.put("cua_khau", cuaKhauNode);
                    }
                    else if ("ben_xe".equals(key)) {
                        benXeNode.put("shortName", valueCell.getStringCellValue());
                        node.put("ben_xe", benXeNode);
                    }
                    else {
                        if (valueCell.getCellTypeEnum() == CellType.STRING) {
                            node.put(key, valueCell.getStringCellValue());
                        }
                        else if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, (int)valueCell.getNumericCellValue());
                        }    
                    }
                    node.put("site", "guest");
                    node.put("storage", "regular");
                    node.put("accessRoles", mapper.createArrayNode());
                    node.put("accessUsers", mapper.createArrayNode());
                    node.put("accessEmails", mapper.createArrayNode());
                    node.put("openAccess", 1);
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                }

                Tuyen tuyen = mapper.readValue(node.toString(), Tuyen.class);
                Tuyen oldTuyen = tuyenRepository.findByShortName(tuyen.getShortName());
                if (oldTuyen != null) {
                }
                else {
                    tuyenRepository.save(tuyen);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/old/cuakhau")
    public UploadFileResponse importOldCuaKhau(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row titleRow = datatypeSheet.getRow(0);

            // while (iterator.hasNext()) {

            // }

            Iterator<Cell> cellIterator = titleRow.iterator();
            while (cellIterator.hasNext()) {
                Cell tempCell = cellIterator.next();
                mapColumns.put(tempCell.getStringCellValue(), tempCell.getColumnIndex());
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);

                    if (valueCell == null) continue;
                    if ("tinh".equals(key)) {
                        node.put("tinh", mapper.createObjectNode());
                    }
                    else if ("quocgia_qt".equals(key)) {
                        node.put("quocgia_qt", mapper.createObjectNode());
                    }
                    else {
                        if (valueCell.getCellTypeEnum() == CellType.STRING) {
                            node.put(key, valueCell.getStringCellValue());
                        }
                        else if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, (int)valueCell.getNumericCellValue());
                        }        
                    }
                    node.put("site", "guest");
                    node.put("storage", "regular");
                    node.put("accessRoles", mapper.createArrayNode());
                    node.put("accessUsers", mapper.createArrayNode());
                    node.put("accessEmails", mapper.createArrayNode());
                    node.put("openAccess", 1);
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                }

                CuaKhau cuaKhau = mapper.readValue(node.toString(), CuaKhau.class);
                CuaKhau oldCuaKhau = cuaKhauRepository.findByShortName(cuaKhau.getShortName());
                if (oldCuaKhau != null) {
                }
                else {
                    cuaKhauRepository.save(cuaKhau);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/old/doanhnghiep")
    public UploadFileResponse importOldDoanhNghiep(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row titleRow = datatypeSheet.getRow(0);

            // while (iterator.hasNext()) {

            // }

            Iterator<Cell> cellIterator = titleRow.iterator();
            while (cellIterator.hasNext()) {
                Cell tempCell = cellIterator.next();
                mapColumns.put(tempCell.getStringCellValue(), tempCell.getColumnIndex());
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);

                    if (valueCell == null) continue;
                    if ("tinh".equals(key)) {
                        ObjectNode tinhNode = mapper.createObjectNode();
                        tinhNode.put("shortName", valueCell.getStringCellValue());
                        ObjectNode tinhSourceNode = mapper.createObjectNode();
                        tinhSourceNode.put("_source", tinhNode);
                        node.put("tinh", tinhSourceNode);
                    }
                    else if ("huyen".equals(key)) {
                        ObjectNode huyenNode = mapper.createObjectNode();
                        huyenNode.put("shortName", valueCell.getStringCellValue());
                        ObjectNode huyenSourceNode = mapper.createObjectNode();
                        huyenSourceNode.put("_source", huyenNode);
                        node.put("huyen", huyenSourceNode);
                    }
                    else if ("xa".equals(key)) {
                        ObjectNode xaNode = mapper.createObjectNode();
                        xaNode.put("shortName", valueCell.getStringCellValue());
                        ObjectNode xaSourceNode = mapper.createObjectNode();
                        xaSourceNode.put("_source", xaNode);
                        node.put("xa", xaSourceNode);
                    }

                    else if ("ngaycap_gcndkkd".equals(key)) {
                        try {
                            node.put("ngaycap_gcndkkd", sdf.parse(valueCell.getStringCellValue()).getTime());
                        }
                        catch (Exception e) {

                        }
                    }
                    else if ("ngayhh_gcndkkd".equals(key)) {
                        try {
                            node.put("ngayhh_gcndkkd", sdf.parse(valueCell.getStringCellValue()).getTime());
                        }
                        catch (Exception e) {

                        }
                    }
                    else {
                        if (valueCell.getCellTypeEnum() == CellType.STRING) {
                            node.put(key, valueCell.getStringCellValue());
                        }
                        else if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, (int)valueCell.getNumericCellValue());
                        }        
                    }
                    node.put("site", "guest");
                    node.put("storage", "regular");
                    node.put("accessRoles", mapper.createArrayNode());
                    node.put("accessUsers", mapper.createArrayNode());
                    node.put("accessEmails", mapper.createArrayNode());
                    node.put("openAccess", 1);
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                    node.put("loai_dn", mapper.createObjectNode());
                }

                DoanhNghiep doanhNghiep = mapper.readValue(node.toString(), DoanhNghiep.class);
//                DoanhNghiep oldDoanhNghiep = doanhNghiepRepository.findByShortName(doanhNghiep.getShortName());
                doanhNghiepRepository.save(doanhNghiep);
//                if (oldDoanhNghiep != null) {
//                }
//                else {
//                    doanhNghiepRepository.save(doanhNghiep);
//                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }    
    
    @PostMapping("/old/phuongtien")
    public UploadFileResponse importOldPhuongTien(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row titleRow = datatypeSheet.getRow(0);

            // while (iterator.hasNext()) {

            // }

            Iterator<Cell> cellIterator = titleRow.iterator();
            while (cellIterator.hasNext()) {
                Cell tempCell = cellIterator.next();
                mapColumns.put(tempCell.getStringCellValue(), tempCell.getColumnIndex());
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);

                    if (valueCell == null) continue;
                    if ("hieuxe".equals(key)) {
                        ObjectNode hieuXeSourceNode = mapper.createObjectNode();
                        ObjectNode hieuXeNode = mapper.createObjectNode();
                        hieuXeNode.put("shortName", valueCell.getStringCellValue());
                        String hieuXeCode = valueCell.getStringCellValue();
                        DictItem hieuXe = dictItemRepository.findByShortName(hieuXeCode);
                        if (hieuXe != null) {
                        	hieuXeNode.put("title", hieuXe.getTitle());
                            hieuXeSourceNode.put("_source", hieuXeNode);
                            node.put("hieuxe", hieuXeSourceNode);                        	
                        }
                    }
                    else if ("nuoc_sx".equals(key)) {
                    	ObjectNode nuocSXSource = mapper.createObjectNode();
                        ObjectNode nuocSXNode = mapper.createObjectNode();
                        nuocSXNode.put("shortName", valueCell.getStringCellValue());
                        String nuocSxCode = valueCell.getStringCellValue();
                        DictItem nuocSx = dictItemRepository.findByShortName(nuocSxCode);
                        if (nuocSx != null) {
                        	nuocSXNode.put("title", nuocSx.getTitle());
                        	nuocSXSource.put("_source", nuocSXNode);
                        	
                            node.put("nuoc_sx", nuocSXSource);                        	
                        }
                    }
                    else if ("mauson".equals(key)) {
                        ObjectNode mauSonNode = mapper.createObjectNode();
                        ObjectNode mauSonSource = mapper.createObjectNode();
                        
                        mauSonNode.put("shortName", valueCell.getStringCellValue());
                        String mauSonCode = valueCell.getStringCellValue();
                        DictItem mauSon = dictItemRepository.findByShortName(mauSonCode);
                        if (mauSon != null) {
                        	mauSonNode.put("title", mauSon.getTitle());
                            node.put("mauson", mauSonSource);                        	
                        }
                    }
                    else {
                        if (valueCell.getCellTypeEnum() == CellType.STRING) {
                            node.put(key, valueCell.getStringCellValue());
                        }
                        else if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, (int)valueCell.getNumericCellValue());
                        }        
                    }
                    node.put("site", "guest");
                    node.put("storage", "regular");
                    node.put("accessRoles", mapper.createArrayNode());
                    node.put("accessUsers", mapper.createArrayNode());
                    node.put("accessEmails", mapper.createArrayNode());
                    node.put("openAccess", 1);
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                    node.put("doanh_nghiep", mapper.createObjectNode());
                    node.put("loai_pt", mapper.createObjectNode());
                    node.put("coquan_ql", mapper.createObjectNode());
                }

                PhuongTien phuongTien = mapper.readValue(node.toString(), PhuongTien.class);
                PhuongTien oldPhuongTien = phuongTienRepository.findByShortName(phuongTien.getShortName());
                if (oldPhuongTien != null) {
                }
                else {
                    phuongTienRepository.save(phuongTien);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/old/danhmuc")
    public UploadFileResponse importOldDanhMuc(@RequestParam("file") MultipartFile file,
    		@RequestParam("collection") String collectionCode) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();

        try {
            FileInputStream excelFile = new FileInputStream(fileStorageService.loadFileAsResource(fileName).getFile());
            Workbook workbook = null;
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(excelFile);            	
            }
            else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row titleRow = datatypeSheet.getRow(0);

            // while (iterator.hasNext()) {

            // }

            Iterator<Cell> cellIterator = titleRow.iterator();
            while (cellIterator.hasNext()) {
                Cell tempCell = cellIterator.next();
                mapColumns.put(tempCell.getStringCellValue(), tempCell.getColumnIndex());
            }
            DictCollection collection = dictCollectionRepository.findByShortName(collectionCode);
            System.out.println("Import into: " + collection.getShortName());
            
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);

                    if (valueCell == null) continue;
                    if ("parent".contentEquals(key)) {
                    	String dictItemCode = valueCell.getStringCellValue();
                    	DictItem item = dictItemRepository.findByShortNameAndCollection(dictItemCode, collectionCode);
                    	if (item != null) {
                            ObjectNode dictItemSource = mapper.createObjectNode();
                            ObjectNode dictItemNode = mapper.createObjectNode();
                            dictItemNode.put("shortName", item.getShortName());
                            dictItemNode.put("title", item.getTitle());
                            dictItemSource.put("_source", dictItemNode);
                            
                            node.put("parent", dictItemSource);
                    	}
                    }
                    else {
                        if (valueCell.getCellTypeEnum() == CellType.STRING) {
                            node.put(key, valueCell.getStringCellValue());
                        }
                        else if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, (int)valueCell.getNumericCellValue());
                        }                                                	
                    }
                }
                node.put("site", "guest");
                node.put("storage", "regular");
                node.put("accessRoles", mapper.createArrayNode());
                node.put("accessUsers", mapper.createArrayNode());
                node.put("accessEmails", mapper.createArrayNode());
                node.put("openAccess", 0);
                node.put("createdAt", (new Date().getTime()));
                node.put("modifiedAt", (new Date().getTime()));
                node.put("type", "dict_item");
                ObjectNode dictCollectionSource = mapper.createObjectNode();
                ObjectNode dictCollectionNode = mapper.createObjectNode();
                dictCollectionNode.put("shortName", collection.getShortName());
                dictCollectionNode.put("title", collection.getTitle());
                dictCollectionSource.put("_source", dictCollectionNode);
                
                node.put("dictCollection", dictCollectionSource);
                
                DictItem dictItem = mapper.readValue(node.toString(), DictItem.class);                    	
                dictItemRepository.save(dictItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }       
    
}
