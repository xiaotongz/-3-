package com.zjweu.entity;


import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author swithin
 * @since 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "用户类",description = "用户的相关信息")
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String salt;

    private String username;

    private String passwordHash;

    private String email;

    private String phoneNumber;

    private LocalDateTime registrationTime;

    private String userRole;

    private String avatarPath;

    private Long totalStorage;

    private Long usedStorage;

    private Boolean isActivated;


}
