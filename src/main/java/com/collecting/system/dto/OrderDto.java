package com.collecting.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto implements Serializable {

    static final long serialVersionUID = -2304915816816629963L;

    @Null
    private Long id;

    @NotBlank
    private String orderName;

    @NotBlank
    private String customerFullName;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private Timestamp dateOfPurchase;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private Timestamp lastModifiedDate;

}