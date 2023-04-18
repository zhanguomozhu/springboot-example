package com.dyq.springboot.oss.utils;

import lombok.Data;

@Data
public class ObjectItem {
    private String objectName;
    private Long size;
    private int  isDelete;
}
