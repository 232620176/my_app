package com.hydra.core.fileservice.interfaces;

import java.util.Date;

public interface IFilePath {
    /**
     * @param
     * @return
     */
    public String getLocalFileDir(Date date);

    /**
     * @param date
     * @return
     */
    public String getLocalFileName(Date date);

    /**
     * @return
     */
    public String getLocalAbsFilePath(Date date);

    /**
     * @param date
     * @return
     */
    public String getRemoteFileDir(Date date);

    /**
     * @param date
     * @return
     */
    public String getRemoteFileName(Date date);

    /**
     * @return
     */
    public String getRemoteAbsFilePath(Date date);
}
