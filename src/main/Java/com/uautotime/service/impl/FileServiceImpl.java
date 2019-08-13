package com.uautotime.service.impl;

import com.google.common.collect.Lists;
import com.uautotime.util.FTPUtil;
import org.slf4j.Logger;
import com.uautotime.service.IFileService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by yaosheng on 2019/5/10.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);             //获取文件扩展名
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;             //使用UUID防止文件重名
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File((path));
        if(!fileDir.exists()){                          //exists()判断文件是否存在
            fileDir.setWritable(true);                  //赋予文件可写的权限
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功
            //todo 将targetFile上传到我们的FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到FTP服务器
            //todo 上传完成之后，删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        //A:abc.jpg
        //B:abc.jpg

        return targetFile.getName();
    }

}
