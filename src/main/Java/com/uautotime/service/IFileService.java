package com.uautotime.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yaosheng on 2019/5/10.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

}
