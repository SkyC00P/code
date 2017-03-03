package com.sunmnet.j2ee.service.mediaroom.multimedia.impl.airing;

import com.sunmnet.j2ee.core.pub.sqlloader.SqlLoader;
import com.sunmnet.j2ee.entity.mediaroom.multimedia.airing.AiringMusic;
import com.sunmnet.j2ee.service.mediaroom.multimedia.airing.AiringMusicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sunmnet.j2ee.service.mediaroom.common.impl.IBaseServiceImpl;

import java.util.List;

/*
 * 代码生成工具自动生成
 * wanghz@sunmnet.com
 *
 */
@Service("/mediaroom/multimedia/airingMusicService")
public class AiringMusicServiceImpl extends IBaseServiceImpl<AiringMusic, Long> implements
        AiringMusicService {

    private static final String CLASS_NAME = AiringMusicServiceImpl.class.getName();

    /**
     * log4j2日志
     **/
    private static final Logger LOG = LogManager.getLogger(CLASS_NAME);

    private static final String SELECT_SQL = SqlLoader.getInstance().getSql(CLASS_NAME, "SELECT_SQL");


    @Override
    public List<AiringMusic> getAllMusic() {
        return findVoBySql(SELECT_SQL);
    }
}
