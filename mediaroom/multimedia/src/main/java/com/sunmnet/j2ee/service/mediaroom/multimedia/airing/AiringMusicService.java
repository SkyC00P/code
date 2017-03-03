package com.sunmnet.j2ee.service.mediaroom.multimedia.airing;

import com.sunmnet.j2ee.entity.mediaroom.multimedia.airing.AiringMusic;
import com.sunmnet.j2ee.service.mediaroom.common.IBaseService;

import java.util.List;

/*
 * 代码生成工具自动生成
 * wanghz@sunmnet.com
 *
 */
public interface AiringMusicService extends IBaseService<AiringMusic, Long> {

    List<AiringMusic> getAllMusic();
}
