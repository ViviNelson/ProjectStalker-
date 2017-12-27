package com.appspot.blacktenure188118.telegram.model.request;

import java.io.Serializable;

/**
 * Stas Parshin
 * 06 May 2016
 */
public class InlineQueryResultCachedMpeg4Gif extends InlineQueryResult<InlineQueryResultCachedMpeg4Gif> implements Serializable {
    private final static long serialVersionUID = 0L;

    private String mpeg4_file_id;

    private String title;
    private String caption;


    public InlineQueryResultCachedMpeg4Gif(String id, String fileId) {
        super("mpeg4_gif", id);
        this.mpeg4_file_id = fileId;
    }

    public InlineQueryResultCachedMpeg4Gif title(String title) {
        this.title = title;
        return this;
    }

    public InlineQueryResultCachedMpeg4Gif caption(String caption) {
        this.caption = caption;
        return this;
    }
}
