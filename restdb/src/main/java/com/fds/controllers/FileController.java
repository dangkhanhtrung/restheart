package com.fds.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
import com.fds.repository.coredb.DictItemSource;
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
import com.fds.repository.qlvtdb.DoanhNghiepObject;
import com.fds.repository.qlvtdb.DoanhNghiepRepository;
import com.fds.repository.qlvtdb.DoanhNghiepSource;
import com.fds.repository.qlvtdb.GiayPhepDKKDVT;
import com.fds.repository.qlvtdb.GiayPhepDKKDVTRepository;
import com.fds.repository.qlvtdb.GioBen;
import com.fds.repository.qlvtdb.LoaiTuyenSource;
import com.fds.repository.qlvtdb.Not;
import com.fds.repository.qlvtdb.NotNgay;
import com.fds.repository.qlvtdb.NotNgaySource;
import com.fds.repository.qlvtdb.NotRepository;
import com.fds.repository.qlvtdb.PhuHieuBienHieu;
import com.fds.repository.qlvtdb.PhuHieuBienHieuRepository;
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
    
    @Autowired
    GiayPhepDKKDVTRepository giayPhepDKKDVTRepository;
    
    @Autowired
    PhuHieuBienHieuRepository phuHieuBienHieuRepository;
    
    @PostMapping("/tuyen")
    public UploadFileResponse importTuyen(@RequestParam(name="file", required=true) MultipartFile file,
    		@RequestParam(name="reviewCode", required=false) String reviewCode) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String message = "";
        
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
            
            Map<String, List<Integer>> checkTuyens = new HashMap<String, List<Integer>>();
            
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                String shortName = "";
                Cell shortNameCell = currentRow.getCell(1);
                if (shortNameCell != null && shortNameCell.getCellTypeEnum() == CellType.STRING) {
                	shortName = shortNameCell.getStringCellValue();
                }
                if (!checkTuyens.containsKey(shortName)) {
                	List<Integer> rowList = new ArrayList<Integer>();
                	rowList.add(currentRow.getRowNum());
                	checkTuyens.put(shortName, rowList);
                }
                else {
                	checkTuyens.get(shortName).add(currentRow.getRowNum());
                }
            }
  
            iterator = datatypeSheet.iterator();
            iterator.next();
            iterator.next();
            
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                Tuyen tuyen = null;
                System.out.println("Tuyen process row num: " + currentRow.getRowNum());
                Integer sttQg = 0;
                String shortName = "";
                String loTrinhDi = "";
                List<CoreObjectSource> lstReviews = new ArrayList<CoreObjectSource>();
                if (reviewCode != null && !"".contentEquals(reviewCode)) {
                	DictItem reviewItem = dictItemRepository.findByShortNameAndCollection(reviewCode, "TRANGTHAI_RASOAT");
                	if (reviewItem != null) {
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName(reviewItem.getShortName());
                    	reviewObject.setTitle(reviewItem.getTitle());
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);                		
                	}
                }
                Cell sttQgCell = currentRow.getCell(0);
                if (sttQgCell != null && sttQgCell.getCellTypeEnum() == CellType.NUMERIC) {
                	sttQg = (int)sttQgCell.getNumericCellValue();
                }
                
                System.out.println("STT QG: " + sttQg);
                Cell shortNameCell = currentRow.getCell(1);
                if (shortNameCell != null && shortNameCell.getCellTypeEnum() == CellType.STRING) {
                	shortName = shortNameCell.getStringCellValue().trim();
//                	tuyen = tuyenRepository.findByShortName(shortName);
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
                else {
                	tuyen.setStorage("review");
                	tuyen.setGhichu("Dữ liệu cự ly sai định dạng");
                	tuyen.setCu_ly(cuLyTuyen);
                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                	CoreObject reviewObject = new CoreObject();
                	reviewObject.setShortName("3");
                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                	reviewStatusSource.set_source(reviewObject);
                	lstReviews.add(reviewStatusSource);
                }
                Integer llQuyHoach = 0;
                Cell llQuyHoachCell = currentRow.getCell(8);
                if (llQuyHoachCell != null && llQuyHoachCell.getCellTypeEnum() == CellType.NUMERIC) {
                	llQuyHoach = (int)llQuyHoachCell.getNumericCellValue();
                	tuyen.setLl_quyhoach(llQuyHoach);
                }
                tuyen.setLl_quyhoach(llQuyHoach);
                
                String phanLoaiTuyen = "";
                Cell phanLoaiTuyenCell = currentRow.getCell(9);
                if (phanLoaiTuyenCell != null && phanLoaiTuyenCell.getCellTypeEnum() == CellType.STRING) {
                	phanLoaiTuyen = phanLoaiTuyenCell.getStringCellValue();
            		CoreObjectSource plQhSource = new CoreObjectSource();
            		CoreObject plQhNode = new CoreObject();
            		List<DictItem> lstPls = dictItemRepository.findByTitle(phanLoaiTuyen.trim(), "PL_TUYEN");
            		if (lstPls.size() > 0) {
            			DictItem phanLoaiItem = lstPls.get(0);
                    	plQhNode.setShortName(phanLoaiItem.getShortName());
                    	plQhNode.setTitle(phanLoaiItem.getTitle());
                    	plQhSource.set_source(plQhNode);
                    	tuyen.setPl_quyhoach(plQhSource);
            		}
                }
                String ghiChu = "";
                Cell ghiChuCell = currentRow.getCell(10);
                if (ghiChuCell != null && ghiChuCell.getCellTypeEnum() == CellType.STRING) {
                	ghiChu = ghiChuCell.getStringCellValue();
                	tuyen.setGhichu(ghiChu);
                	tuyen.setDescription(ghiChu);
                }
                Cell trangThaiCell = currentRow.getCell(11);
                if (trangThaiCell != null && trangThaiCell.getCellTypeEnum() == CellType.STRING) {              	
                	String trangThai = trangThaiCell.getStringCellValue();
                	List<DictItem> lstSts = dictItemRepository.findByTitle(trangThai.trim(), "TRANGTHAI_TUYEN");
                	if (lstSts.size() > 0) {
                    	DictItem statusItem = lstSts.get(0);
                    	if (statusItem != null) {
                    		CoreObjectSource statusSource = new CoreObjectSource();
                    		CoreObject statusObj = new CoreObject();
                    		statusObj.setShortName(statusItem.getShortName());
                    		statusObj.setTitle(statusItem.getTitle());
                    		statusSource.set_source(statusObj);
                    		tuyen.setStatus(statusSource);
                    	}                		
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
                	if (Integer.parseInt(maSoDi) > Integer.parseInt(maSoDen)) {
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	tuyen.setGhichu("Mã tuyến bị viết ngược");
                    	lstReviews.add(reviewStatusSource);
                	}
//                	BenXe benXe = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
                	BenXe benXe = benXeRepository.findByShortNameAndStorage(maBenDi + "_" + maSoDi, "regular");
                	if (benXe != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXe.getShortName());
                		co.setTitle(benXe.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe(benXeSource);
                	}
                	else {
                		tuyen.setGhichu("Không có thông tin bến đi");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);
                	}
//                	BenXe benXeDi = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
                	BenXe benXeDi = benXeRepository.findByShortNameAndStorage(maSoDi + "_" + maSoDi, "regular");
                	if (benXeDi != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXeDi.getShortName());
                		co.setTitle(benXeDi.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe_di(benXeSource);                		
                	}
                	else {
                		tuyen.setGhichu("Không có thông tin bến đi");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);               		
                	}
//                	BenXe benXeDen = benXeRepository.findByMaBXAndSo(maBenDen, soDenText);
                	BenXe benXeDen = benXeRepository.findByShortNameAndStorage(maBenDen + "_" + maSoDen, "regular");
                	if (benXeDen != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXeDen.getShortName());
                		co.setTitle(benXeDen.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe_den(benXeSource);                		
                	}
                	else {
                		tuyen.setGhichu("Không có thông tin bến đến");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);                		
                	}

                	DictItem soDiItem = dictItemRepository.findByShortNameAndCollection(maSoDi, "GOVERNMENT");
            		CoreObjectSource soDiSource = new CoreObjectSource();
            		CoreObjectSource soDenSource = new CoreObjectSource();
            		CoreObjectSource[] coquan_ql = new CoreObjectSource[2];
                	if (soDiItem != null) {
                		CoreObject soDiObject = new CoreObject();
                		soDiObject.setShortName(soDiItem.getShortName());
                		soDiObject.setTitle(soDiItem.getTitle());
                		soDiSource.set_source(soDiObject);
                		tuyen.setNoi_di(soDiSource);
                		coquan_ql[0] = soDiSource;
                	}
                	DictItem soDenItem = dictItemRepository.findByShortNameAndCollection(maSoDen, "GOVERNMENT");
                	if (soDenItem != null) {
                		CoreObject soDenObject = new CoreObject();
                		soDenObject.setShortName(soDenItem.getShortName());
                		soDenObject.setTitle(soDenItem.getTitle());
                		soDenSource.set_source(soDenObject);
                		tuyen.setNoi_den(soDenSource);
                		coquan_ql[1] = soDenSource;
                	}
                	tuyen.setCoquan_ql(coquan_ql);

                	AccessRole[] arr1 = new AccessRole[3];
                	arr1[0] = new AccessRole();
                	arr1[0].setShortName(maSoDi + "_EDIT");
                	Role soDiRole = roleRepository.findByShortName(maSoDi + "_EDIT");
                	if (soDiRole != null) {
                		arr1[0].setTitle(soDiRole.getTitle());
                	}
                	arr1[0].setPermission("2");

                	arr1[1] = new AccessRole();
                	arr1[1].setShortName(maSoDen + "_EDIT");
                	Role soDenRole = roleRepository.findByShortName(maSoDen + "_EDIT");
                	if (soDenRole != null) {
                		arr1[1].setTitle(soDenRole.getTitle());
                	}
                	arr1[1].setPermission("2");

                	arr1[2] = new AccessRole();
                	arr1[2].setShortName("TCDB");
                	Role tcdbRole = roleRepository.findByShortName("TCDB");
                	if (tcdbRole != null) {
                		arr1[2].setTitle(tcdbRole.getTitle());
                	}                    	
                	arr1[2].setPermission("1");
                	
                	CoreObject loaiTuyenNode = new CoreObject();
            		LoaiTuyenSource loaiTuyen = new LoaiTuyenSource();
                	
                	if (maSoDi.contentEquals(maSoDen)) {                		
                		loaiTuyenNode.setShortName("1");
                		loaiTuyenNode.setTitle("Nội tỉnh");
                		loaiTuyen.set_source(loaiTuyenNode);
                		tuyen.setLoai_tuyen(loaiTuyen);
                	}
                	else {
                		loaiTuyenNode.setShortName("2");
                		loaiTuyenNode.setTitle("Liên tỉnh");
                		loaiTuyen.set_source(loaiTuyenNode);
                		tuyen.setLoai_tuyen(loaiTuyen);                		
                	}
