package com.cqjtu.dpta.dao.dto;

import com.cqjtu.dpta.dao.entity.DealD;
import com.cqjtu.dpta.dao.entity.PafComm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * author: mumu
 * date: 2021/5/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DealDDto extends DealD {
    private PafComm pafComm;
}
