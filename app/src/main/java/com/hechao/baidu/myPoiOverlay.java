package com.hechao.baidu;


import com.baidu.mapapi.map.BaiduMap;

//        针对检索功能模块（POI检索、线路规划等），地图SDK还对外提供相应的覆盖物来快速展示结果信息。这些方法都是开源的，开发者可根据自己的实际去求来做个性化的定制。
//        利用检索结果覆盖物展示POI搜索结果的方式如下：
//        第一步，构造自定义 PoiOverlay 类；
public class MyPoiOverlay extends PoiOverlay {
    public MyPoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public boolean onPoiClick(int index) {
        super.onPoiClick(index);
        return true;
    }


}