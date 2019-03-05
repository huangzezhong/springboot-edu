package com.gz.edu.service.log.impl;

import com.gz.edu.exception.ServiceException;
import com.gz.edu.service.log.RecordLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecordLogServiceImpl implements RecordLogService {

    @Override
    public void record(String remark) throws ServiceException {
        log.info("记录日志，{}", remark);
    }

    @Override
    public void delete() throws ServiceException {
        log.info("删除日志");
    }
}
