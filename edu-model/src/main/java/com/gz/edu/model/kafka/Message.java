package com.gz.edu.model.kafka;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Message implements Serializable {

    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳

}
