package com.gz.edu.service.log;

import com.gz.edu.exception.ServiceException;

public interface RecordLogService {

    public void record(String remark) throws ServiceException;

    public void delete() throws ServiceException;

}
