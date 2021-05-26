package com.cqjtu.dpta.dao.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/21
 */
@Data
@Document("visits")
public class Visits {

    @Id
    private String id;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime date;
    private Long shopId;
    private String userId;

    @Indexed
    private Long distrId;

}
