package com.fjnu.assetsManagement.module.uploadFile.Action;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Data;
import org.apache.struts2.convention.annotation.Action;

import java.io.File;

@Data
public class UploadFileAction extends ActionSupport {
    private File doc;
    private String docFileName;
    private String docContentType;

    @Action(value = "/upload")
    public String upload() {
        System.out.println(doc);
        System.out.println(docFileName);
        System.out.println(docContentType);
        return SUCCESS;
    }


}