//                	System.out.println("" + maSoDi + ", " + maSoDen + ", " + maBenDi + ", " + maBenDen);
                }
                else {
                	tuyen.setGhichu("Mã tuyến đánh sai quy tắc");
                	tuyen.setStorage("review");
                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                	CoreObject reviewObject = new CoreObject();
                	reviewObject.setShortName("3");
                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                	reviewStatusSource.set_source(reviewObject);
                	lstReviews.add(reviewStatusSource);                	
                }

                if (checkTuyens.containsKey(shortName)) {
                	if (checkTuyens.get(shortName).size() > 1) {
                		tuyen.setGhichu("Mã tuyến trùng nhau");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("4");
                    	reviewObject.setTitle("Tuyến trùng mã");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);               		
                	}
                }
                tuyen.setSite("guest");
                if (tuyen.getStorage() == null) {
                    tuyen.setStorage("regular");                	
                }
                tuyen.setOpenAccess("1");
                tuyen.setCreatedAt((new Date()).getTime());
                tuyen.setModifiedAt((new Date()).getTime());

                if (!checkTuyens.containsKey(shortName)
                		|| checkTuyens.get(shortName).size() > 1) {
                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                	CoreObject reviewObject = new CoreObject();
                	reviewObject.setShortName("4");
                	reviewObject.setTitle("Tuyến trùng mã");
                	reviewStatusSource.set_source(reviewObject);
                	lstReviews.add(reviewStatusSource);
                }
                else {
                	List<Tuyen> listTuyens = tuyenRepository.findByShortName(tuyen.getShortName());
                	if (listTuyens.size() > 1) {
                		tuyen.setStorage("review");
                	}
                	else {
                        Tuyen oldTuyen = tuyenRepository.findByShortNameAndStorage(tuyen.getShortName(), "regular");
                        if (oldTuyen != null && ! "review".contentEquals(oldTuyen.getStorage())) {
                        	tuyen.setId(oldTuyen.getId());
//                        	System.out.println("Duplicate tuyen");
                        }                	                	                		
                	}
                }
                CoreObjectSource[] reviewStatus = new CoreObjectSource[lstReviews.size()];
                lstReviews.toArray(reviewStatus);
                
                tuyen.setReview_status(reviewStatus);
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
                file.getContentType(), file.getSize(), message);
    }

    @PostMapping("/dev/tuyen")
    public UploadFileResponse importDevTuyen(@RequestParam("file") MultipartFile file,
    		@RequestParam(name="reviewCode", required=false) String reviewCode,
    		@RequestParam(name="mode", required=false) String mode) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String message = "";
        
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
            
            Map<String, List<Integer>> checkTuyens = new HashMap<String, List<Integer>>();
            
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                String shortName = "";
                Cell shortNameCell = currentRow.getCell(1);
                if (shortNameCell != null && shortNameCell.getCellTypeEnum() == CellType.STRING) {
                	shortName = shortNameCell.getStringCellValue();
                }
                if (!checkTuyens.containsKey(shortName)) {
                	List<Integer> rowList = new ArrayList<Integer>();
                	rowList.add(currentRow.getRowNum());
                	checkTuyens.put(shortName, rowList);
                }
                else {
                	checkTuyens.get(shortName).add(currentRow.getRowNum());
                }
            }
  
            iterator = datatypeSheet.iterator();
            iterator.next();
            iterator.next();
            
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                Tuyen tuyen = null;
                System.out.println("Tuyen process row num: " + currentRow.getRowNum());
                Integer sttQg = 0;
                String shortName = "";
                String loTrinhDi = "";
                List<CoreObjectSource> lstReviews = new ArrayList<CoreObjectSource>();
                if (reviewCode != null && !"".contentEquals(reviewCode)) {
                	DictItem reviewItem = dictItemRepository.findByShortNameAndCollection(reviewCode, "TRANGTHAI_RASOAT");
                	if (reviewItem != null) {
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName(reviewItem.getShortName());
                    	reviewObject.setTitle(reviewItem.getTitle());
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);                		
                	}
                }
                
                Cell sttQgCell = currentRow.getCell(0);
                if (sttQgCell != null && sttQgCell.getCellTypeEnum() == CellType.NUMERIC) {
                	sttQg = (int)sttQgCell.getNumericCellValue();
                }
                
                System.out.println("STT QG: " + sttQg);
                Cell shortNameCell = currentRow.getCell(1);
                if (shortNameCell != null && shortNameCell.getCellTypeEnum() == CellType.STRING) {
                	shortName = shortNameCell.getStringCellValue().trim();
//                	tuyen = tuyenRepository.findByShortName(shortName);
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
                else {
                	tuyen.setStorage("review");
                	tuyen.setGhichu("Dữ liệu cự ly sai định dạng");
                	tuyen.setCu_ly(cuLyTuyen);
                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                	CoreObject reviewObject = new CoreObject();
                	reviewObject.setShortName("3");
                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                	reviewStatusSource.set_source(reviewObject);
                	lstReviews.add(reviewStatusSource);
                }
                Integer llQuyHoach = 0;
                Cell llQuyHoachCell = currentRow.getCell(8);
                if (llQuyHoachCell != null && llQuyHoachCell.getCellTypeEnum() == CellType.NUMERIC) {
                	llQuyHoach = (int)llQuyHoachCell.getNumericCellValue();
                	tuyen.setLl_quyhoach(llQuyHoach);
                }
                tuyen.setLl_quyhoach(llQuyHoach);
                
                String phanLoaiTuyen = "";
                Cell phanLoaiTuyenCell = currentRow.getCell(9);
                if (phanLoaiTuyenCell != null && phanLoaiTuyenCell.getCellTypeEnum() == CellType.STRING) {
                	phanLoaiTuyen = phanLoaiTuyenCell.getStringCellValue();
            		CoreObjectSource plQhSource = new CoreObjectSource();
            		CoreObject plQhNode = new CoreObject();
            		List<DictItem> lstPls = dictItemRepository.findByTitle(phanLoaiTuyen.trim(), "PL_TUYEN");
            		if (lstPls.size() > 0) {
            			DictItem phanLoaiItem = lstPls.get(0);
                    	plQhNode.setShortName(phanLoaiItem.getShortName());
                    	plQhNode.setTitle(phanLoaiItem.getTitle());
                    	plQhSource.set_source(plQhNode);
                    	tuyen.setPl_quyhoach(plQhSource);
            		}
                }
                String ghiChu = "";
                Cell ghiChuCell = currentRow.getCell(10);
                if (ghiChuCell != null && ghiChuCell.getCellTypeEnum() == CellType.STRING) {
                	ghiChu = ghiChuCell.getStringCellValue();
                	tuyen.setGhichu(ghiChu);
                	tuyen.setDescription(ghiChu);
                }
                Cell trangThaiCell = currentRow.getCell(11);
                if (trangThaiCell != null && trangThaiCell.getCellTypeEnum() == CellType.STRING) {              	
                	String trangThai = trangThaiCell.getStringCellValue();
                	List<DictItem> lstSts = dictItemRepository.findByTitle(trangThai.trim(), "TRANGTHAI_TUYEN");
                	if (lstSts.size() > 0) {
                    	DictItem statusItem = lstSts.get(0);
                    	if (statusItem != null) {
                    		CoreObjectSource statusSource = new CoreObjectSource();
                    		CoreObject statusObj = new CoreObject();
                    		statusObj.setShortName(statusItem.getShortName());
                    		statusObj.setTitle(statusItem.getTitle());
                    		statusSource.set_source(statusObj);
                    		tuyen.setStatus(statusSource);
                    	}                		
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
                	if (Integer.parseInt(maSoDi) > Integer.parseInt(maSoDen)) {
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	tuyen.setGhichu("Mã tuyến bị viết ngược");
                    	lstReviews.add(reviewStatusSource);
                	}
//                	BenXe benXe = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
                	BenXe benXe = benXeRepository.findByShortNameAndStorage(maBenDi + "_" + maSoDi, "regular");
                	if (benXe != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXe.getShortName());
                		co.setTitle(benXe.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe(benXeSource);
                	}
                	else {
                		tuyen.setGhichu("Không có thông tin bến đi");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);
                	}
//                	BenXe benXeDi = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
                	BenXe benXeDi = benXeRepository.findByShortNameAndStorage(maBenDi + "_" + maSoDi, "regular");
                	if (benXeDi != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXeDi.getShortName());
                		co.setTitle(benXeDi.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe_di(benXeSource);                		
                	}
                	else {
                		tuyen.setGhichu("Không có thông tin bến đi");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);               		
                	}
//                	BenXe benXeDen = benXeRepository.findByMaBXAndSo(maBenDen, soDenText);
                	BenXe benXeDen = benXeRepository.findByShortNameAndStorage(maBenDen + "_" + maSoDen, "regular");
                	if (benXeDen != null) {
                		BenXeSource benXeSource = new BenXeSource();
                		CoreObject co = new CoreObject();
                		co.setShortName(benXeDen.getShortName());
                		co.setTitle(benXeDen.getTitle());
                		benXeSource.set_source(co);
                		tuyen.setBen_xe_den(benXeSource);                		
                	}
                	else {
                		tuyen.setGhichu("Không có thông tin bến đến");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);                		
                	}

                	DictItem soDiItem = dictItemRepository.findByShortNameAndCollection(maSoDi, "GOVERNMENT");
            		CoreObjectSource soDiSource = new CoreObjectSource();
            		CoreObjectSource soDenSource = new CoreObjectSource();
            		CoreObjectSource[] coquan_ql = new CoreObjectSource[2];
                	if (soDiItem != null) {
                		CoreObject soDiObject = new CoreObject();
                		soDiObject.setShortName(soDiItem.getShortName());
                		soDiObject.setTitle(soDiItem.getTitle());
                		soDiSource.set_source(soDiObject);
                		tuyen.setNoi_di(soDiSource);
                		coquan_ql[0] = soDiSource;
                	}
                	DictItem soDenItem = dictItemRepository.findByShortNameAndCollection(maSoDen, "GOVERNMENT");
                	if (soDenItem != null) {
                		CoreObject soDenObject = new CoreObject();
                		soDenObject.setShortName(soDenItem.getShortName());
                		soDenObject.setTitle(soDenItem.getTitle());
                		soDenSource.set_source(soDenObject);
                		tuyen.setNoi_den(soDenSource);
                		coquan_ql[1] = soDenSource;
                	}
                	tuyen.setCoquan_ql(coquan_ql);

                	AccessRole[] arr1 = new AccessRole[2];
                	arr1[0] = new AccessRole();
                	arr1[0].setShortName(maSoDi + "_EDIT");
                	Role soDiRole = roleRepository.findByShortName(maSoDi + "_EDIT");
                	if (soDiRole != null) {
                		arr1[0].setTitle(soDiRole.getTitle());
                	}

                	arr1[0].setPermission("2");

                	arr1[1] = new AccessRole();
                	arr1[1].setShortName(maSoDen + "_EDIT");
                	Role soDenRole = roleRepository.findByShortName(maSoDen + "_EDIT");
                	if (soDenRole != null) {
                		arr1[1].setTitle(soDenRole.getTitle());
                	}

                	arr1[1].setPermission("2");

                	arr1[1] = new AccessRole();
                	arr1[1].setShortName("TCDB");
                	Role tcdbRole = roleRepository.findByShortName("TCDB");
                	if (tcdbRole != null) {
                		arr1[1].setTitle(tcdbRole.getTitle());
                	}                    	
                	arr1[1].setPermission("1");
                	
                	tuyen.setAccessRoles(arr1);
                	
                	CoreObject loaiTuyenNode = new CoreObject();
            		LoaiTuyenSource loaiTuyen = new LoaiTuyenSource();
                	
                	if (!maSoDi.contentEquals(maSoDen)) {                		
                		loaiTuyenNode.setShortName("1");
                		loaiTuyenNode.setTitle("Nội tỉnh");
                		loaiTuyen.set_source(loaiTuyenNode);
                		tuyen.setLoai_tuyen(loaiTuyen);
                	}
                	else {
                		loaiTuyenNode.setShortName("2");
                		loaiTuyenNode.setTitle("Liên tỉnh");
                		loaiTuyen.set_source(loaiTuyenNode);
                		tuyen.setLoai_tuyen(loaiTuyen);                		
                	}
