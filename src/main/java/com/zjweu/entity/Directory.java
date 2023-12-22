package com.zjweu.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author swithin
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Directory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String directoryName;

    /**
     * 属于哪个用户
     */
    private Integer userId;

    /**
     * 上一级根目录的id，如果为null，那么就是根目录
     */
    private Integer fatherRoot;

    private LocalDateTime uploadTime;

    private LocalDateTime modifyTime;

    private Boolean isDeleted;

    private LocalDateTime deleteTime;


}
