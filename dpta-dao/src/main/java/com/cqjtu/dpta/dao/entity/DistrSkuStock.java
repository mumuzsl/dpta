package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mumu
 * @since 2021-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_distr_sku_stock")
public class DistrSkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long skuId;

    private Long commId;

    private Integer stock;


}