//                	System.out.println("" + maSoDi + ", " + maSoDen + ", " + maBenDi + ", " + maBenDen);
                }
                else {
                	tuyen.setGhichu("Mã tuyến đánh sai quy tắc");
                	tuyen.setStorage("review");
                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                	CoreObject reviewObject = new CoreObject();
                	reviewObject.setShortName("3");
                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                	reviewStatusSource.set_source(reviewObject);
                	lstReviews.add(reviewStatusSource);                	
                }

                if (checkTuyens.containsKey(shortName)) {
                	if (checkTuyens.get(shortName).size() > 1) {
                		tuyen.setGhichu("Mã tuyến trùng nhau");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("4");
                    	reviewObject.setTitle("Tuyến trùng mã");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);               		
                	}
                }
                tuyen.setSite("guest");
                if (tuyen.getStorage() == null) {
                    tuyen.setStorage("regular");                	
                }
                tuyen.setOpenAccess("1");
                tuyen.setCreatedAt((new Date()).getTime());
                tuyen.setModifiedAt((new Date()).getTime());

                if (!checkTuyens.containsKey(shortName)
                		|| checkTuyens.get(shortName).size() > 1) {
                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                	CoreObject reviewObject = new CoreObject();
                	reviewObject.setShortName("4");
                	reviewObject.setTitle("Tuyến trùng mã");
                	reviewStatusSource.set_source(reviewObject);
                	lstReviews.add(reviewStatusSource);
                }
                else {
                	if (mode != null && "checkduplicate".contentEquals(mode)) {
                    	List<Tuyen> listTuyens = tuyenRepository.findByShortName(tuyen.getShortName());
                    	if (listTuyens.size() > 0) {
                    		for (Tuyen t : listTuyens) {
                    			t.setStorage("review");
                            	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                            	CoreObject reviewObject = new CoreObject();
                            	reviewObject.setShortName("4");
                            	reviewObject.setTitle("Tuyến trùng mã");
                            	reviewStatusSource.set_source(reviewObject);
                            	CoreObjectSource[] arrReviews = new CoreObjectSource[t.getReview_status().length + 1];
                            	System.arraycopy(t.getReview_status(), 0, arrReviews, 0, t.getReview_status().length);
                            	arrReviews[t.getReview_status().length] = reviewStatusSource;
                            	t.setReview_status(arrReviews);
                            	
                            	tuyenRepository.save(t);
                    		}
                    		tuyen.setStorage("review");
                        	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                        	CoreObject reviewObject = new CoreObject();
                        	reviewObject.setShortName("4");
                        	reviewObject.setTitle("Tuyến trùng mã");
                        	reviewStatusSource.set_source(reviewObject);
                        	lstReviews.add(reviewStatusSource);
                    	}                		
                	}
                	else {
                        Tuyen oldTuyen = tuyenRepository.findByShortNameAndStorage(tuyen.getShortName(), "regular");
                        if (oldTuyen != null && ! "review".contentEquals(oldTuyen.getStorage())) {
                        	tuyen.setId(oldTuyen.getId());
                        }                	                	                		
                	}
                }
                CoreObjectSource[] reviewStatus = new CoreObjectSource[lstReviews.size()];
                lstReviews.toArray(reviewStatus);
                
                tuyen.setReview_status(reviewStatus);
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
                file.getContentType(), file.getSize(), message);
    }
    
    @PostMapping("/benxe")
    public UploadFileResponse importBenXe(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String message = "";
        
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
                file.getContentType(), file.getSize(), message);
    }
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String message = "";
        
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
                file.getContentType(), file.getSize(), message);
    }

	private boolean checkBlank(Row row) {
		if (row == null) return true;
		if (row.getCell(0) == null) return true;
		String value = "";
		if (row.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
			value = (int)row.getCell(0).getNumericCellValue() + "";
		}
		else {
			value = row.getCell(0).getStringCellValue();
		}
		return "".contentEquals(value);
	}

    @PostMapping("/bieudo")
    @ResponseBody
    public UploadFileResponse importBieuDo(@RequestParam("file") MultipartFile file,
    		@RequestParam(name = "role", required=false) String role) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String message = "";
        
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
            
            int currentIndex = 0;
            
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
            boolean endOfSheet = false;
            int countBlank = 0;
            
            while (!endOfSheet) {
                Row tenTuyenRow = datatypeSheet.getRow(currentIndex++);
                if (tenTuyenRow == null) break;
                Cell tenTuyenCell = tenTuyenRow.getCell(4);
                if (tenTuyenCell == null) break;
                System.out.println("Ten tuyen: " + tenTuyenCell.getStringCellValue());
                Row benXeDiRow = datatypeSheet.getRow(currentIndex++);
                Cell tenBenDi = benXeDiRow.getCell(4);
                System.out.println("Ten ben xe di: " + tenBenDi.getStringCellValue());
                
                Row benXeDenRow = datatypeSheet.getRow(currentIndex++);
                Cell tenBenDen = benXeDenRow.getCell(4);
                System.out.println("Ten ben xe den: " + tenBenDen.getStringCellValue());
                
                Row benXeMaSoTuyenRow = datatypeSheet.getRow(currentIndex++);
                Cell maSoTuyenCell = benXeMaSoTuyenRow.getCell(4);
                System.out.println("Ma so tuyen: " + maSoTuyenCell.getStringCellValue());            

                Row loTrinhTuyenRow = datatypeSheet.getRow(currentIndex++);
                Cell loTrinhTuyenCell = loTrinhTuyenRow.getCell(4);
                System.out.println("Lo trinh tuyen: " + loTrinhTuyenCell.getStringCellValue());            

                Row cuLyTuyenRow = datatypeSheet.getRow(currentIndex++);
                Cell cuLyTuyenCell = cuLyTuyenRow.getCell(4);
                if (cuLyTuyenCell.getCellTypeEnum() == CellType.NUMERIC) {
                	
                }
                else {
                	System.out.println("Cu ly tuyen: " + cuLyTuyenCell.getStringCellValue());  
                }
                
                Row tongSoChuyenRow = datatypeSheet.getRow(currentIndex++);
                Cell tongSoChuyenCell = tongSoChuyenRow.getCell(4);
                if (tongSoChuyenCell.getCellTypeEnum() == CellType.NUMERIC) {
                	System.out.println("Tong so chuyen: " + (int)tongSoChuyenCell.getNumericCellValue());
                }
                else {
                    System.out.println("Tong so chuyen: " + tongSoChuyenCell.getStringCellValue());                            	
                }
                
                int countNode = 0;
                boolean endOfTable = false;
                currentIndex += 4;
                int ll_kt = 0;
                
                Tuyen tuyen = tuyenRepository.findByShortNameAndStorage(maSoTuyenCell.getStringCellValue().trim(), "regular");
                if (tuyen != null) {
                	System.out.println("Tuyến đã có: " + tuyen.getLotrinh_di());
                }
                else {
//                	List<Tuyen> lstTuyens = tuyenRepository.findByShortNameDraft(maSoTuyenCell.getStringCellValue(), "draft");
//                	if (lstTuyens.size() > 0) {
//                    	tuyen = lstTuyens.get(0);                		
//                	}
//                	if (tuyen == null) {
//                    	tuyen = new Tuyen();
//                    	tuyen.setShortName(maSoTuyenCell.getStringCellValue());
//                    	tuyen.setLotrinh_di(loTrinhTuyenCell.getStringCellValue());
//                    	tuyen.setLotrinh_den(loTrinhTuyenCell.getStringCellValue());
//                    	String cuLyTuyen = "";
//                    	if (cuLyTuyenCell.getCellTypeEnum() == CellType.NUMERIC) {
//                    		cuLyTuyen = (int)cuLyTuyenCell.getNumericCellValue() + "";
//                    	}
//                    	else {
//                    		cuLyTuyen = cuLyTuyenCell.getStringCellValue();
//                    	}
//                    	tuyen.setCu_ly(Double.parseDouble(cuLyTuyen.split(" ")[0]));
//                    	String tongSoChuyen = "";
//                    	if (tongSoChuyenCell.getCellTypeEnum() == CellType.NUMERIC) {
//                    		tongSoChuyen = (int)tongSoChuyenCell.getNumericCellValue() + "";
//                    	}
//                    	else {
//                    		tongSoChuyen = tongSoChuyenCell.getStringCellValue();
//                    	}
//                    	tuyen.setLl_quyhoach(Integer.parseInt(tongSoChuyen.split(" ")[0]));
//                    	CoreObjectSource plSource = new CoreObjectSource();
//                    	CoreObject plNode = new CoreObject();
//                    	plNode.setShortName("01");
//                    	plNode.setTitle("Tuyến mới");
//                    	plSource.set_source(plNode);
//                    	tuyen.setPl_quyhoach(plSource);
//                    	
//                    	CoreObjectSource statusSource = new CoreObjectSource();
//                    	CoreObject statusNode = new CoreObject();
//                    	statusNode.setShortName("1");
//                    	statusNode.setTitle("Tạo mới");
//                    	
//                    	statusSource.set_source(statusNode);
//                    	tuyen.setStatus(statusSource);
//                    	
//                    	List<CoreObjectSource> lstReviews = new ArrayList<CoreObjectSource>();
//                    	
//                        String[] tuyenArr = tuyen.getShortName().split("\\.");
//                        if (tuyenArr.length > 2) {
//                        	String soStr = tuyenArr[0];
//                        	String benXeStr = tuyenArr[1];
//                        	if (soStr.length() == 4 && benXeStr.length() == 4) {
//                            	String maSoDi = soStr.substring(0, 2);
//                            	String maSoDen = soStr.substring(2, 4);
//                            	String maBenDi = benXeStr.substring(0, 2);
//                            	String maBenDen = benXeStr.substring(2, 4);
//                            	if (Integer.parseInt(maSoDi) > Integer.parseInt(maSoDen)) {
//                                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
//                                	CoreObject reviewObject = new CoreObject();
//                                	reviewObject.setShortName("3");
//                                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
//                                	reviewStatusSource.set_source(reviewObject);
//                                	tuyen.setGhichu("Mã tuyến bị viết ngược");
//                                	lstReviews.add(reviewStatusSource);
//                            	}
////                            	BenXe benXe = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
//                            	BenXe benXe = benXeRepository.findByShortNameAndStorage(maBenDi + "_" + maSoDi, "regular");
//                            	if (benXe != null) {
//                            		BenXeSource benXeSource = new BenXeSource();
//                            		CoreObject co = new CoreObject();
//                            		co.setShortName(benXe.getShortName());
//                            		co.setTitle(benXe.getTitle());
//                            		benXeSource.set_source(co);
//                            		tuyen.setBen_xe(benXeSource);
//                            	}
//                            	else {
//                            		tuyen.setGhichu("Không có thông tin bến đi");
//                            		tuyen.setStorage("review");
//                                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
//                                	CoreObject reviewObject = new CoreObject();
//                                	reviewObject.setShortName("3");
//                                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
//                                	reviewStatusSource.set_source(reviewObject);
//                                	lstReviews.add(reviewStatusSource);
//                            	}
////                            	BenXe benXeDi = benXeRepository.findByMaBXAndSo(maBenDi, soDiText);
//                            	BenXe benXeDi = benXeRepository.findByShortNameAndStorage(maSoDi + "_" + maSoDi, "regular");
//                            	if (benXeDi != null) {
//                            		BenXeSource benXeSource = new BenXeSource();
//                            		CoreObject co = new CoreObject();
//                            		co.setShortName(benXeDi.getShortName());
//                            		co.setTitle(benXeDi.getTitle());
//                            		benXeSource.set_source(co);
//                            		tuyen.setBen_xe_di(benXeSource);                		
//                            	}
//                            	else {
//                            		tuyen.setGhichu("Không có thông tin bến đi");
//                            		tuyen.setStorage("review");
//                                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
//                                	CoreObject reviewObject = new CoreObject();
//                                	reviewObject.setShortName("3");
//                                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
//                                	reviewStatusSource.set_source(reviewObject);
//                                	lstReviews.add(reviewStatusSource);               		
//                            	}
////                            	BenXe benXeDen = benXeRepository.findByMaBXAndSo(maBenDen, soDenText);
//                            	BenXe benXeDen = benXeRepository.findByShortNameAndStorage(maBenDen + "_" + maSoDen, "regular");
//                            	if (benXeDen != null) {
//                            		BenXeSource benXeSource = new BenXeSource();
//                            		CoreObject co = new CoreObject();
//                            		co.setShortName(benXeDen.getShortName());
//                            		co.setTitle(benXeDen.getTitle());
//                            		benXeSource.set_source(co);
//                            		tuyen.setBen_xe_den(benXeSource);                		
//                            	}
//                            	else {
//                            		tuyen.setGhichu("Không có thông tin bến đến");
//                            		tuyen.setStorage("review");
//                                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
//                                	CoreObject reviewObject = new CoreObject();
//                                	reviewObject.setShortName("3");
//                                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
//                                	reviewStatusSource.set_source(reviewObject);
//                                	lstReviews.add(reviewStatusSource);                		
//                            	}
//
//                            	DictItem soDiItem = dictItemRepository.findByShortNameAndCollection(maSoDi, "GOVERNMENT");
//                        		CoreObjectSource soDiSource = new CoreObjectSource();
//                        		CoreObjectSource soDenSource = new CoreObjectSource();
//                        		CoreObjectSource[] coquan_ql = new CoreObjectSource[2];
//                            	if (soDiItem != null) {
//                            		CoreObject soDiObject = new CoreObject();
//                            		soDiObject.setShortName(soDiItem.getShortName());
//                            		soDiObject.setTitle(soDiItem.getTitle());
//                            		soDiSource.set_source(soDiObject);
//                            		tuyen.setNoi_di(soDiSource);
//                            		coquan_ql[0] = soDiSource;
//                            	}
//                            	DictItem soDenItem = dictItemRepository.findByShortNameAndCollection(maSoDen, "GOVERNMENT");
//                            	if (soDenItem != null) {
//                            		CoreObject soDenObject = new CoreObject();
//                            		soDenObject.setShortName(soDenItem.getShortName());
//                            		soDenObject.setTitle(soDenItem.getTitle());
//                            		soDenSource.set_source(soDenObject);
//                            		tuyen.setNoi_den(soDenSource);
//                            		coquan_ql[1] = soDenSource;
//                            	}
//                            	tuyen.setCoquan_ql(coquan_ql);
//
//                            	AccessRole[] arr1 = new AccessRole[2];
//                            	arr1[0] = new AccessRole();
//                            	arr1[0].setShortName(maSoDi + "_EDIT");
//                            	Role soDiRole = roleRepository.findByShortName(maSoDi + "_EDIT");
//                            	if (soDiRole != null) {
//                            		arr1[0].setTitle(soDiRole.getTitle());
//                            	}
//                            	arr1[0].setPermission("2");
//
//                            	arr1[1] = new AccessRole();
//                            	arr1[1].setShortName("TCDB");
//                            	Role tcdbRole = roleRepository.findByShortName("TCDB");
//                            	if (tcdbRole != null) {
//                            		arr1[1].setTitle(tcdbRole.getTitle());
//                            	}                    	
//                            	arr1[1].setPermission("1");
//                            	
//                            	CoreObject loaiTuyenNode = new CoreObject();
//                        		LoaiTuyenSource loaiTuyen = new LoaiTuyenSource();
//                            	
//                            	if (maSoDi.contentEquals(maSoDen)) {                		
//                            		loaiTuyenNode.setShortName("1");
//                            		loaiTuyenNode.setTitle("Nội tỉnh");
//                            		loaiTuyen.set_source(loaiTuyenNode);
//                            		tuyen.setLoai_tuyen(loaiTuyen);
//                            	}
//                            	else {
//                            		loaiTuyenNode.setShortName("2");
//                            		loaiTuyenNode.setTitle("Liên tỉnh");
//                            		loaiTuyen.set_source(loaiTuyenNode);
//                            		tuyen.setLoai_tuyen(loaiTuyen);                		
//                            	}
////                            	System.out.println("" + maSoDi + ", " + maSoDen + ", " + maBenDi + ", " + maBenDen);                        		
//                        	}
//                        	else {
//                            	tuyen.setGhichu("Mã tuyến đánh sai quy tắc");
//                            	tuyen.setStorage("review");
//                            	CoreObjectSource reviewStatusSource = new CoreObjectSource();
//                            	CoreObject reviewObject = new CoreObject();
//                            	reviewObject.setShortName("3");
//                            	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
//                            	reviewStatusSource.set_source(reviewObject);
//                            	lstReviews.add(reviewStatusSource);                	                        		
//                        	}
//                        }
//                        else {
//                        	tuyen.setGhichu("Mã tuyến đánh sai quy tắc");
//                        	tuyen.setStorage("review");
//                        	CoreObjectSource reviewStatusSource = new CoreObjectSource();
//                        	CoreObject reviewObject = new CoreObject();
//                        	reviewObject.setShortName("3");
//                        	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
//                        	reviewStatusSource.set_source(reviewObject);
//                        	lstReviews.add(reviewStatusSource);                	
//                        }
//
//                        tuyen.setSite("guest");
//                        if (tuyen.getStorage() == null) {
//                            tuyen.setStorage("regular");                	
//                        }
//                        tuyen.setOpenAccess("1");
//                        tuyen.setCreatedAt((new Date()).getTime());
//                        tuyen.setModifiedAt((new Date()).getTime());
//                    	
//                    	tuyen = tuyenRepository.save(tuyen);                		
//                	}
                }

                List<Not> nots = new ArrayList<Not>();
                
                while (!endOfTable) {
                	Row lastRow = datatypeSheet.getRow(currentIndex++);
                	if (lastRow != null)
                		System.out.println("Current row: " + lastRow.getRowNum());
                	if (lastRow == null) {
    					currentIndex += 3;
    					break;                		
                	}
                		Cell sttCell = lastRow.getCell(0);
            			Cell gioDiCell = lastRow.getCell(2);
            			Cell gioDenCell = lastRow.getCell(3);
//            			System.out.println("Gio di: " + sdf.format(gioDiCell.getDateCellValue()));
//            			System.out.println("Gio den: " + sdf.format(gioDenCell.getDateCellValue()));
            			String stt = "";
            			
            			if (sttCell != null) {
            				if (sttCell.getCellTypeEnum() == CellType.NUMERIC) {
            					stt = (int)sttCell.getNumericCellValue() + "";
            				}
            				else {
            					stt = sttCell.getStringCellValue();
            				}
            				System.out.println("STT: " + stt.contentEquals(""));
            			}
            			if (sttCell == null || "".contentEquals(stt)) {
            				currentIndex += 3;
            				break;
            			}
            			countNode++;
            			Not not = new Not();
            			nots.add(not);
            			
            			System.out.println("Gio di: " + gioDiCell.getStringCellValue());
            			String gioDi = gioDiCell.getStringCellValue();
            			if (!"".contentEquals(gioDi)) {
            				int hIndex = gioDi.indexOf("h");
            				
                			int gioDiInt = Integer.parseInt(gioDi.substring(0, hIndex));
                			int phutDiInt = Integer.parseInt(gioDi.substring(hIndex + 1));
                			
//                			c.setTime(gioDiCell.getDateCellValue());
                			c.set(1970, 1, 1, gioDiInt - 1, phutDiInt, 0);
                			
//                			System.out.println("Gio di: " + c.getTimeInMillis());
                			not.setGio_bendi(c.getTimeInMillis());            				
            			}
            			else {
            				not.setGio_bendi(0l);
            			}

            			String gioDen = gioDenCell != null ? gioDenCell.getStringCellValue() : "";
            			if (!"".contentEquals(gioDen)) {
            				System.out.println("Gio den: " + gioDen);
            				int hIndex = gioDen.indexOf("h");
                			int gioDenInt = Integer.parseInt(gioDen.substring(0, hIndex));
                			int phutDenInt = Integer.parseInt(gioDen.substring(hIndex + 1));
                			
//                			c.setTime(gioDenCell.getDateCellValue());
                			c.set(1970, 1, 1, gioDenInt - 1, phutDenInt, 0);
                			not.setGio_benden(c.getTimeInMillis());            				
            			}
            			else {
            				not.setGio_benden(0l);
            			}

            			CoreObjectSource statusSource = new CoreObjectSource();
            			CoreObject statusNode = new CoreObject();
            			statusNode.setShortName("2");
            			statusNode.setTitle("Công bố");
            			
            			statusSource.set_source(statusNode);
            			not.setStatus(statusSource);
            			not.setType("qlvt_not");
            			CoreObjectSource tuyenSource = new CoreObjectSource();
            			CoreObject tuyenNode = new CoreObject();
            			tuyenNode.setShortName(maSoTuyenCell.getStringCellValue());
            			tuyenSource.set_source(tuyenNode);
            			
            			not.setTuyen(tuyenSource);
            			NotNgaySource[] notSources = new NotNgaySource[30];

                		for (int i = 1; i <= 15; i++) {
                			notSources[i - 1] = new NotNgaySource();
                			NotNgay notNgay = new NotNgay();
                			notNgay.setShortName(i + "");
                			String maSoTuyen = maSoTuyenCell.getStringCellValue();
                			List<Tuyen> oldTuyens = tuyenRepository.findByShortNameDraft(maSoTuyen, "draft");
                			                			
                			String[] tuyenArr = maSoTuyenCell.getStringCellValue().split("\\.");
                            if (tuyenArr.length > 2) {
                            	String soStr = tuyenArr[0];
                            	String benXeStr = tuyenArr[1];
                            	if (soStr.length() != 4) continue;
                            	if (benXeStr.length() != 4) continue;

                            	String maSoDi = soStr.substring(0, 2);
                            	String maSoDen = soStr.substring(2, 4);
                            	String maBenDi = benXeStr.substring(0, 2);
                            	String maBenDen = benXeStr.substring(2, 4);
                            	if (tuyen == null && oldTuyens.size() == 0) {
                            	                          		
                            	}
                            	else {
                            		GioBen gioBenDi = new GioBen();
                            		if (tuyen != null && tuyen.getBen_xe_di() != null) {
                                		gioBenDi.setBen_xe(tuyen.getBen_xe_di().get_source().getShortName());
                                		notNgay.setGio_bendi(gioBenDi);                            			
                            		}
                            		GioBen gioBenDen = new GioBen();
                            		if (tuyen != null && tuyen.getBen_xe_den() != null) {
                                		gioBenDen.setBen_xe(tuyen.getBen_xe_den().get_source().getShortName());                            			
                                		notNgay.setGio_benden(gioBenDen);
                            		}
                        			Cell mauNotDi = lastRow.getCell((i - 1) * 2 + 2);
                        			System.out.println("Check color in row: " + lastRow.getRowNum() + ", cell: " + ((i - 1) * 2 + 3));
//                        			if (fileName.endsWith("xls")) {
//                        				
//                        			}
//                        			else if (fileName.endsWith("xlsx")) {
                        				if (mauNotDi != null) {
                            				Color color = mauNotDi.getCellStyle().getFillForegroundColorColor();
                            				
                            			    if (color != null) {
                            			         if (color instanceof XSSFColor) {
                            			        	 if ("FF99FF99".contentEquals(((XSSFColor)color).getARGBHex()) 
                            			        			 || "FF66FFCC".contentEquals(((XSSFColor)color).getARGBHex())
                            			        			 || "FF00CCFF".contentEquals(((XSSFColor)color).getARGBHex())
                            			        			 ) {
                            			        		 gioBenDi.setStatus("3");
                            			        	 }
                            			        	 else if ("FF00FF00".contentEquals(((XSSFColor)color).getARGBHex())
                            			        			 || "FF1FB714".contentEquals(((XSSFColor)color).getARGBHex())) {
                            			        		 gioBenDi.setStatus("2");
                            			        	 }
                            			        	 else {
                            			        		 gioBenDi.setStatus("1");
                            			        	 }
                            			         } else if (color instanceof HSSFColor) {
                            			        	 if ("9999:FFFF:9999".contentEquals(((HSSFColor)color).getHexString()) 
                            			        			 || "6666:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
                            			        			 || "CCCC:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
                            			        			 ) {
                            			        		 gioBenDi.setStatus("3");
                            			        	 }
                            			        	 else if ("0000:FFFF:0000".contentEquals(((HSSFColor)color).getHexString())
                            			        			 || "1F1F:B7B7:1414".contentEquals(((HSSFColor)color).getHexString())) {
                            			        		 gioBenDi.setStatus("2");
                            			        	 }
                            			        	 else {
                            			        		 gioBenDi.setStatus("1");
                            			        	 }
                            			         }
                            			         else {
                            			        	 gioBenDi.setStatus("1");
                            			         }
                            			    }    
                            			    else {
                            			    	gioBenDi.setStatus("1");
                            			    }                        					
                        				}
                        				else {
                        					gioBenDi.setStatus("1");
                        				}
//                        			}

                        			System.out.println("Gio ben di: " + gioBenDi.getStatus());
                        			Cell mauNotDen = lastRow.getCell((i - 1) * 2 + 3);
                        			System.out.println("Check color in row: " + lastRow.getRowNum() + ", cell: " + ((i - 1) * 2 + 4));
                        			
//                        			if (fileName.endsWith("xls")) {
//                        				
//                        			}
//                        			else if (fileName.endsWith("xlsx")) {
                        				if (mauNotDen != null) {
                            				Color color = mauNotDen.getCellStyle().getFillForegroundColorColor();
                            				
                            			    if (color != null) {
                            			         if (color instanceof XSSFColor) {
                            			        	 if ("FF99FF99".contentEquals(((XSSFColor)color).getARGBHex()) 
                            			        			 || "FF66FFCC".contentEquals(((XSSFColor)color).getARGBHex())
                            			        			 || "FF00CCFF".contentEquals(((XSSFColor)color).getARGBHex())
                            			        			 ) {
                            			        		 gioBenDen.setStatus("3");
                            			        	 }
                            			        	 else if ("FF00FF00".contentEquals(((XSSFColor)color).getARGBHex())
                            			        			 || "FF1FB714".contentEquals(((XSSFColor)color).getARGBHex())) {
                            			        		 gioBenDen.setStatus("2");
                            			        	 }
                            			        	 else {
                            			        		 gioBenDen.setStatus("1");
                            			        	 }
                            			         } else if (color instanceof HSSFColor) {
    	                    			        	 if ("9999:FFFF:9999".contentEquals(((HSSFColor)color).getHexString()) 
    	                    			        			 || "6666:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
    	                    			        			 || "CCCC:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
    	                    			        			 ) {
    	                    			        		 gioBenDen.setStatus("3");
    	                    			        	 }
    	                    			        	 else if ("0000:FFFF:0000".contentEquals(((HSSFColor)color).getHexString())
    	                    			        			 || "1F1F:B7B7:1414".contentEquals(((HSSFColor)color).getHexString())) {
    	                    			        		 gioBenDen.setStatus("2");
    	                    			        	 }
    	                    			        	 else {
    	                    			        		 gioBenDen.setStatus("1");
    	                    			        	 }
                            			         }
                            			         else {
                            			        	 gioBenDen.setStatus("1");
                            			         }
                            			    }    
                            			    else {
                            			    	gioBenDen.setStatus("1");
                            			    }                        					
                        				}
                        				else {
                        					gioBenDen.setStatus("1");
                        				}
//                        			}
                        			
                        			if ((gioBenDi != null && gioBenDen != null && gioBenDi.getStatus() != null && gioBenDen.getStatus() != null) &&  ("3".contentEquals(gioBenDi.getStatus()) || "3".contentEquals(gioBenDen.getStatus()))) {
                        				ll_kt++;
                        			}
                        			System.out.println("Gio ben den: " + gioBenDen.getStatus());
                            	}
                            	
//                            	System.out.println("" + maSoDi + ", " + maSoDen + ", " + maBenDi + ", " + maBenDen);
                            }
                			
                			
                			notSources[i - 1].set_source(notNgay);
                		}
                		
            			not.setNot_ngay(notSources);                			
//            			notRepository.save(not);
//                		System.out.println("STT: " + sttCell.getNumericCellValue());		
                }
                System.out.println("Total node: " + countNode);
                
                for (int temp = 0; temp < countNode; temp++) {
                	Row lastRow = datatypeSheet.getRow(currentIndex++);
                	System.out.println("Current row: " + lastRow.getRowNum());
            		Cell sttCell = lastRow.getCell(0);
        			Cell gioDiCell = lastRow.getCell(2);
        			Cell gioDenCell = lastRow.getCell(3);
//        			System.out.println("Gio di: " + sdf.format(gioDiCell.getDateCellValue()));
//        			System.out.println("Gio den: " + sdf.format(gioDenCell.getDateCellValue()));  
        			if (sttCell == null || sttCell.getCellTypeEnum() == CellType.STRING) {
        				if (sttCell == null || "".contentEquals(sttCell.getStringCellValue())) {
        					break;
        				}
        			}
        			
        			String gioDi = gioDiCell.getStringCellValue();
//        			System.out.println("Gio di: " + c.getTimeInMillis());
        			
        			Not not = nots.get(temp);
        			
        			if (!"".contentEquals(gioDi)) {
            			System.out.println("Gio di: " + gioDiCell.getStringCellValue());
        				int hIndex = gioDi.indexOf("h");
            			int gioDiInt = Integer.parseInt(gioDi.substring(0, hIndex));
            			int phutDiInt = Integer.parseInt(gioDi.substring(hIndex + 1));
            			
//            			c.setTime(gioDiCell.getDateCellValue());
            			c.set(1970, 1, 1, gioDiInt - 1, phutDiInt, 0);        				
            			not.setGio_bendi(c.getTimeInMillis());
        			}
        			else {
        				not.setGio_bendi(0l);
        			}
        			
//        			System.out.println("Gio di: " + c.getTimeInMillis());

        			String gioDen = gioDenCell != null ? gioDenCell.getStringCellValue() : "";
        			if (!"".contentEquals(gioDen.trim())) {
        				int hIndex = gioDen.indexOf("h");
            			int gioDenInt = Integer.parseInt(gioDen.substring(0, hIndex));
            			int phutDenInt = Integer.parseInt(gioDen.substring(hIndex + 1));
            			
//            			c.setTime(gioDenCell.getDateCellValue());
            			c.set(1970, 1, 1, gioDenInt - 1, phutDenInt, 0);        				
            			not.setGio_benden(c.getTimeInMillis());
        			}
        			else {
        				not.setGio_benden(0l);
        			}
        			

        			CoreObjectSource statusSource = new CoreObjectSource();
        			CoreObject statusNode = new CoreObject();
        			statusNode.setShortName("2");
        			statusNode.setTitle("Công bố");
        			
        			statusSource.set_source(statusNode);
        			not.setStatus(statusSource);
        			not.setType("qlvt_not");
        			CoreObjectSource tuyenSource = new CoreObjectSource();
        			CoreObject tuyenNode = new CoreObject();
        			tuyenNode.setShortName(maSoTuyenCell.getStringCellValue());
        			tuyenSource.set_source(tuyenNode);
        			
        			not.setTuyen(tuyenSource);
        			NotNgaySource[] notSources = not.getNot_ngay();

            		for (int i = 1; i <= 15; i++) {
            			if (notSources != null) {
                			notSources[i - 1 + 15] = new NotNgaySource();            				
            			}
            			NotNgay notNgay = new NotNgay();
            			notNgay.setShortName((i + 15) + "");
            			String maSoTuyen = maSoTuyenCell.getStringCellValue();
            			List<Tuyen> oldTuyens = tuyenRepository.findByShortNameDraft(maSoTuyen, "draft");
            			                			
            			String[] tuyenArr = maSoTuyenCell.getStringCellValue().split("\\.");
                        if (tuyenArr.length > 2) {
                        	String soStr = tuyenArr[0];
                        	String benXeStr = tuyenArr[1];
                        	if (soStr.length() != 4 || benXeStr.length() != 4) break;
                        	
                        	String maSoDi = soStr.substring(0, 2);
                        	String maSoDen = soStr.substring(2, 4);
                        	String maBenDi = benXeStr.substring(0, 2);
                        	String maBenDen = benXeStr.substring(2, 4);
                        	if (tuyen == null && oldTuyens.size() == 0) {
                        	                          		
                        	}
                        	else {
                        		GioBen gioBenDi = new GioBen();
                        		System.out.println("Gio ben di: " + tuyen.getBen_xe_di());
                        		if (tuyen != null && tuyen.getBen_xe_di() != null) {
                            		gioBenDi.setBen_xe(tuyen.getBen_xe_di().get_source().getShortName());
                            		notNgay.setGio_bendi(gioBenDi);                        			
                        		}
                        		
                        		GioBen gioBenDen = new GioBen();
                        		if (tuyen != null && tuyen.getBen_xe_den() != null) {
                            		gioBenDen.setBen_xe(tuyen.getBen_xe_den().get_source().getShortName());
                            		notNgay.setGio_benden(gioBenDen);                        			
                        		}
                    			Cell mauNotDi = lastRow.getCell((i - 1) * 2 + 2);
                    			
//                    			if (fileName.endsWith("xls")) {
//                    				
//                    			}
//                    			else if (fileName.endsWith("xlsx")) {
                    				if (mauNotDi != null) {
                        				Color color = mauNotDi.getCellStyle().getFillForegroundColorColor();
                        				
                        			    if (color != null) {
	                       			         if (color instanceof XSSFColor) {
	                    			        	 if ("FF99FF99".contentEquals(((XSSFColor)color).getARGBHex()) 
	                    			        			 || "FF66FFCC".contentEquals(((XSSFColor)color).getARGBHex())
	                    			        			 || "FF00CCFF".contentEquals(((XSSFColor)color).getARGBHex())
	                    			        			 ) {
	                    			        		 gioBenDi.setStatus("3");
	                    			        	 }
	                    			        	 else if ("FF00FF00".contentEquals(((XSSFColor)color).getARGBHex())
	                    			        			 || "FF1FB714".contentEquals(((XSSFColor)color).getARGBHex())) {
	                    			        		 gioBenDi.setStatus("2");
	                    			        	 }
	                    			        	 else {
	                    			        		 gioBenDi.setStatus("1");
	                    			        	 }
	                    			         } else if (color instanceof HSSFColor) {
	                    			        	 if ("9999:FFFF:9999".contentEquals(((HSSFColor)color).getHexString()) 
	                    			        			 || "6666:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
	                    			        			 || "CCCC:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
	                    			        			 ) {
	                    			        		 gioBenDi.setStatus("3");
	                    			        	 }
	                    			        	 else if ("0000:FFFF:0000".contentEquals(((HSSFColor)color).getHexString())
	                    			        			 || "1F1F:B7B7:1414".contentEquals(((HSSFColor)color).getHexString())) {
	                    			        		 gioBenDi.setStatus("2");
	                    			        	 }
	                    			        	 else {
	                    			        		 gioBenDi.setStatus("1");
	                    			        	 }
	                    			         }
	                    			         else {
	                    			        	 gioBenDi.setStatus("1");
	                    			         }
                        			    }    
                        			    else {
                        			    	gioBenDi.setStatus("1");
                        			    }                        					
                    				}
                    				else {
                    					gioBenDi.setStatus("1");
                    				}
//                    			}

                    			Cell mauNotDen = lastRow.getCell((i - 1) * 2 + 3);
                    			
//                    			if (fileName.endsWith("xls")) {
//                    				
//                    			}
//                    			else if (fileName.endsWith("xlsx")) {
                    				if (mauNotDen != null) {
                        				Color color = mauNotDen.getCellStyle().getFillForegroundColorColor();
                        				
                        			    if (color != null) {
                        			         if (color instanceof XSSFColor) {
                        			        	 if ("FF99FF99".contentEquals(((XSSFColor)color).getARGBHex()) 
                        			        			 || "FF66FFCC".contentEquals(((XSSFColor)color).getARGBHex())
                        			        			 || "FF00CCFF".contentEquals(((XSSFColor)color).getARGBHex())
                        			        			 ) {
                        			        		 gioBenDen.setStatus("3");
                        			        	 }
                        			        	 else if ("FF00FF00".contentEquals(((XSSFColor)color).getARGBHex())
                        			        			 || "FF1FB714".contentEquals(((XSSFColor)color).getARGBHex())) {
                        			        		 gioBenDen.setStatus("2");
                        			        	 }
                        			        	 else {
                        			        		 gioBenDen.setStatus("1");
                        			        	 }
                        			         } else if (color instanceof HSSFColor) {
	                    			        	 if ("9999:FFFF:9999".contentEquals(((HSSFColor)color).getHexString()) 
	                    			        			 || "6666:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
	                    			        			 || "CCCC:FFFF:CCCC".contentEquals(((HSSFColor)color).getHexString())
	                    			        			 ) {
	                    			        		 gioBenDen.setStatus("3");
	                    			        	 }
	                    			        	 else if ("0000:FFFF:0000".contentEquals(((HSSFColor)color).getHexString())
	                    			        			 || "1F1F:B7B7:1414".contentEquals(((HSSFColor)color).getHexString())) {
	                    			        		 gioBenDen.setStatus("2");
	                    			        	 }
	                    			        	 else {
	                    			        		 gioBenDen.setStatus("1");
	                    			        	 }
                        			         }
                        			         else {
                        			        	 gioBenDen.setStatus("1");
                        			         }
                        			    }    
                        			    else {
                        			    	gioBenDen.setStatus("1");
                        			    }                        					
                    				}
                    				else {
                    					gioBenDen.setStatus("1");
                    				}
//                    			}
                    			
                    			if ((gioBenDi != null && gioBenDen != null && gioBenDi.getStatus() != null && gioBenDen.getStatus() != null) &&  ("3".contentEquals(gioBenDi.getStatus()) || "3".contentEquals(gioBenDen.getStatus()))) {
                    				ll_kt++;
                    			}
                        	}

//                        	System.out.println("" + maSoDi + ", " + maSoDen + ", " + maBenDi + ", " + maBenDen);
                        }
            			
            			if (notSources != null) {
                			notSources[i - 1 + 15].set_source(notNgay);            				
            			}
            		}
        			not.setNot_ngay(notSources);  
        			AccessRole[] arr = new AccessRole[2];
                	arr[0] = new AccessRole();
                	arr[0].setShortName(role);
                	Role soDiRole = roleRepository.findByShortName(role);
                	if (soDiRole != null) {
                		arr[0].setTitle(soDiRole.getTitle());
                	}                    	
                	arr[0].setPermission("2");

                	arr[1] = new AccessRole();
                	arr[1].setShortName("TCDB");
                	Role tcdbRole = roleRepository.findByShortName("TCDB");
                	if (tcdbRole != null) {
                		arr[1].setTitle(tcdbRole.getTitle());
                	}                    	
                	arr[1].setPermission("1");
        			
        			not.setAccessRoles(arr);

        			notRepository.save(not);
//            		System.out.println("STT: " + sttCell.getNumericCellValue());		
            	}
                
                if (ll_kt != 0) {
                	if (tuyen != null) {
                    	tuyen.setLl_kt(ll_kt);
                    	tuyenRepository.save(tuyen);                		
                	}
                }
                System.out.println("Current index: " + currentIndex);
                Row currentRow = datatypeSheet.getRow(currentIndex);
                Row nextRow = datatypeSheet.getRow(currentIndex + 1);
                if (currentRow == null && nextRow == null) endOfSheet = true;
                if (checkBlank(currentRow) && checkBlank(nextRow)) endOfSheet = true;
                currentIndex++;                
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), message);
    }
    
    @PostMapping("/cuakhaus")
    public UploadFileResponse importCuaKhaus(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String message = "";
        
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
                file.getContentType(), file.getSize(), message);
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
        String message = "";
        
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

            Map<String, List<Integer>> checkBenxes = new HashMap<String, List<Integer>>();
            
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);
                    if (valueCell == null) continue;
                    if ("shortName".contentEquals(key)) {
                    	String shortName = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	shortName = (int)valueCell.getNumericCellValue() + "";
                        }                    	
                        else {
                        	shortName = valueCell.getStringCellValue();
                        }
                        if (checkBenxes.containsKey(shortName)) {
                        	checkBenxes.get(shortName).add(currentRow.getRowNum());
                        }
                        else {
                        	List<Integer> lstRows = new ArrayList<Integer>();
                        	lstRows.add(currentRow.getRowNum());
                        	checkBenxes.put(shortName, lstRows);
                        }
                    }
                }
            }
            
            iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) continue;
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                String so_gtvt = "";
                
                for (String key : mapColumns.keySet()) {
                    int index = mapColumns.get(key);
                    Cell valueCell = currentRow.getCell(index);
                    if (valueCell == null) continue;
                    if ("shortName".contentEquals(key)) {
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            node.put(key, (int)valueCell.getNumericCellValue());
                        }   
                        else {
                        	node.put(key, valueCell.getStringCellValue());
                        }
                    }
                    else if ("tinh".equals(key)) {
                        ObjectNode tinhNode = mapper.createObjectNode();
                        tinhNode.put("shortName", valueCell.getStringCellValue());
                        ObjectNode tinhSourceNode = mapper.createObjectNode();
                        String tinhCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	tinhCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	tinhCode = valueCell.getStringCellValue();
                        }
                        DictItem tinhItem = dictItemRepository.findByShortNameAndCollection(tinhCode, "ADMINISTRATIVE_REGION");
                        if (tinhItem != null) {
                        	tinhNode.put("title", tinhItem.getTitle());
                        }
                        tinhSourceNode.put("_source", tinhNode);
                        
                        node.put("tinh", tinhSourceNode);
                    }
                    else if ("huyen".equals(key)) {
                        ObjectNode huyenNode = mapper.createObjectNode();
                        huyenNode.put("shortName", valueCell.getStringCellValue());
                        ObjectNode huyenSourceNode = mapper.createObjectNode();
                        String huyenCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	huyenCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	huyenCode = valueCell.getStringCellValue();
                        }
                        DictItem huyenItem = dictItemRepository.findByShortNameAndCollection(huyenCode, "ADMINISTRATIVE_REGION");
                        if (huyenItem != null) {
                        	huyenNode.put("title", huyenItem.getTitle());
                        }

                        huyenSourceNode.put("_source", huyenNode);
                        
                        node.put("huyen", huyenSourceNode);
                    }
                    else if ("xa".equals(key)) {
                        ObjectNode xaNode = mapper.createObjectNode();
                        xaNode.put("shortName", valueCell.getStringCellValue());
                        ObjectNode xaSourceNode = mapper.createObjectNode();
                        String xaCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	xaCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	xaCode = valueCell.getStringCellValue();
                        }
                        DictItem xaItem = dictItemRepository.findByShortNameAndCollection(xaCode, "ADMINISTRATIVE_REGION");
                        if (xaItem != null) {
                        	xaNode.put("title", xaItem.getTitle());
                        }
                        
                        xaSourceNode.put("_source", xaNode);
                        
                        node.put("xa", xaSourceNode);
                    }
                    else if ("loai_ben".equals(key)) {
                    	String loaiBenCode = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		loaiBenCode = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		loaiBenCode = valueCell.getStringCellValue();
                    	}
                        ObjectNode loaiBenNode = mapper.createObjectNode();
                        loaiBenNode.put("shortName", (int)valueCell.getNumericCellValue());
                        DictItem loaiBenItem = dictItemRepository.findByShortNameAndCollection(loaiBenCode, "LOAI_BEN");
                        if (loaiBenItem != null) {
                        	loaiBenNode.put("title", loaiBenItem.getTitle());
                        }
                        ObjectNode loaiBenSourceNode = mapper.createObjectNode();
                        loaiBenSourceNode.put("_source", loaiBenNode);
                        
                        node.put("loai_ben", loaiBenSourceNode);
                    }
                    else if ("trangthai".contentEquals(key)) {
                		ObjectNode trangThaiNode = mapper.createObjectNode();
                		ObjectNode trangThaiSource = mapper.createObjectNode();
                    	if (valueCell.getStringCellValue().contentEquals("Đang hoạt động")) {
                    		trangThaiNode.put("shortName", "1");
                    		trangThaiNode.put("title", "Còn hoạt động");
                    		trangThaiSource.put("_source", trangThaiNode);                    		
                    	}
                    	else {
                    		trangThaiNode.put("shortName", "0");
                    		trangThaiNode.put("title", "Ngừng hoạt động");
                    		trangThaiSource.put("_source", trangThaiNode);                    		
                    	}
                    }
                    else if ("status".contentEquals(key)) {
                		ObjectNode trangThaiNode = mapper.createObjectNode();
                		ObjectNode trangThaiSource = mapper.createObjectNode();
                		String trangThaiCode = "";
                		if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                			trangThaiCode = (int)valueCell.getNumericCellValue() + "";
                		}
                		else {
                			trangThaiCode = valueCell.getStringCellValue();
                		}
                		DictItem trangThaiItem = dictItemRepository.findByShortNameAndCollection(trangThaiCode, "TRANGTHAI_BENXE");
                		
                    	if (trangThaiItem != null) {
                    		trangThaiNode.put("shortName", trangThaiItem.getShortName());
                    		trangThaiNode.put("title", trangThaiItem.getTitle());
                    		trangThaiSource.put("_source", trangThaiNode);                    		
                    	}
                    }
                    else if ("so_gtvt".contentEquals(key)) {
                    	so_gtvt = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		so_gtvt = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		so_gtvt = valueCell.getStringCellValue();
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
                node.put("openAccess", "1");
                node.put("createdAt", (new Date().getTime()));
                node.put("modifiedAt", (new Date().getTime()));
//                node.put("coquan_ql", mapper.createObjectNode());

                BenXe benXe = mapper.readValue(node.toString(), BenXe.class);
                DictItem soGtvtItem = dictItemRepository.findByShortNameAndCollection(so_gtvt, "SO_GTVT");
                benXe.setMa_bx(benXe.getMa_bx());
                String uniqueShortName = benXe.getShortName();
                
                if (soGtvtItem != null) {
                    CoreObjectSource soGtvtSource = new CoreObjectSource();
                    CoreObject soGtvtObject = new CoreObject();
                    soGtvtObject.setShortName(soGtvtItem.getShortName());
                    soGtvtObject.setTitle(soGtvtItem.getTitle());
                    soGtvtSource.set_source(soGtvtObject);
                    benXe.setSo_gtvt(soGtvtSource);
                }
                
                if (!checkBenxes.containsKey(benXe.getShortName())
                		|| checkBenxes.get(benXe.getShortName()).size() > 1) {
                	benXe.setStorage("draft");
                }
                else {
                    BenXe oldBenXe = benXeRepository.findByShortName(uniqueShortName);
                    
                    if (oldBenXe != null) {
                    	benXe.setId(oldBenXe.getId());
                    }
                    else {
                    }                	
                }

                benXeRepository.save(benXe);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), message);
    }

    @PostMapping("/old/tuyen")
    public UploadFileResponse importOldTuyen(@RequestParam("file") MultipartFile file,
    		@RequestParam(name="reviewCode", required=false) String reviewCode,
    		@RequestParam(name="mode", required=false) String mode) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        String message = "";
        
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
                    	String loaiTuyenCode = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		loaiTuyenCode = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		loaiTuyenCode = valueCell.getStringCellValue();
                    	}
                    	DictItem loaiTuyenItem = dictItemRepository.findByShortNameAndCollection(loaiTuyenCode, "PL_TUYEN");
                    	if (loaiTuyenItem != null) {
                            loaiTuyenNode.put("shortName", loaiTuyenItem.getShortName());
                            loaiTuyenNode.put("title", loaiTuyenItem.getTitle());                    		
                            ObjectNode loaiTuyenSource = mapper.createObjectNode();
                            loaiTuyenSource.put("_source", loaiTuyenNode);
                            
                            node.put("pl_quyhoach", loaiTuyenSource);
                    	}
                    }
                    else if ("status".equals(key)) {
                    	String statusCode = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		statusCode = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		statusCode = valueCell.getStringCellValue();
                    	}
                    	DictItem statusItem = dictItemRepository.findByShortNameAndCollection(statusCode, "TRANGTHAI_TUYEN");
                    	if (statusItem != null) {
                            ObjectNode statusNode = mapper.createObjectNode();
                            statusNode.put("shortName", statusItem.getShortName());
                            statusNode.put("title", statusItem.getTitle());                    		
                            ObjectNode statusSource = mapper.createObjectNode();
                            statusSource.put("_source", statusNode);
                            
                            node.put("status", statusSource);
                    	}
                    }
                    else if ("ben_xe".equals(key)) {
                    	String benXeCode = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		benXeCode = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		benXeCode = valueCell.getStringCellValue();
                    	}
                    	BenXe benXe = benXeRepository.findByShortNameAndStorage(benXeCode, "regular");
                    	if (benXe != null) {
                            benXeNode.put("shortName", benXeCode);
                            benXeNode.put("title", benXe.getTitle());
                            ObjectNode benXeSourceNode = mapper.createObjectNode();
                            benXeSourceNode.put("_source", benXeNode);
                            
                            node.put("ben_xe", benXeSourceNode);                    		
                    	}
                    }
                    else if ("ben_xe_di".equals(key)) {
                    	String benXeCode = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		benXeCode = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		benXeCode = valueCell.getStringCellValue();
                    	}
                    	BenXe benXe = benXeRepository.findByShortNameAndStorage(benXeCode, "regular");
                    	if (benXe != null) {
                            benXeNode.put("shortName", benXeCode);
                            benXeNode.put("title", benXe.getTitle());
                            ObjectNode benXeSourceNode = mapper.createObjectNode();
                            benXeSourceNode.put("_source", benXeNode);
                            
                            node.put("ben_xe_di", benXeSourceNode);                    		
                    	}
                    }
                    else if ("ben_xe_den".equals(key)) {
                    	String benXeCode = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		benXeCode = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		benXeCode = valueCell.getStringCellValue();
                    	}
                    	BenXe benXe = benXeRepository.findByShortNameAndStorage(benXeCode, "regular");
                    	if (benXe != null) {
                            benXeNode.put("shortName", benXeCode);
                            benXeNode.put("title", benXe.getTitle());
                            ObjectNode benXeSourceNode = mapper.createObjectNode();
                            benXeSourceNode.put("_source", benXeNode);
                            
                            node.put("ben_xe_den", benXeSourceNode);                    		
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
                    node.put("openAccess", "1");
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                }
                Tuyen tuyen = mapper.readValue(node.toString(), Tuyen.class);
                List<CoreObjectSource> lstReviews = new ArrayList<CoreObjectSource>();
                if (reviewCode != null && !"".contentEquals(reviewCode)) {
                	DictItem reviewItem = dictItemRepository.findByShortNameAndCollection(reviewCode, "TRANGTHAI_RASOAT");
                	if (reviewItem != null) {
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName(reviewItem.getShortName());
                    	reviewObject.setTitle(reviewItem.getTitle());
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);                		
                	}
                }
                
                String[] tuyenArr = tuyen.getShortName().split("\\.");
                if (tuyenArr.length > 2) {
                	String soStr = tuyenArr[0];
                	String benXeStr = tuyenArr[1];
                	if (soStr.length() == 4 && benXeStr.length() == 4) {
                    	String maSoDi = soStr.substring(0, 2);
                    	String maSoDen = soStr.substring(2, 4);
                    	DictItem soDiItem = dictItemRepository.findByShortNameAndCollection(maSoDi, "GOVERNMENT");
                    	DictItem soDenItem = dictItemRepository.findByShortNameAndCollection(maSoDen, "GOVERNMENT");
                		CoreObjectSource soDiSource = new CoreObjectSource();
                		CoreObjectSource soDenSource = new CoreObjectSource();
                		
                		CoreObjectSource[] coquan_ql = new CoreObjectSource[2];
                    	if (soDiItem != null) {
                    		CoreObject soDiObject = new CoreObject();
                    		soDiObject.setShortName(soDiItem.getShortName());
                    		soDiObject.setTitle(soDiItem.getTitle());
                    		soDiSource.set_source(soDiObject);
                    		tuyen.setNoi_di(soDiSource);
                    		coquan_ql[0] = soDiSource;
                    	}
                    	if (soDenItem != null) {
                    		CoreObject soDenObject = new CoreObject();
                    		soDenObject.setShortName(soDenItem.getShortName());
                    		soDenObject.setTitle(soDenItem.getTitle());
                    		soDenSource.set_source(soDenObject);
                    		tuyen.setNoi_den(soDenSource);
                    		coquan_ql[1] = soDenSource;
                    	}
                    	tuyen.setCoquan_ql(coquan_ql);
                    	AccessRole[] arr1 = new AccessRole[3];
                    	arr1[0] = new AccessRole();
                    	arr1[0].setShortName(maSoDi + "_EDIT");
                    	Role soDiRole = roleRepository.findByShortName(maSoDi + "_EDIT");
                    	if (soDiRole != null) {
                    		arr1[0].setTitle(soDiRole.getTitle());
                    	}                    	
                    	arr1[0].setPermission("2");

                    	arr1[1] = new AccessRole();
                    	arr1[1].setShortName(maSoDen + "_EDIT");
                    	Role soDenRole = roleRepository.findByShortName(maSoDen + "_EDIT");
                    	if (soDenRole != null) {
                    		arr1[1].setTitle(soDenRole.getTitle());
                    	}                    	
                    	arr1[1].setPermission("2");

                    	arr1[2] = new AccessRole();
                    	arr1[2].setShortName("TCDB");
                    	Role tcdbRole = roleRepository.findByShortName("TCDB");
                    	if (tcdbRole != null) {
                    		arr1[2].setTitle(tcdbRole.getTitle());
                    	}                    	
                    	arr1[2].setPermission("1");

                    	tuyen.setAccessRoles(arr1);
                    	
                    	CoreObject loaiTuyenNode = new CoreObject();
                		LoaiTuyenSource loaiTuyen = new LoaiTuyenSource();
                    	
                    	if (maSoDi.contentEquals(maSoDen)) {                		
                    		loaiTuyenNode.setShortName("1");
                    		loaiTuyenNode.setTitle("Nội tỉnh");
                    		loaiTuyen.set_source(loaiTuyenNode);
                    		tuyen.setLoai_tuyen(loaiTuyen);
                    	}
                    	else {
                    		loaiTuyenNode.setShortName("2");
                    		loaiTuyenNode.setTitle("Liên tỉnh");
                    		loaiTuyen.set_source(loaiTuyenNode);
                    		tuyen.setLoai_tuyen(loaiTuyen);                		
                    	}
                    	
                	}
                	else {
                		tuyen.setGhichu("Mã tuyến sai quy tắc");
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("3");
                    	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);              		
                	}
                }
                else {
                	tuyen.setGhichu("Mã tuyến sai quy tắc");
                	tuyen.setStorage("review");
                	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                	CoreObject reviewObject = new CoreObject();
                	reviewObject.setShortName("3");
                	reviewObject.setTitle("Tuyến cần rà soát dữ liệu");
                	reviewStatusSource.set_source(reviewObject);
                	lstReviews.add(reviewStatusSource);                	
                }
                CoreObjectSource[] reviewStatus = new CoreObjectSource[lstReviews.size()];
                lstReviews.toArray(reviewStatus);
                tuyen.setReview_status(reviewStatus);
                
