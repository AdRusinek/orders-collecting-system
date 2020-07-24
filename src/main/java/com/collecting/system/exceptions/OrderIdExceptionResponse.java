package com.collecting.system.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class OrderIdExceptionResponse {

    private String orderId;
}
