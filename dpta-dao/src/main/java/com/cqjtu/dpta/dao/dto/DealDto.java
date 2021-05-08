package com.cqjtu.dpta.dao.dto;

import com.cqjtu.dpta.dao.entity.Deal;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * author: mumu
 * date: 2021/5/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DealDto extends Deal {
    List<DealDDto> details;
}
