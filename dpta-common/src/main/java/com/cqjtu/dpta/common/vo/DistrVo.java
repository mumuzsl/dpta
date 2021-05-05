package com.cqjtu.dpta.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * author: mumu
 * date: 2021/5/4
 */
@Data
public class DistrVo {

    private Long distrId;

    private String distrNm;

    private Integer state;

    private Long levelId;

    private String phone;

}
