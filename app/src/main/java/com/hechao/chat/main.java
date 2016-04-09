package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.TransitRouteLine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2016/4/9.
 */

public class main extends Activity {

    //获取地图控件引用
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    private Marker marker = null;
    private Marker mMarkerD = null;
    private TransitRouteLine route = null;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    StationData stationData = new StationData();
    private LatLng point = null;
    boolean isFirstLoc = true;

    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
    BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
    BitmapDescriptor bitmap3 = BitmapDescriptorFactory.fromResource(R.drawable.icon_markc);
    private TextView textView;

    @InjectView(R.id.myrun)
    ImageButton myrun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ButterKnife.inject(main.this);
        //RongIM登录
        connect();
        mMapView = (MapView) findViewById(R.id.bmapView);
//        设置地图形式
        setMapType();
        initLocation();
//        LocationClient定位配置
//        initLocationClient();
//        initTimer();
        //加油站
//        initStationdata();

// 将底图标注设置为隐藏，方法如下：
//        mBaiduMap.showMapPoi(false);


        //周边
//        initAround();


    }






    /**
     * RongIM登录
     */
    private void connect() {
        String token = App.token;
        if (App.isLogin) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
//                    Toast.makeText(MainActivity.this, "no success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(String s) {
                    Toast.makeText(main.this, "成功登陆", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {


//                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });


//            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//                @Override
//                public UserInfo getUserInfo(String s) {
//                    UserInfo userInfo = new UserInfo(s, s, Uri.parse("http://img.ltn.com.tw/Upload/ent/page/800/2015/04/03/phpHOkTWG.jpg"));
//                    return userInfo;
//                }
//            }, false);

        }
    }








    @OnClick(R.id.start)
    void start(){
//        LocationClient定位配置
        initLocationClient();
        initTimer();
    }


    @OnClick(R.id.myrun)
    void setMyrun() {

        Intent intent= new Intent(main.this,Myrun.class) ;
        startActivity(intent);

    }


    /**
     * 设置地图形式
     */

    private void setMapType() {
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();

//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
//        mBaiduMap.setMyLocationEnabled(true);

        //普通地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //卫星地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

        //开启交通图
//        mBaiduMap.setTrafficEnabled(true);

        //开启城市交通热力图
//        mBaiduMap.setBaiduHeatMapEnabled(true);
    }



    @OnClick(R.id.friend)
    void friend(){
        Intent intent= new Intent(main.this,FriendStatusActivity.class);
        startActivity(intent);

    }



    /**
     * 定位监听器
     */

    private int i = 0;
    double distance = 0;
    List<LatLng> pts = new ArrayList<LatLng>();

    double totalDistance = 0;
    TextView speed;

    private class MyLocationListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
//            Log.e("hechao", "onReceive...");
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                Log.e("hechao", "location == null || mMapView == null ");
                return;
            }

            if (pts.size() > 1) {
                if (pts.get(pts.size() - 1).latitude != location.getLatitude() ||
                        pts.get(pts.size() - 1).longitude != location.getLongitude()) {
//                    Toast.makeText(getApplicationContext(), "正在移动" + location.getLocationDescribe(), Toast.LENGTH_LONG).show();
                }

            }


//            pts.add(new LatLng(30.123123,114.123123));

            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(16.7f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            } else {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//                Log.e("hechao","首次定位"+ll.toString());
                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
//            mylocation.setText(location.getAddrStr());


            //标出当前位置
            point = new LatLng(location.getLatitude(), location.getLongitude());

//            Log.e("hechao", i + point.toString());
            i++;
//            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
//            Toast.makeText(getApplicationContext(), "第" + i + "次定位" + point.toString(), Toast.LENGTH_SHORT).show();
//            mBaiduMap.addOverlay(option);
//            mMapView.removeAllViews();
            long n = new Date().getTime();
            DecimalFormat df = new DecimalFormat("0.0 ");
            if (pts.size() >= 2) {
                double d = com.baidu.mapapi.utils.DistanceUtil.getDistance(pts.get(pts.size() - 1), point);
                totalDistance += d;
                Log.e("hechao", "跑了 " + totalDistance + " 米");
//                Toast.makeText(getApplicationContext(), "第" + i + "次跑了 " + d + " 米", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "跑了 " + totalDistance + " 米", Toast.LENGTH_SHORT).show();
                textView.setText("完成了： " + df.format(totalDistance) + " 米");
                int min = ((int) ((n - currentTime) / 1000) / 60);
                int sec = (int) ((n - currentTime) / 1000 - min * 60);
                speed.setText("时间：" + min + "min" + sec + "sec  \n" + "速度：" + (double) (Math.round(totalDistance / min * 100) / 100.0) + " 米/分钟");
                OverlayOptions ooPolyline = new PolylineOptions().width(20).color(0xAAFF0000).points(pts);
                //添加到地图
                mBaiduMap.addOverlay(ooPolyline);
            }
            pts.add(point);
//            ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//            giflist.add(bitmap);
//            giflist.add(bitmap2);
//            giflist.add(bitmap3);

//            MarkerOptions ooD = new MarkerOptions().position(point).icons(giflist).zIndex(0).period(10);
//            // 生长动画
//            ooD.animateType(MarkerOptions.MarkerAnimateType.grow);
//            Marker mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));


        }


    }


    /**
     * location
     */
    private void initLocationClient() {

        textView = (TextView) findViewById(R.id.totalDistance);
        speed = (TextView) findViewById(R.id.speed);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数


//        mBaiduMap.setMyLocationConfigeration( new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, mCurrentMarker) );


//        LocationClientOption类，该类用来设置定位SDK的定位方式，e.g.：
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);

        //开始定位
        mLocationClient.start();
    }


    /**
     * location1
     */
    private void initLocation() {
        textView = (TextView) findViewById(R.id.totalDistance);
        speed = (TextView) findViewById(R.id.speed);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数


//        mBaiduMap.setMyLocationConfigeration( new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, mCurrentMarker) );


//        LocationClientOption类，该类用来设置定位SDK的定位方式，e.g.：
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);

        //开始定位
        mLocationClient.start();
    }


    /**
     * 定时器
     */
    long currentTime = 0;

    void initTimer() {
        Date now = new Date();
        currentTime = now.getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }


}
