package com.thd.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class BaseDto implements Serializable {
    protected UUID id;
    protected Boolean voided;
}
