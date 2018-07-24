package com.optimus.common.excel;

import com.optimus.common.exception.BizException;
import com.optimus.utils.Utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Workbook 构造工具
 * Created on 2017/12/8 0008.
 */
public class ExcelBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ExcelBuilder.class);
    private static final String XLS_SUFFIX = "xls";
    private static final String XLSX_SUFFIX = "xlsx";

    public static Workbook build(InputStream inputIo){
        try {
            Workbook wb;
            try {
                wb = new XSSFWorkbook(inputIo);
            }catch (Exception e){
                //2003 以前的
                wb = new HSSFWorkbook(inputIo);
            }
            return wb;
        }catch (Exception e){
            logger.error("excel导入失败,异常信息:{}",e.getMessage(),e);
            throw new BizException("excel导入失败:"+e.getMessage());
        }
    }

    public static Workbook build(File file){
        if(file == null){
            throw new NullPointerException("File can not be null");
        }
        if(!file.exists()){
            throw new BizException("文件不存在!");
        }
        if(file.isDirectory()){
            throw new BizException("参数是目录,需要文件");
        }
        if(!file.canRead()){
            try {
                file.setReadable(true);
            }catch (Exception e){
                throw new BizException("文件不可读",e);
            }
        }
        try {
            String fileName = file.getName();
            if(fileName.endsWith(XLS_SUFFIX) || fileName.endsWith(XLSX_SUFFIX)){
                throw new BizException("不支持的文件类型");
            }
            Workbook wb;
            try {
                wb = new XSSFWorkbook(file);
            }catch (Exception e){
                //2003 以前的
                wb = new HSSFWorkbook(new FileInputStream(file));
            }
            return wb;
        }catch (Exception e){
            logger.error("excel导入失败,异常信息:{}",e.getMessage(),e);
            throw new BizException("excel导入失败:"+e.getMessage());
        }
    }

    public static Workbook build(String path){
        if(Utils.isEmpty(path)){
            throw new BizException("文件路径不能为空");
        }
        return build(new File(path));
    }
}
