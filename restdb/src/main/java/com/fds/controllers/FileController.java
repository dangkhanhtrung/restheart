package com.fds.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fds.payload.UploadFileResponse;
import com.fds.repository.coredb.DictCollection;
import com.fds.repository.coredb.DictCollectionRepository;
import com.fds.repository.coredb.DictItem;
import com.fds.repository.coredb.DictItemRepository;
import com.fds.repository.coredb.Role;
import com.fds.repository.coredb.RoleRepository;
import com.fds.repository.qlvtdb.AccessRole;
import com.fds.repository.qlvtdb.BenXe;
import com.fds.repository.qlvtdb.BenXeRepository;
import com.fds.repository.qlvtdb.BenXeSource;
import com.fds.repository.qlvtdb.CoreObject;
import com.fds.repository.qlvtdb.CoreObjectSource;
import com.fds.repository.qlvtdb.CuaKhau;
import com.fds.repository.qlvtdb.CuaKhauRepository;
import com.fds.repository.qlvtdb.DoanhNghiep;
import com.fds.repository.qlvtdb.DoanhNghiepRepository;
import com.fds.repository.qlvtdb.GioBen;
import com.fds.repository.qlvtdb.Not;
import com.fds.repository.qlvtdb.NotNgay;
import com.fds.repository.qlvtdb.NotNgaySource;
import com.fds.repository.qlvtdb.NotRepository;
import com.fds.repository.qlvtdb.PhuongTien;
import com.fds.repository.qlvtdb.PhuongTienRepository;
import com.fds.repository.qlvtdb.Tuyen;
import com.fds.repository.qlvtdb.TuyenRepository;
import com.fds.service.FileStorageService;

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
    
    @Autowired
    NotRepository notRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @PostMapping("/tuyen")
    public UploadFileResponse importTuyen(@RequestParam("file") MultipartFile file) {
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
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();
            iterator.next();
            
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                Tuyen tuyen = null;
                
                Integer sttQg = 0;
                String shortName = "";
                String loTrinhDi = "";
                
                Cell sttQgCell = currentRow.getCell(0);
                if (sttQgCell != null && sttQgCell.getCellTypeEnum() == CellType.NUMERIC) {
                	sttQg = (int)sttQgCell.getNumericCellValue();
                }
                
                System.out.println("STT QG: " + sttQg);
                Cell shortNameCell = currentRow.getCell(1);
                if (shortNameCell != null && shortNameCell.getCellTypeEnum() == CellType.STRING) {
                	shortName = shortNameCell.getStringCellValue();
                	tuyen = tuyenRepository.findByShortName(shortName);
                }
                
                if (tuyen == null) {
                	tuyen = new Tuyen();
                }
                tuyen.setSTT_QG(sttQg);
            	tuyen.setShortName(shortName);
                String title = "";
                
                String soDiText = "";
                Cell soDiCell = currentRow.getCell(2);
                if (soDiCell != null && soDiCell.getCellTypeEnum() == CellType.STRING) {
                	soDiText = soDiCell.getStringCellValue();
                	title = "Tuyến " + soDiText;
                }
                String soDenText = "";
                Cell soDenCell = currentRow.getCell(3);
                if (soDenCell != null && soDenCell.getCellTypeEnum() == CellType.STRING) {
                	soDenText = soDenCell.getStringCellValue();
                	title = title + " đi " + soDenText;
                }
                String benXeDiText = "";
                Cell benXeDiCell = currentRow.getCell(4);
                if (benXeDiCell != null && benXeDiCell.getCellTypeEnum() == CellType.STRING) {
                	benXeDiText = benXeDiCell.getStringCellValue();
                	title = title + " (bến đi: " + benXeDiText;
                }
                String benXeDenText = "";
                Cell benXeDenCell = currentRow.getCell(5);
                if (benXeDenCell != null && benXeDenCell.getCellTypeEnum() == CellType.STRING) {
                	benXeDenText = benXeDenCell.getStringCellValue();
                	title = title + " bến đến: " + benXeDenText + ")";
                }
                
                tuyen.setTitle(title);
                
                Cell loTrinhDiCell = currentRow.getCell(6);
                if (loTrinhDiCell != null && loTrinhDiCell.getCellTypeEnum() == CellType.STRING) {
                	loTrinhDi = loTrinhDiCell.getStringCellValue();
                	tuyen.setLotrinh_di(loTrinhDi);
                	tuyen.setLotrinh_den(loTrinhDi);
                	tuyen.setLotrinh_ve(loTrinhDi);
                }
                Integer cuLyTuyen = 0;
                Cell cuLyTuyenCell = currentRow.getCell(7);
                if (cuLyTuyenCell != null && cuLyTuyenCell.getCellTypeEnum() == CellType.NUMERIC) {
                	cuLyTuyen = (int)cuLyTuyenCell.getNumericCellValue();
                	tuyen.setCu_ly(cuLyTuyen);
                }
                Integer llQuyHoach = 0;
                Cell llQuyHoachCell = currentRow.getCell(8);
                if (llQuyHoachCell != null && llQuyHoachCell.getCellTypeEnum() == CellType.NUMERIC) {
                	llQuyHoach = (int)llQuyHoachCell.getNumericCellValue();
                	tuyen.setLl_quyhoach(llQuyHoach);
                }
                String phanLoaiTuyen = "";
                Cell phanLoaiTuyenCell = currentRow.getCell(9);
                if (phanLoaiTuyenCell != null && phanLoaiTuyenCell.getCellTypeEnum() == CellType.STRING) {
                	phanLoaiTuyen = phanLoaiTuyenCell.getStringCellValue();
                	
                }
                String ghiChu = "";
                Cell ghiChuCell = currentRow.getCell(10);
                if (ghiChuCell != null && ghiChuCell.getCellTypeEnum() == CellType.STRING) {
                	ghiChu = ghiChuCell.getStringCellValue();
                	tuyen.setGhichu(ghiChu);
                	tuyen.setDescription(ghiChu);
                }
                Integer trangThai = 0;
                Cell trangThaiCell = currentRow.getCell(11);
                if (trangThaiCell != null && trangThaiCell.getCellTypeEnum() == CellType.NUMERIC) {
                	trangThai = (int)trangThaiCell.getNumericCellValue();
                	tuyen.setTrangthai(trangThai);
                	DictItem statusItem = dictItemRepository.findByShortNameAndCollection(trangThai + "", "TRANGTHAI_TUYEN");
                	if (statusItem != null) {
                		CoreObjectSource statusSource = new CoreObjectSource();
                		CoreObject statusObj = new CoreObject();
                		statusObj.setShortName(statusItem.getShortName());
                		statusObj.setTitle(statusItem.getTitle());
                		statusSource.set_source(statusObj);
                		tuyen.setStatus(statusSource);
                	}
                }
                
                String[] tuyenArr = shortName.split("\\.");
                if (tuyenArr.length > 2) {
                	String soStr = tuyenArr[0];
                	String benXeStr = tuyenArr[1];
                	String maSoDi = soStr.substring(0, 2);
                	String maSoDen = soStr.substring(2, 4);
                	String maBenDi = benXeStr.substring(0, 2);
                	String maBenDen = benXeStr.substring(2, 4);
                	BenXe benXe = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
                	if (benXe != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXe.getShortName());
                		co.setTitle(benXe.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe(benXeSource);
                	}
                	BenXe benXeDi = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
                	if (benXeDi != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXeDi.getShortName());
                		co.setTitle(benXeDi.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe_di(benXeSource);                		
                	}
                	BenXe benXeDen = benXeRepository.findByMaBXAndSo(maBenDen, soDenText);
                	if (benXeDen != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXeDen.getShortName());
                		co.setTitle(benXeDen.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe_den(benXeSource);                		
                	}

                	AccessRole[] arr1 = new AccessRole[2];
                	arr1[0] = new AccessRole();
                	arr1[0].setShortName(maSoDi + "_EDIT");
                	arr1[0].setPermission("2");

                	arr1[1] = new AccessRole();
                	arr1[1].setShortName(maSoDen + "_EDIT");
                	arr1[1].setPermission("2");

                	tuyen.setAccessRoles(arr1);
                	
                	System.out.println("" + maSoDi + ", " + maSoDen + ", " + maBenDi + ", " + maBenDen);
                }

                tuyen.setSite("guest");
                tuyen.setStorage("regular");
                tuyen.setOpenAccess(1);
                tuyen.setCreatedAt((new Date()).getTime());
                tuyen.setModifiedAt((new Date()).getTime());

                
                if (tuyen.getShortName() != null && !"".contentEquals(tuyen.getShortName().trim())) {
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
            Calendar c = Calendar.getInstance();
            
            while (rowIterator.hasNext()) {
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
                Tuyen tuyen = tuyenRepository.findByShortName(maSoTuyenCell.getStringCellValue());
                if (tuyen != null) {
                	System.out.println("Tuyến đã có: " + tuyen.getLotrinh_di());
                }
                else {
                	tuyen = new Tuyen();
                	tuyen.setShortName(maSoTuyenCell.getStringCellValue());
                	tuyen.setLotrinh_di(loTrinhTuyenCell.getStringCellValue());
                	tuyen.setLotrinh_den(loTrinhTuyenCell.getStringCellValue());
                	tuyen.setCu_ly(Double.parseDouble(cuLyTuyenCell.getStringCellValue().split(" ")[0]));
                	tuyen.setLl_quyhoach(Integer.parseInt(tongSoChuyenCell.getStringCellValue().split(" ")[0]));
                	
                	tuyen = tuyenRepository.save(tuyen);
                }
                
                while (rowIterator.hasNext()) {
                	Row lastRow = rowIterator.next();
                	if ((lastRow.getRowNum() - startTableRow.getRowNum()) >= 5
                			&& prevRow.getRowNum() == lastRow.getRowNum() - 1) {
                		Cell sttCell = lastRow.getCell(0);
            			Cell gioDiCell = lastRow.getCell(1);
            			Cell gioDenCell = lastRow.getCell(2);
            			
//            			System.out.println("Gio di: " + sdf.format(gioDiCell.getDateCellValue()));
//            			System.out.println("Gio den: " + sdf.format(gioDenCell.getDateCellValue()));  
            			c.setTime(gioDiCell.getDateCellValue());
            			c.set(1970, 1, 1, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
//            			System.out.println("Gio di: " + c.getTimeInMillis());
            			Not not = new Not();
            			not.setGio_bendi(c.getTimeInMillis());
            			c.setTime(gioDenCell.getDateCellValue());
            			c.set(1970, 1, 1, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
            			not.setGio_benden(c.getTimeInMillis());
            			CoreObjectSource statusSource = new CoreObjectSource();
            			CoreObject statusNode = new CoreObject();
            			statusNode.setShortName("0");
            			statusNode.setTitle("Chưa duyệt");
            			
            			statusSource.set_source(statusNode);
            			not.setStatus(statusSource);
            			not.setType("qlvt_not");
            			CoreObjectSource tuyenSource = new CoreObjectSource();
            			CoreObject tuyenNode = new CoreObject();
            			tuyenNode.setShortName(maSoTuyenCell.getStringCellValue());
            			tuyenSource.set_source(tuyenNode);
            			
            			not.setTuyen(tuyenSource);
            			NotNgaySource[] notSources = new NotNgaySource[31];

                		for (int i = 1; i <= 31; i++) {
                			notSources[i - 1] = new NotNgaySource();
                			NotNgay notNgay = new NotNgay();
                			notNgay.setShortName(i + "");
                			String maSoTuyen = maSoTuyenCell.getStringCellValue();
                			Tuyen oldTuyen = tuyenRepository.findByShortName(maSoTuyen);
                			                			
                			String[] tuyenArr = maSoTuyenCell.getStringCellValue().split("\\.");
                            if (tuyenArr.length > 2) {
                            	String soStr = tuyenArr[0];
                            	String benXeStr = tuyenArr[1];
                            	String maSoDi = soStr.substring(0, 2);
                            	String maSoDen = soStr.substring(2, 4);
                            	String maBenDi = benXeStr.substring(0, 2);
                            	String maBenDen = benXeStr.substring(2, 4);
                            	if (oldTuyen == null) {
                            	                          		
                            	}
                            	else {
                            		GioBen gioBenDi = new GioBen();
                            		gioBenDi.setBen_xe(tuyen.getBen_xe_di().get_source().getShortName());
                            		notNgay.setGio_bendi(gioBenDi);
                            		
                            		GioBen gioBenDen = new GioBen();
                            		gioBenDen.setBen_xe(tuyen.getBen_xe_den().get_source().getShortName());
                            		notNgay.setGio_benden(gioBenDen);
                        			Cell mauNotDi = lastRow.getCell((i - 1) * 2 + 1);
                        			
                        			if (fileName.endsWith("xls")) {
                        				
                        			}
                        			else if (fileName.endsWith("xlsx")) {
                        				XSSFCell xssMauNotDiCell = (XSSFCell)mauNotDi;
                        				Color color = xssMauNotDiCell.getCellStyle().getFillForegroundColorColor();
                        				
                        			    if (color != null) {
                        			         if (color instanceof XSSFColor) {
                        			        	 System.out.println("Color: " + xssMauNotDiCell.getAddress() + ": " + ((XSSFColor)color).getARGBHex());
                        			        	 if ("FFFFFF00".contentEquals(((XSSFColor)color).getARGBHex())) {
                        			        		 gioBenDi.setStatus("0");
                        			        	 }
                        			        	 else if ("FF92D050".contentEquals(((XSSFColor)color).getARGBHex())) {
                        			        		 gioBenDi.setStatus("2");
                        			        	 }
                        			         } else if (color instanceof HSSFColor) {
                        			        	 if (! (color instanceof HSSFColor.AUTOMATIC))
                        			        		 System.out.println("Color: " + xssMauNotDiCell.getAddress() + ": " + ((HSSFColor)color).getHexString());
                        			         }
                        			    }    
                        			    else {
                        			    	gioBenDi.setStatus("1");
                        			    }
                        			}

                        			Cell mauNotDen = lastRow.getCell((i - 1) * 2 + 2);
                        			
                        			if (fileName.endsWith("xls")) {
                        				
                        			}
                        			else if (fileName.endsWith("xlsx")) {
                        				XSSFCell xssMauNotDenCell = (XSSFCell)mauNotDen;
                        				Color color = xssMauNotDenCell.getCellStyle().getFillForegroundColorColor();
                        				
                        			    if (color != null) {
                        			         if (color instanceof XSSFColor) {
                        			        	 System.out.println("Color: " + xssMauNotDenCell.getAddress() + ": " + ((XSSFColor)color).getARGBHex());
                        			        	 if ("FFFFFF00".contentEquals(((XSSFColor)color).getARGBHex())) {
                        			        		 gioBenDen.setStatus("0");
                        			        	 }
                        			        	 else if ("FF92D050".contentEquals(((XSSFColor)color).getARGBHex())) {
                        			        		 gioBenDen.setStatus("2");
                        			        	 }
                        			         } else if (color instanceof HSSFColor) {
                        			        	 if (! (color instanceof HSSFColor.AUTOMATIC))
                        			        		 System.out.println("Color: " + xssMauNotDenCell.getAddress() + ": " + ((HSSFColor)color).getHexString());
                        			         }
                        			    }    
                        			    else {
                        			    	gioBenDen.setStatus("1");
                        			    }
                        			}
                        			
                            	}
                            	
//                            	System.out.println("" + maSoDi + ", " + maSoDen + ", " + maBenDi + ", " + maBenDen);
                            }
                			
                			
                			notSources[i - 1].set_source(notNgay);
                			not.setNot_ngay(notSources);                			
                		}
                		
            			notRepository.save(not);
//                		System.out.println("STT: " + sttCell.getNumericCellValue());		
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
                    if ("shortName".contentEquals(key)) {
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, (int)valueCell.getNumericCellValue());
                        }                    	
                    }
                    else if ("tinh".equals(key)) {
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
                    else if ("loai_ben".equals(key)) {
                        ObjectNode loaiBenNode = mapper.createObjectNode();
                        loaiBenNode.put("shortName", (int)valueCell.getNumericCellValue());
                        ObjectNode loaiBenSourceNode = mapper.createObjectNode();
                        loaiBenSourceNode.put("_source", loaiBenNode);
                        
                        node.put("loai_ben", loaiBenSourceNode);
                    }
                    else if ("trangthai".contentEquals(key)) {
                    	if (valueCell.getStringCellValue().contentEquals("Đang hoạt động")) {
                    		node.put("trangthai", 1);
                    	}
                    	else {
                    		node.put("trangthai", 0);
                    	}
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
                List<DictItem> soGtvts = dictItemRepository.findByTitle(benXe.getSo_gtvt(), "SO_GTVT");
                benXe.setMa_bx(benXe.getShortName());
                String uniqueShortName = benXe.getShortName();
                if (soGtvts.size() > 0) {
                	uniqueShortName = uniqueShortName + "_" + soGtvts.get(0).getShortName();
                }
                benXe.setShortName(uniqueShortName);
                BenXe oldBenXe = benXeRepository.findByShortName(uniqueShortName);
                
                if (oldBenXe != null) {
                	benXe.setId(oldBenXe.getId());
                }
                else {
                }

                benXeRepository.save(benXe);
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
                        ObjectNode loaiTuyenSource = mapper.createObjectNode();
                        loaiTuyenSource.put("_source", loaiTuyenNode);
                        
                        node.put("loai_tuyen", loaiTuyenSource);
                    }
                    else if ("cua_khau".equals(key)) {
                    	ObjectNode cuaKhauSource = mapper.createObjectNode();
                    	cuaKhauSource.put("_source", cuaKhauNode);
                    	
                        node.put("cua_khau", cuaKhauSource);
                    }
                    else if ("ben_xe".equals(key)) {
                        benXeNode.put("shortName", valueCell.getStringCellValue());
                        ObjectNode benXeSourceNode = mapper.createObjectNode();
                        benXeSourceNode.put("_source", benXeNode);
                        
                        node.put("ben_xe", benXeSourceNode);
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
//                tuyenRepository.save(tuyen);
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
                        ObjectNode tinhSource = mapper.createObjectNode();
                        tinhSource.put("_source", mapper.createObjectNode());
                        
                        node.put("tinh", tinhSource);                        
                    }
                    else if ("quocgia_qt".equals(key)) {
                    	ObjectNode qgSource = mapper.createObjectNode();
                    	qgSource.put("_source", mapper.createObjectNode());
                    	
                        node.put("quocgia_qt", qgSource);
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
                    node.put("shortName", UUID.randomUUID().toString().replaceAll("-", "_"));
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
                DoanhNghiep oldDoanhNghiep = doanhNghiepRepository.findByMa_dn(doanhNghiep.getMa_dn());
                doanhNghiepRepository.save(doanhNghiep);
                if (oldDoanhNghiep != null) {
                	doanhNghiep.setId(oldDoanhNghiep.getId());
                }
                doanhNghiepRepository.save(doanhNghiep);
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
                        DictItem hieuXe = dictItemRepository.findByShortNameAndCollection(hieuXeCode, "HIEUXE");
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
                        DictItem nuocSx = dictItemRepository.findByShortNameAndCollection(nuocSxCode, "NATION");
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
                        DictItem mauSon = dictItemRepository.findByShortNameAndCollection(mauSonCode, "MAUSON");
                        if (mauSon != null) {
                        	mauSonNode.put("title", mauSon.getTitle());
                            node.put("mauson", mauSonSource);                        	
                        }
                    }
                    else if ("ma_dn".equals(key)) {
                        int ma_dn = (int)valueCell.getNumericCellValue();
                        System.out.println("Ma DN: " + ma_dn);
                        	
                        if (ma_dn != 0) {
                            DoanhNghiep dn = doanhNghiepRepository.findByMa_dn(ma_dn + "");
                            if (dn != null) {
                                ObjectNode dnSource = mapper.createObjectNode();
                                ObjectNode dnNode = mapper.createObjectNode();

                            	dnNode.put("shortName", dn.getShortName());
                            	dnNode.put("title", dn.getTitle());
                            		
                            	dnSource.put("_source", dnNode);
                            		
                            	node.put("doanh_nghiep", dnSource);
                            }                    		
                        }                  	                    		
                    }
                    else if ("tenloai_pt".equals(key)) {
                    	List<DictItem> listLoaiPts = dictItemRepository.findByTitle(valueCell.getStringCellValue(), "LOAIPT");
                    	if (listLoaiPts.size() > 0) {
                    		DictItem loaipt = listLoaiPts.get(0);
                            ObjectNode loaiptSource = mapper.createObjectNode();
                            ObjectNode loaiptNode = mapper.createObjectNode();

                        	loaiptNode.put("shortName", loaipt.getShortName());
                        	loaiptNode.put("title", loaipt.getTitle());
                        		
                        	loaiptSource.put("_source", loaiptNode);
                        		
                        	node.put("loai_pt", loaiptSource);
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
                    //node.put("doanh_nghiep", mapper.createObjectNode());
                    //node.put("loai_pt", mapper.createObjectNode());
                    node.put("coquan_ql", mapper.createObjectNode());
                }

                PhuongTien phuongTien = mapper.readValue(node.toString(), PhuongTien.class);
                PhuongTien oldPhuongTien = phuongTienRepository.findByShortName(phuongTien.getShortName());
                if (oldPhuongTien != null) {
                	phuongTien.setId(oldPhuongTien.getId());
                }
                phuongTienRepository.save(phuongTien);
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
  
    @PostMapping("/excel/not")
    public UploadFileResponse importExcelNot(@RequestParam("file") MultipartFile file) {
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
                    node.put("site", "guest");
                    node.put("storage", "regular");
                    node.put("accessRoles", mapper.createArrayNode());
                    node.put("accessUsers", mapper.createArrayNode());
                    node.put("accessEmails", mapper.createArrayNode());
                    node.put("openAccess", 1);
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
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
    
    @PostMapping("/role")
    public UploadFileResponse importRole(@RequestParam("file") MultipartFile file) {
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
                node.put("openAccess", 0);
                node.put("createdAt", (new Date().getTime()));
                node.put("modifiedAt", (new Date().getTime()));
                node.put("type", "role");
                
                Role role = mapper.readValue(node.toString(), Role.class);
                role.setDescription(role.getTitle());
                CoreObjectSource statusSource = new CoreObjectSource();
                CoreObject status = new CoreObject();
                status.setShortName("active");
                status.setTitle("Active");
                statusSource.set_source(status);
                role.setStatus(statusSource);
                
                Role oldRole = roleRepository.findByShortName(role.getShortName());
                if (oldRole != null) {
                	role.setId(oldRole.getId());
                }
                else {
                    role = mapper.readValue(node.toString(), Role.class);
                    role.setShortName(role.getShortName() + "_VIEW");
                    role.setDescription(role.getTitle());
                    status.setShortName("active");
                    status.setTitle("Active");
                    statusSource.set_source(status);
                    role.setStatus(statusSource);
                	roleRepository.save(role);
                	
                    role = mapper.readValue(node.toString(), Role.class);
                    role.setDescription(role.getTitle());
                    status.setShortName("active");
                    status.setTitle("Active");
                    statusSource.set_source(status);
                    role.setStatus(statusSource);
                    role.setShortName(role.getShortName() + "_EDIT");
                	roleRepository.save(role);
                	
                    role = mapper.readValue(node.toString(), Role.class);
                    role.setDescription(role.getTitle());
                    status.setShortName("active");
                    status.setTitle("Active");
                    statusSource.set_source(status);
                    role.setStatus(statusSource);
                    role.setShortName(role.getShortName() + "_DELETE");
                	roleRepository.save(role);                	                	
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
    
}
