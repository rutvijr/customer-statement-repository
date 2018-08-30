package com.rabo.customerstatementprocessor.util;

import org.apache.commons.lang3.StringUtils;

import com.rabo.customerstatementprocessor.constant.FileTypeConstants;

/**
 * This class contains useful methods to work with mimeTypes, including
 * string representations of mime types (such as text/csv).
 * 
 * @author rutvijr@gmail.com
 *
 */
public class MimeTypeUtils
{
    private MimeTypeUtils()
    {
        // Hiding the public constructor
    }

    /**
     * It returns the correct subtype of the given MIME type.
     *
     * @param mimeType the mime type as a string
     * @return the subtype
     */
    public static String getSubTypeOf(String mimeType)
    {
        int index = mimeType.indexOf('/');
        String subType = mimeType.substring(index + 1);
        // Windows sets the content type as vnd.ms-excel.
        // The standard MIME type for CSV is text/csv
        if (StringUtils.contains(subType, FileTypeConstants.EXCEL))
        {
            return FileTypeConstants.CSV;
        }
        else
        {
            return subType;
        }
    }
}
