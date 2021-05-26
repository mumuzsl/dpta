package com.cqjtu.dpta.web.support.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import org.springframework.stereotype.Component;

/**
 * author: mumu
 * date: 2021/5/12
 */
@Component
public class StateConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return OrderState.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(OrderState.valueOf(value).label());
    }
}
