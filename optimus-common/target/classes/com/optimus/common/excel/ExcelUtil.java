package com.optimus.common.excel;

import com.optimus.common.excel.annotation.Column;
import com.optimus.common.excel.annotation.ExcelEntity;
import com.optimus.common.exception.BizException;
import com.optimus.utils.FormatUtil;
import com.optimus.utils.Utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static final int DEFAULT_SHEET_SIZE = 65535;

    /**
     * 获取excel 模板
     * @param cls 模板对应的Class(被{@link Column}注解)
     * @param outputIo 输出流(web:通过HttpServletResponse 获得)
     */
    public static void excelTemplate(Class<?> cls,OutputStream outputIo){
        if(Utils.isNull(cls)){
            throw new BizException("cls 不能为null");
        }
        if(Utils.isNull(outputIo)){
            throw new BizException("outputIo 不能为null");
        }
        if(!cls.isAnnotationPresent(ExcelEntity.class)){
            throw new BizException("class未找到注解 ExcelEntity:"+ExcelEntity.class.getName());
        }
        List<Field> validField = resolveField(cls);
        validateFieldConfig(validField);
        LinkedHashMap<String,String> fieldName = new LinkedHashMap<>();
        for (Field field : validField){
            Column attr = field.getAnnotation(Column.class);
            String title = attr.title();
            fieldName.put(String.valueOf(attr.order()),title);
        }
        listToExcel(Collections.emptyList(),fieldName,"模板",outputIo);
    }

    public static <T> List<T> excelToList(Class<T> cls, File file){
        if(Utils.isNull(cls)){
            throw new BizException("cls 不能为null");
        }
        if(Utils.isNull(file)){
            throw new BizException("file 不能为null");
        }
        if(!cls.isAnnotationPresent(ExcelEntity.class)){
            throw new BizException("class未找到注解 ExcelEntity:"+ExcelEntity.class.getName());
        }
        List<T> result = new ArrayList<>();
        try {
            Workbook workbook = ExcelBuilder.build(file);
            resolveWorkBook(cls, result, workbook);
            return result;
        }catch (Exception e){
            logger.error("导入excel数据失败:{}",e.getMessage());
            throw new BizException(e);
        }
    }

    public static <T> List<T> excelToList(Class<T> cls, InputStream inputIo){
        return handle(cls, inputIo);
    }

    /**
     * 数据导出到excel
     * @param data 数据
     * @param fieldName 列标题映射
     * @param sheetName sheet名称
     * @param writeIo 输出流
     * @param <T> 数据类型
     */
    public static <T> void listToExcel(List<T> data, LinkedHashMap<String,String> fieldName, String sheetName, OutputStream writeIo){
        listToExcel(data,fieldName,sheetName,DEFAULT_SHEET_SIZE,writeIo);
    }

    /**
     * @param data 数据
     * @param fieldName 列标题映射
     * @param sheetName sheet名称
     * @param sheetSize sheetSize 最大行数限制(默认65535)
     * @param writeIo 输出流
     * @param <T> 数据类型
     */
    public static <T> void listToExcel(List<T> data, LinkedHashMap<String,String> fieldName, String sheetName,int sheetSize, OutputStream writeIo){
        //数据可以为空(此时导出的只有标题,可充当模板)
        if(data == null){
            throw new BizException("导出的数据不能为null");
        }
        if(fieldName == null || fieldName.isEmpty()){
            logger.info("【excel中文列标题未设置,将默认采用对象属性名】");
        }
        if(Utils.isEmpty(sheetName)){
            sheetName = "sheet";
        }
        if(sheetSize <= 0){
            sheetSize = DEFAULT_SHEET_SIZE;
        }

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            SXSSFWorkbook wb = new SXSSFWorkbook(xssfWorkbook);

            //BigDecimal类型的数据格式化
            CellStyle decimalCellStyle = wb.createCellStyle();
            DataFormat df = wb.createDataFormat();
            decimalCellStyle.setDataFormat(df.getFormat("#,#0.00"));

            int dataSize = data.size();
            //计算需要多少个sheet
            int count = (dataSize/sheetSize)+1;
            if(count == 1) {
                fillSheet(data,fieldName,sheetName,wb,decimalCellStyle);
            } else {
                for (int i = 1;i <= count;i++){
                    int start = (i - 1) * DEFAULT_SHEET_SIZE;
                    int end = i == count ? dataSize : (start + DEFAULT_SHEET_SIZE);
                    fillSheet(data.subList(start,end),fieldName,sheetName+i,wb,decimalCellStyle);
                }
            }

            wb.write(writeIo);
            wb.close();
            writeIo.close();
        }catch (Exception e){
            logger.error("【导出excel失败】--【错误信息:{}】",e.getMessage());
            throw new BizException("导出excel失败");
        }
    }

    private static <T> void fillSheet(List<T> data, LinkedHashMap<String,String> fieldName, String sheetName,SXSSFWorkbook wb,CellStyle decimalCellStyle) throws Exception {
        SXSSFSheet sheet = wb.createSheet(sheetName);
        //列标题 样式(居中)
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        int columnSize = 0;
        Row head = sheet.createRow(0);
        for (Map.Entry<String,String> entry : fieldName.entrySet()){
            Cell cell = head.createCell(columnSize++, CellType.STRING);
            cell.setCellValue(entry.getValue());
            cell.setCellStyle(cellStyle);
        }

        //填充数据
        int rowOrder = 1;
        for(T itemData : data){
            int cellOrder = 0;
            Class cls = itemData.getClass();
            Set<String> fields = fieldName.keySet();
            Row row = sheet.createRow(rowOrder++);
            for(String str : fields){
                Field field = cls.getDeclaredField(str);
                ReflectionUtils.makeAccessible(field);
                Class ty = field.getType();
                if(ty == BigDecimal.class){
                    BigDecimal valNum = (BigDecimal) ReflectionUtils.getField(field,itemData);
                    Cell cell = row.createCell(cellOrder++,CellType.NUMERIC);
                    cell.setCellStyle(decimalCellStyle);
                    cell.setCellValue(valNum.doubleValue());
                }else {
                    String val = resolveJavaType(ReflectionUtils.getField(field,itemData));
                    row.createCell(cellOrder++,CellType.STRING).setCellValue(val);
                }
            }
        }

        //设置 自适应列宽
        sheet.trackAllColumnsForAutoSizing();
        for(;columnSize >= 0;columnSize--){
            sheet.autoSizeColumn(columnSize);
        }
    }

    private static <T> void readSheet(Sheet sheet, List<Field> validField, Class<T> cls, List<T> result) {
        int lastSize = sheet.getLastRowNum();
        for(int i = 1;i <= lastSize;i++){
            Row row = sheet.getRow(i);
            if(row == null){continue;}
            readRow(row,validField,cls,result);
        }
    }

    private static <T> void readRow(Row row, List<Field> validField, Class<T> cls, List<T> result) {
        //以类属性数量为准(即为excel模板)
        int index = 0;
        try {
            T ins = cls.newInstance();
            int nullFieldCount = 0;
            for(Field field : validField){
                ReflectionUtils.makeAccessible(field);
                Cell cell = row.getCell(index++);
                if(cell == null || cell.getCellTypeEnum() == CellType.BLANK){
                    nullFieldCount++;
                    continue;
                }

                CellType cellType = cell.getCellTypeEnum();
                switch (cellType){
                    case STRING:
                        resolveStringField(ins,field,cell);
                        break;
                    case NUMERIC:
                        resolveNumericField(ins,field,cell);
                        break;
                    case BOOLEAN:
                        resolveBooleanField(ins,field,cell);
                        break;
                    case ERROR:
                        resolveNoneField(ins,field,cell);
                        break;
                    default:
                        resolveDefaultField(ins,field,cell);
                }
            }

            //避免添加空行
            if(nullFieldCount < index){
                result.add(ins);
            }
        }catch (Exception e){
            logger.error("excel导入失败,异常信息:{}",e.getMessage(),e);
            throw new BizException("excel导入失败:"+e.getMessage());
        }

    }

    private static <T> void resolveDefaultField(T ins, Field field, Cell cell) throws Exception {
        Class tp = field.getType();
        Object val;
        if(tp == Date.class){
            val = cell.getDateCellValue();
        }else {
            val = cell.getStringCellValue();
        }

        field.set(ins,val);
    }

    private static <T> void resolveNoneField(T ins, Field field, Cell cell) throws Exception {
        byte val = cell.getErrorCellValue();
        if(field.getType() == Byte.class){
            field.set(ins,val);
        }
    }

    private static <T> void resolveBooleanField(T ins, Field field, Cell cell) throws Exception {
        boolean val = cell.getBooleanCellValue();
        field.set(ins,val);
    }

    private static <T> void resolveNumericField(T ins, Field field, Cell cell) throws Exception {
        double value = cell.getNumericCellValue();
        Class tp = field.getType();
        if(tp == BigDecimal.class){
            field.set(ins,BigDecimal.valueOf(value));
        } else if (tp == Long.class){
            field.set(ins,Double.valueOf(value).longValue());
        } else if (tp == Integer.class){
            field.set(ins,Double.valueOf(value).intValue());
        } else if (tp == Short.class){
            field.set(ins,Double.valueOf(value).shortValue());
        } else if (tp == Byte.class){
            field.set(ins,Double.valueOf(value).byteValue());
        } else {
            throw new BizException("不支持的数据格式,请修改excel数据["+tp.getName()+"]");
        }
    }

    private static <T> void resolveStringField(T ins, Field field, Cell cell) throws Exception {
        String val = cell.getStringCellValue();
        Class tp = field.getType();
        if(Number.class.isAssignableFrom(tp)){
            Double doubleVal = Double.valueOf(val);
            if(tp == BigDecimal.class){
                field.set(ins,BigDecimal.valueOf(doubleVal));
            } else if (tp == Long.class){
                field.set(ins,doubleVal.longValue());
            } else if (tp == Integer.class){
                field.set(ins,doubleVal.intValue());
            } else if (tp == Short.class){
                field.set(ins,doubleVal.shortValue());
            } else if (tp == Byte.class){
                field.set(ins,doubleVal.byteValue());
            }
        } else if(tp == Date.class){
            Date date = FormatUtil.parseDate(val);
            field.set(ins,date);
        } else if(tp == Boolean.class){
            field.set(ins,Boolean.valueOf(val));
        } else {
            field.set(ins,val);
        }
    }

    private static List<Field> resolveField(Class cls){
        Field[] fields = cls.getDeclaredFields();
        List<Field> validField = new ArrayList<>(fields.length);
        for(Field field : fields){
            if(!field.isAnnotationPresent(Column.class)){continue;}
            Column attr = field.getAnnotation(Column.class);
            int order = attr.order();
            if(order < 0){
                throw new BizException(Column.class.getName()+"【order】 必须大于0");
            }
            if(Utils.isEmpty(attr.title())){
                throw new BizException(Column.class.getName()+"【title】 不能为空");
            }
            validField.add(field);
        }
        validField.sort((o1,o2) -> {
            int order1 = o1.getAnnotation(Column.class).order();
            int order2 = o2.getAnnotation(Column.class).order();
            return order1-order2;
        });
        return validField;
    }

    private static void validateFieldConfig(List<Field> validField) {
        Map<Integer,Integer> orderMap = new LinkedHashMap<>();
        for(Field field :validField){
            Column attr = field.getAnnotation(Column.class);
            int order = attr.order();
            if(orderMap.containsKey(order)){
                throw new BizException("Column order:["+order+"]重复");
            }
            orderMap.put(order,1);
        }
        orderMap.clear();
    }

    private static <T> void resolveWorkBook(Class<T> cls, List<T> result, Workbook workbook) throws IOException {
        List<Field> validFields = resolveField(cls);
        validateFieldConfig(validFields);
        int sheetSize = workbook.getNumberOfSheets();
        for(int i = 0;i < sheetSize;i++){
            Sheet sheet = workbook.getSheetAt(i);
            if(sheet == null){continue;}
            readSheet(sheet,validFields,cls,result);
        }
        workbook.close();
    }

    private static <T> List<T> handle(Class<T> cls, InputStream inputIo) {
        if(Utils.isNull(cls)){
            throw new BizException("cls 不能为null");
        }
        if(Utils.isNull(inputIo)){
            throw new BizException("inputIo 不能为null");
        }
        if(!cls.isAnnotationPresent(ExcelEntity.class)){
            throw new BizException("class未找到注解 ExcelEntity:"+ExcelEntity.class.getName());
        }
        List<T> result = new ArrayList<>();
        try {
            Workbook workbook = ExcelBuilder.build(inputIo);
            List<Field> validField = resolveField(cls);
            validateFieldConfig(validField);
            int sheetSize = workbook.getNumberOfSheets();
            for(int i = 0;i < sheetSize;i++){
                Sheet sheet = workbook.getSheetAt(i);
                if(sheet == null){continue;}
                readSheet(sheet,validField,cls,result);
            }
            workbook.close();
            return result;
        }catch (Exception e){
            logger.error("导入excel数据失败:{}",e.getMessage());
            throw new BizException(e);
        }
    }

    private static String resolveJavaType(Object val) {

        if(val == null){
            return "";
        }else if(val instanceof Number){
            return val.toString();
        } else if(val instanceof Date){
            return null;//FormatUtil.format((Date) val);
        } else if(val instanceof Boolean){
            return val.toString();
        } else if(val instanceof String){
            return (String) val;
        } else if(val.getClass().isArray()){
            throw new BizException("数据类型只支持java基本类型及其包装类型");
        } else {
            throw new BizException("数据类型只支持java基本类型及其包装类型");
        }
    }
}
