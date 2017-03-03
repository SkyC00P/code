package com.sunmnet.j2ee.utils;

import com.sunmnet.j2ee.bean.pub.action.JsonResult;

/**
 * ResultUtil
 *
 * @author : skyco
 * @date : 2017/3/3
 */
public class ResultUtil {
    public static final JsonResult SUC = new JsonResult(true);
    public static final JsonResult FAIL = new JsonResult(false);
}