//                tuyenRepository.save(tuyen);
                if (mode != null && "checkduplicate".contentEquals(mode)) {
                	List<Tuyen> listTuyens = tuyenRepository.findByShortName(tuyen.getShortName());
                	if (listTuyens.size() > 0) {
                		for (Tuyen t : listTuyens) {
                			t.setStorage("review");
                        	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                        	CoreObject reviewObject = new CoreObject();
                        	reviewObject.setShortName("4");
                        	reviewObject.setTitle("Tuyến trùng mã");
                        	reviewStatusSource.set_source(reviewObject);
                        	CoreObjectSource[] arrReviews = new CoreObjectSource[t.getReview_status().length + 1];
                        	System.arraycopy(t.getReview_status(), 0, arrReviews, 0, t.getReview_status().length);
                        	arrReviews[t.getReview_status().length] = reviewStatusSource;
                        	t.setReview_status(arrReviews);
                        	
                        	tuyenRepository.save(t);
                		}
                		tuyen.setStorage("review");
                    	CoreObjectSource reviewStatusSource = new CoreObjectSource();
                    	CoreObject reviewObject = new CoreObject();
                    	reviewObject.setShortName("4");
                    	reviewObject.setTitle("Tuyến trùng mã");
                    	reviewStatusSource.set_source(reviewObject);
                    	lstReviews.add(reviewStatusSource);
                    	tuyenRepository.save(tuyen);
                	}                	
                }
                else {
                	Tuyen oldTuyen = tuyenRepository.findByShortNameAndStorage(tuyen.getShortName(), "regular");
                	if (oldTuyen != null) {
                		tuyen.setId(oldTuyen.getId());
                	}
                	tuyenRepository.save(tuyen);                	
                }
            	
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), message);
    }

    @PostMapping("/old/cuakhau")
    public UploadFileResponse importOldCuaKhau(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        String message = "";
        
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
                    node.put("openAccess", "1");
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
                file.getContentType(), file.getSize(), message);
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
        String message = "";
        
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
                    	DictItem tinhItem = dictItemRepository.findByShortNameAndCollection(valueCell.getStringCellValue(), "ADMINISTRATIVE_REGION");
                    	
                        ObjectNode tinhNode = mapper.createObjectNode();
                        tinhNode.put("shortName", valueCell.getStringCellValue());
                        if (tinhItem != null) {
                        	tinhNode.put("title", tinhItem.getTitle());
                        }
                        ObjectNode tinhSourceNode = mapper.createObjectNode();
                        tinhSourceNode.put("_source", tinhNode);
                        node.put("tinh", tinhSourceNode);
                    }
                    else if ("huyen".equals(key)) {
                    	DictItem huyenItem = dictItemRepository.findByShortNameAndCollection(valueCell.getStringCellValue(), "ADMINISTRATIVE_REGION");
                        ObjectNode huyenNode = mapper.createObjectNode();
                        huyenNode.put("shortName", valueCell.getStringCellValue());
                        if (huyenItem != null) {
                        	huyenNode.put("title", huyenItem.getTitle());
                        }
                        ObjectNode huyenSourceNode = mapper.createObjectNode();
                        huyenSourceNode.put("_source", huyenNode);
                        node.put("huyen", huyenSourceNode);
                    }
                    else if ("xa".equals(key)) {
                    	DictItem xaItem = dictItemRepository.findByShortNameAndCollection(valueCell.getStringCellValue(), "ADMINISTRATIVE_REGION");

                        ObjectNode xaNode = mapper.createObjectNode();
                        xaNode.put("shortName", valueCell.getStringCellValue());
                        if (xaItem != null) {
                        	xaNode.put("title", xaItem.getTitle());
                        }
                        ObjectNode xaSourceNode = mapper.createObjectNode();
                        xaSourceNode.put("_source", xaNode);
                        node.put("xa", xaSourceNode);
                    }

                    else if ("ngaycap_gcndkkd".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) {
                            try {
                                node.put("ngaycap_gcndkkd", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {

                            }                    		
                    	}
                    }
                    else if ("ngayhh_gcndkkd".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) { 
                            try {
                                node.put("ngayhh_gcndkkd", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {

                            }                    		
                    	}
                    }
                    else if ("loai_dn".equals(key)) {
                    	DictItem loaiDNItem = dictItemRepository.findByShortNameAndCollection(valueCell.getStringCellValue(), "LOAI_DN");

                        ObjectNode loaiDNNode = mapper.createObjectNode();
                        loaiDNNode.put("shortName", valueCell.getStringCellValue());
                        if (loaiDNItem != null) {
                        	loaiDNNode.put("title", loaiDNItem.getTitle());
                        }
                        ObjectNode loaiDNSourceNode = mapper.createObjectNode();
                        loaiDNSourceNode.put("_source", loaiDNNode);
                        node.put("loai_dn", loaiDNSourceNode);                    	
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
                    node.put("openAccess", "1");
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
//                    node.put("loai_dn", mapper.createObjectNode());
                }

                DoanhNghiep doanhNghiep = mapper.readValue(node.toString(), DoanhNghiep.class);
                DoanhNghiep oldDoanhNghiep = doanhNghiepRepository.findByMa_dn(doanhNghiep.getMa_dn());
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
                file.getContentType(), file.getSize(), message);
    }    
    
    @PostMapping("/old/phuongtien")
    public UploadFileResponse importOldPhuongTien(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "";
        
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
                    if ("nam_caitao".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) { 
                            try {
                                node.put("nam_caitao", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {

                            }                    		
                    	}
                    }
                    else if ("hieuxe".equals(key)) {
                        ObjectNode hieuXeSourceNode = mapper.createObjectNode();
                        ObjectNode hieuXeNode = mapper.createObjectNode();
                        String hieuXeCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	hieuXeCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	hieuXeCode = valueCell.getStringCellValue();
                        }
                        hieuXeNode.put("shortName", hieuXeCode);
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
                        String nuocSXCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	nuocSXCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	nuocSXCode = valueCell.getStringCellValue();
                        }
                        nuocSXNode.put("shortName", nuocSXCode);
                        DictItem nuocSx = dictItemRepository.findByShortNameAndCollection(nuocSXCode, "NATION");
                        if (nuocSx != null) {
                        	nuocSXNode.put("title", nuocSx.getTitle());
                        	nuocSXSource.put("_source", nuocSXNode);
                        	
                            node.put("nuoc_sx", nuocSXSource);                        	
                        }
                    }
                    else if ("mauson".equals(key)) {
                        ObjectNode mauSonNode = mapper.createObjectNode();
                        ObjectNode mauSonSource = mapper.createObjectNode();
                        
                        String mausonCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	mausonCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	mausonCode = valueCell.getStringCellValue();
                        }
                        mauSonNode.put("shortName", mausonCode);
                        DictItem mauSon = dictItemRepository.findByShortNameAndCollection(mausonCode, "MAUSON");
                        if (mauSon != null) {
                        	mauSonNode.put("title", mauSon.getTitle());
                        	mauSonSource.put("_source", mauSonNode);
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
//                    else if ("tenloai_pt".equals(key)) {
//                    	List<DictItem> listLoaiPts = dictItemRepository.findByTitle(valueCell.getStringCellValue(), "LOAIPT");
//                    	if (listLoaiPts.size() > 0) {
//                    		DictItem loaipt = listLoaiPts.get(0);
//                            ObjectNode loaiptSource = mapper.createObjectNode();
//                            ObjectNode loaiptNode = mapper.createObjectNode();
//
//                        	loaiptNode.put("shortName", loaipt.getShortName());
//                        	loaiptNode.put("title", loaipt.getTitle());
//                        		
//                        	loaiptSource.put("_source", loaiptNode);
//                        		
//                        	node.put("loai_pt", loaiptSource);
//                    	}
//                    }
                    else if ("loai_pt".equals(key)) {
                    	String loaiPtCode = "";
                    	if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                    		loaiPtCode = (int)valueCell.getNumericCellValue() + "";
                    	}
                    	else {
                    		loaiPtCode = valueCell.getStringCellValue();
                    	}
                    	DictItem loaiPtItem = dictItemRepository.findByShortNameAndCollection(loaiPtCode, "LOAIPT");
                    	
                    	if (loaiPtItem != null) {
                    		if (loaiPtItem.getLevel().contentEquals("1")) {
	                            ObjectNode loaiptSource = mapper.createObjectNode();
	                            ObjectNode loaiptNode = mapper.createObjectNode();
	
	                        	loaiptNode.put("shortName", loaiPtItem.getShortName());
	                        	loaiptNode.put("title", loaiPtItem.getTitle());
	                        		
	                        	loaiptSource.put("_source", loaiptNode);
	                        		
	                        	node.put("loai_pt", loaiptSource);                    			
                    		}
                    		else {
                            	DictItemSource parentLoaiItem = loaiPtItem.getParent();
                            	if (parentLoaiItem != null) {
    	                            ObjectNode loaiptSource = mapper.createObjectNode();
    	                            ObjectNode loaiptNode = mapper.createObjectNode();
    	
    	                        	loaiptNode.put("shortName", parentLoaiItem.get_source().getShortName());
    	                        	loaiptNode.put("title", parentLoaiItem.get_source().getTitle());
    	                        		
    	                        	loaiptSource.put("_source", loaiptNode);
    	                        		
    	                        	node.put("loai_pt", loaiptSource);
                            	}                    			
                    		}
                    	}
                    }
                    else if ("status".equals(key)) {
                        ObjectNode statusNode = mapper.createObjectNode();
                        ObjectNode statusSource = mapper.createObjectNode();
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            statusNode.put("shortName", (int)valueCell.getNumericCellValue() + "");                        	
                        }
                        else {
                            statusNode.put("shortName", valueCell.getStringCellValue());                        	
                        }
                        String statusCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            statusCode = (int)valueCell.getNumericCellValue() + "";                        	
                        }
                        else {
                            statusCode = valueCell.getStringCellValue();                        	
                        }
                        
                        DictItem statusItem = dictItemRepository.findByShortNameAndCollection(statusCode, "TRANGTHAI_PHUONGTIEN");
                        if (statusItem != null) {
                        	statusNode.put("title", statusItem.getTitle());
                        	statusSource.put("_source", statusNode);
                            node.put("status", statusSource);                        	
                        }
                    }
                    else if ("coquan_ql".equals(key)) {
                        ObjectNode coquanQLNode = mapper.createObjectNode();
                        ObjectNode coquanQLSource = mapper.createObjectNode();
                        
                        String coquanCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	coquanCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	coquanCode = valueCell.getStringCellValue();
                        }
                        DictItem coquanItem = dictItemRepository.findByShortNameAndCollection(coquanCode, "GOVERNMENT");
                        if (coquanItem != null) {
                            coquanQLNode.put("shortName", coquanItem.getShortName());
                        	coquanQLNode.put("title", coquanItem.getTitle());
                        	coquanQLSource.put("_source", coquanQLNode);
                            node.put("coquan_ql", coquanQLSource);                        	
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
                    node.put("openAccess", "1");
                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                    //node.put("doanh_nghiep", mapper.createObjectNode());
                    //node.put("loai_pt", mapper.createObjectNode());
//                    node.put("coquan_ql", mapper.createObjectNode());
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
                file.getContentType(), file.getSize(), message);
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
        String message = "";
        
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
                    else if ("status".contentEquals(key) && "LOAIPT".contentEquals(collectionCode)) {
                    	int status = (int)valueCell.getNumericCellValue();
                    	if (status == 0) {
                    		node.put("storage", "draft");
                    	}
                    	else {
                    		node.put("storage", "regular");
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
                if (!node.has("storage")) {
                    node.put("storage", "regular");                	
                }
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
                DictItem oldItem = dictItemRepository.findByShortNameAndCollection(dictItem.getShortName(), collectionCode);
                if (oldItem != null) {
                	dictItem.setId(oldItem.getId());
                }
                dictItemRepository.save(dictItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), message);
    }       
  
    @PostMapping("/excel/not")
    public UploadFileResponse importExcelNot(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        String message = "";
        
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
                    node.put("openAccess", "1");
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
                file.getContentType(), file.getSize(), message);
    }
    
    @PostMapping("/role")
    public UploadFileResponse importRole(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        String message = "";
        
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
                file.getContentType(), file.getSize(), message);
    }       
    
    @PostMapping("/old/bienhieuphuhieu")
    public UploadFileResponse importOldPhuHieuBienHieu(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "";
        
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
                    if ("createdAt".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) { 
                            try {
                                node.put("createdAt", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {

                            }                    		
                    	}
                    }
                    else if ("ngay_cap".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) { 
                            try {
                                node.put("ngay_cap", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {

                            }                    		
                    	}
                    }
                    else if ("ngay_hh".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) { 
                            try {
                                node.put("ngay_hh", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {

                            }                    		
                    	}
                    }
                    else if ("ngay_thuhoi".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) { 
                            try {
                                node.put("ngay_thuhoi", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {

                            }                    		
                    	}
                    }
                    else if ("la_bh".equals(key)) {
                    	int la_bh = (int)valueCell.getNumericCellValue();
                    	if (la_bh == 0) {
                    		node.put("la_bh", false);
                    	}
                    	else {
                    		node.put("la_bh", true);
                    	}
                    }
                    else if ("phuong_tien".equals(key)) {
                        ObjectNode phuongTienSourceNode = mapper.createObjectNode();
                        ObjectNode phuongTienNode = mapper.createObjectNode();
                        phuongTienNode.put("shortName", valueCell.getStringCellValue());
                        String bienkiemsoat = valueCell.getStringCellValue();
                        PhuongTien phuongTien = phuongTienRepository.findByShortName(bienkiemsoat);
                        if (phuongTien != null) {
                        	phuongTienNode.put("title", phuongTien.getTitle());
                            phuongTienSourceNode.put("_source", phuongTienNode);
                            node.put("phuong_tien", phuongTienSourceNode);                        	
                        }
                    }
                    else if ("tuyen".equals(key)) {
                    	ObjectNode tuyenSource = mapper.createObjectNode();
                        ObjectNode tuyenNode = mapper.createObjectNode();
                        if (!"".contentEquals(valueCell.getStringCellValue())) {
                            tuyenNode.put("shortName", valueCell.getStringCellValue());
                            String maTuyen = valueCell.getStringCellValue();
                            Tuyen tuyen = tuyenRepository.findByShortNameAndStorage(maTuyen, "regular");
                            if (tuyen != null) {
                            	tuyenNode.put("title", tuyen.getTitle());
                            	tuyenSource.put("_source", tuyenNode);
                            	
                                node.put("tuyen", tuyenSource);                        	
                            }                        	
                        }
                    }
                    else if ("loai_ph".equals(key)) {
                        ObjectNode loaiPhNode = mapper.createObjectNode();
                        ObjectNode loaiPhSource = mapper.createObjectNode();
                        String loaiPhCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	loaiPhCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	loaiPhCode = valueCell.getStringCellValue();
                        }
                        if (!"0".contentEquals(loaiPhCode)) {
                            loaiPhNode.put("shortName", loaiPhCode);
                            DictItem loaiPhItem = dictItemRepository.findByShortNameAndCollection(loaiPhCode, "LOAI_PH");
                            if (loaiPhItem != null) {
                            	loaiPhNode.put("title", loaiPhItem.getTitle());
                            	loaiPhSource.put("_source", loaiPhNode);
                                node.put("loai_ph", loaiPhSource);                        	
                            }                        	
                        }
                    }
                    else if ("loaihinh".equals(key)) {
                        ObjectNode loaiHinhNode = mapper.createObjectNode();
                        ObjectNode loaiHinhSource = mapper.createObjectNode();
                        
                        String loaiHinhCode = valueCell.getStringCellValue();
                        DictItem loaiHinhItem = dictItemRepository.findByShortNameAndCollection(loaiHinhCode, "LOAIHINH_BHPH");
                        if (loaiHinhItem != null) {
                            loaiHinhNode.put("shortName", valueCell.getStringCellValue());
                        	loaiHinhNode.put("title", loaiHinhItem.getTitle());
                        	loaiHinhSource.put("_source", loaiHinhNode);
                            node.put("loaihinh", loaiHinhSource);                        	
                        }
                    }
                    else if ("loai_bh".equals(key)) {
                        ObjectNode loaiHinhNode = mapper.createObjectNode();
                        ObjectNode loaiHinhSource = mapper.createObjectNode();
                        
                        String loaiHinhCode = valueCell.getStringCellValue();
                        DictItem loaiHinhItem = dictItemRepository.findByShortNameAndCollection(loaiHinhCode, "LOAIHINH_BHPH");
                        if (loaiHinhItem != null) {
                            loaiHinhNode.put("shortName", valueCell.getStringCellValue());
                        	loaiHinhNode.put("title", loaiHinhItem.getTitle());
                        	loaiHinhSource.put("_source", loaiHinhNode);
                            node.put("loaihinh", loaiHinhSource);                        	
                        }
                    }
                    else if ("status".equals(key)) {
                        ObjectNode statusNode = mapper.createObjectNode();
                        ObjectNode statusSource = mapper.createObjectNode();
                        
                        String statusCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	statusCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	statusCode = valueCell.getStringCellValue();
                        }
                        
                        DictItem statusItem = dictItemRepository.findByShortNameAndCollection(statusCode, "TRANGTHAI_BHPH");
                        if (statusItem != null) {
                            statusNode.put("shortName", statusCode);
                        	statusNode.put("title", statusItem.getTitle());
                        	statusSource.put("_source", statusNode);
                            node.put("status", statusSource);                        	
                        }
                    }
                    else if ("coquan_ql".equals(key)) {
                        ObjectNode coquanQLNode = mapper.createObjectNode();
                        ObjectNode coquanQLSource = mapper.createObjectNode();
                        
                        String coquanCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	coquanCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	coquanCode = valueCell.getStringCellValue();
                        }
                        DictItem coquanItem = dictItemRepository.findByShortNameAndCollection(coquanCode, "GOVERNMENT");
                        if (coquanItem != null) {
                            coquanQLNode.put("shortName", valueCell.getStringCellValue());
                        	coquanQLNode.put("title", coquanItem.getTitle());
                        	coquanQLSource.put("_source", coquanQLNode);
                            node.put("coquan_ql", coquanQLSource);                        	
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
                    node.put("openAccess", "1");
//                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                    //node.put("doanh_nghiep", mapper.createObjectNode());
                    //node.put("loai_pt", mapper.createObjectNode());
//                    node.put("coquan_ql", mapper.createObjectNode());
                }

                PhuHieuBienHieu phbh = mapper.readValue(node.toString(), PhuHieuBienHieu.class);
                PhuHieuBienHieu oldPhbh = phuHieuBienHieuRepository.findByShortName(phbh.getShortName());
                if (oldPhbh != null) {
                	phbh.setId(oldPhbh.getId());
                }
                phuHieuBienHieuRepository.save(phbh);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), message);
    }    


    @PostMapping("/old/giayphepdkkdvt")
    public UploadFileResponse importOldGiayPhepDKKDVT(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "";
        
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
                    if ("ngay_cap".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) {
                            try {
                                node.put("ngay_cap", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {
                            	e.printStackTrace();
                            }                    		
                    	}
                    }
                    else if ("ngay_hh".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) {
                            try {
                                node.put("ngay_hh", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {
                            	e.printStackTrace();
                            }                    		
                    	}
                    	else {
                    		System.out.println("Date cell error");
                    	}
                    }
                    else if ("ngay_thuhoi".equals(key)) {
                    	if (!"".contentEquals(valueCell.getStringCellValue())) {
                            try {
                                node.put("ngay_thuhoi", sdf.parse(valueCell.getStringCellValue()).getTime());
                            }
                            catch (Exception e) {
                            	e.printStackTrace();
                            }                    		
                    	}
                    }
                    else if ("doanh_nghiep".equals(key)) {
                        ObjectNode dnSourceNode = mapper.createObjectNode();
                        ObjectNode dnNode = mapper.createObjectNode();
                        String ma_dn = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	ma_dn = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	ma_dn = valueCell.getStringCellValue();
                        }
                        DoanhNghiep dn = doanhNghiepRepository.findByMa_dn(ma_dn);
                        if (dn != null) {
                            dnNode.put("shortName", dn.getShortName());
                        	dnNode.put("title", dn.getTitle());
                            dnSourceNode.put("_source", dnNode);
                            node.put("doanh_nghiep", dnSourceNode);                        	
                        }
                    }
                    else if ("loai".equals(key)) {
                        ObjectNode loaiNode = mapper.createObjectNode();
                        ObjectNode loaiSource = mapper.createObjectNode();
                        
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            loaiNode.put("shortName", (int)valueCell.getNumericCellValue() + "");                        	
                        }
                        else {
                            loaiNode.put("shortName", valueCell.getStringCellValue());                        	
                        }
                        String loaiCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            loaiCode = (int)valueCell.getNumericCellValue() + "";                        	
                        }
                        else {
                            loaiCode = valueCell.getStringCellValue();                        	
                        }
                        
                        DictItem loaiItem = dictItemRepository.findByShortNameAndCollection(loaiCode, "LOAI_GPDKKDVT");
                        if (loaiItem != null) {
                        	loaiNode.put("title", loaiItem.getTitle());
                        	loaiSource.put("_source", loaiNode);
                            node.put("loai", loaiSource);                        	
                        }
                    }
                    else if ("status".equals(key)) {
                        ObjectNode statusNode = mapper.createObjectNode();
                        ObjectNode statusSource = mapper.createObjectNode();
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            statusNode.put("shortName", (int)valueCell.getNumericCellValue() + "");                        	
                        }
                        else {
                            statusNode.put("shortName", valueCell.getStringCellValue());                        	
                        }
                        String statusCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                            statusCode = (int)valueCell.getNumericCellValue() + "";                        	
                        }
                        else {
                            statusCode = valueCell.getStringCellValue();                        	
                        }
                        
                        DictItem statusItem = dictItemRepository.findByShortNameAndCollection(statusCode, "TRANGTHAI_GP_DKKDVT");
                        if (statusItem != null) {
                        	statusNode.put("title", statusItem.getTitle());
                        	statusSource.put("_source", statusNode);
                            node.put("status", statusSource);                        	
                        }
                    }
                    else if ("coquan_ql".equals(key)) {
                        ObjectNode coquanQLNode = mapper.createObjectNode();
                        ObjectNode coquanQLSource = mapper.createObjectNode();
                        
                        String coquanCode = "";
                        if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                        	coquanCode = (int)valueCell.getNumericCellValue() + "";
                        }
                        else {
                        	coquanCode = valueCell.getStringCellValue();
                        }
                        DictItem coquanItem = dictItemRepository.findByShortNameAndCollection(coquanCode, "GOVERNMENT");
                        if (coquanItem != null) {
                            coquanQLNode.put("shortName", coquanCode);
                        	coquanQLNode.put("title", coquanItem.getTitle());
                        	coquanQLSource.put("_source", coquanQLNode);
                            node.put("coquan_ql", coquanQLSource);                        	
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
                    node.put("openAccess", "1");
//                    node.put("createdAt", (new Date().getTime()));
                    node.put("modifiedAt", (new Date().getTime()));
                    //node.put("doanh_nghiep", mapper.createObjectNode());
                    //node.put("loai_pt", mapper.createObjectNode());
//                    node.put("coquan_ql", mapper.createObjectNode());
                }

                GiayPhepDKKDVT giayPhepDKKDVT = mapper.readValue(node.toString(), GiayPhepDKKDVT.class);
                GiayPhepDKKDVT oldGiayPhep = giayPhepDKKDVTRepository.findByShortName(giayPhepDKKDVT.getShortName());
                if (oldGiayPhep != null) {
                	giayPhepDKKDVT.setId(oldGiayPhep.getId());
                }
                giayPhepDKKDVTRepository.save(giayPhepDKKDVT);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), message);
    }    
    
    @PostMapping("/old/gantuyen")
    public UploadFileResponse importGanTuyen(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Map<String, Integer> mapColumns = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = "";
        
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
                Cell tuyenCell = currentRow.getCell(0);
                Cell doanhNghiepCell = currentRow.getCell(1);
                String maTuyen = "";
                if (tuyenCell.getCellTypeEnum() == CellType.NUMERIC) {
                	maTuyen = (int)tuyenCell.getNumericCellValue() + "";
                }
                else {
                	maTuyen = tuyenCell.getStringCellValue();
                }
                String ma_dn = "";
                if (doanhNghiepCell.getCellTypeEnum() == CellType.NUMERIC) {
                	ma_dn = (int)doanhNghiepCell.getNumericCellValue() + "";
                }
                else {
                	ma_dn = doanhNghiepCell.getStringCellValue();
                }
                
                
                List<Tuyen> tuyens = tuyenRepository.findByShortName(maTuyen);
                for (Tuyen t : tuyens) {
                	DoanhNghiepSource[] doanh_nghiep = t.getDoanh_nghiep();
                	DoanhNghiepSource[] new_doanh_nghiep = (doanh_nghiep != null) ? new DoanhNghiepSource[doanh_nghiep.length + 1] : new DoanhNghiepSource[1];
                	if (doanh_nghiep != null)
                		System.arraycopy(doanh_nghiep, 0, new_doanh_nghiep, 0, doanh_nghiep.length);
                	DoanhNghiep dn = doanhNghiepRepository.findByMa_dn(ma_dn);
                	if (dn != null) {
                		DoanhNghiepSource dnSource = new DoanhNghiepSource();
                		DoanhNghiepObject dnNode = new DoanhNghiepObject();
                		dnNode.setShortName(dn.getShortName());
                		dnNode.setTitle(dn.getTitle());
                		dnNode.setMa_dn(dn.getMa_dn());
                		dnNode.setTen_dn(dn.getTen_dn());
                		dnNode.setSo_gcndkkd(dn.getSo_gcndkkd());
                		dnNode.setMs_thue(dn.getMs_thue());
                		
                		dnSource.set_source(dnNode);
                		new_doanh_nghiep[new_doanh_nghiep.length - 1] = dnSource;
                		t.setDoanh_nghiep(new_doanh_nghiep);
                		
                		tuyenRepository.save(t);
                	}
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), message);
    }       
}
