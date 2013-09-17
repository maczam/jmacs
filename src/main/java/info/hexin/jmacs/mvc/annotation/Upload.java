package info.hexin.jmacs.mvc.annotation;

import info.hexin.jmacs.mvc.handler.upload.ProgressListener;

/**
 * 上传配置
 * 
 * @author ydhexin@gmail.com
 * 
 */
public @interface Upload {

    public int bufferdSize() default 812000;

    /**
     * 最大上传文件限制
     * 
     * @return
     */
    public int maxSize() default Integer.MAX_VALUE;

    /**
     * 禁止文件名称
     * 
     * @return
     */
    public String forbid() default "";

    /**
     * 临时文件存放地址
     * 
     * @return
     */
    public String tmpPath() default "~/update";

    /**
     * 默认将文件存放的位置
     * 
     * @return
     */
    public UploadType type() default UploadType.MEMORY;
}
