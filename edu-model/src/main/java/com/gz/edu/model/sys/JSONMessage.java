package com.gz.edu.model.sys;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JSONMessage implements Serializable {

    public static final String SUCCESS = "1";

    public static final String FAIL = "0";

    private String flag;

    private String msg;

}
