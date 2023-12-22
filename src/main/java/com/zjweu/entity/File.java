package com.zjweu.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String filename;

    private Float size;

    private Integer type;

    private Integer userId;

    private String md5;

    private Integer directoryId;

    private LocalDateTime uploadTime;

    private LocalDateTime modifyTime;

    private Boolean isDeleted;

    private LocalDateTime deleteTime;


}
