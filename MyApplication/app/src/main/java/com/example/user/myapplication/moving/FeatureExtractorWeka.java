package com.example.user.myapplication.moving;

import tuat.daily.phonepos.feature.base.BaseFeature;
import tuat.daily.phonepos.feature.base.IBaseFeature;
import tuat.daily.phonepos.feature.name.Axis;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 * Created by CARROT on 2016/10/11.
 */
public class FeatureExtractorWeka implements FeatureExtractor{
    private final IBaseFeature base;
    //デシリアライズされた分類器．immutableなのでそのままコピー
    private final Classifier pd;

    public FeatureExtractorWeka(Classifier pd,String[] features){
        this.pd = pd;
        this.base = new BaseFeature(features);
    }

    @Override
    public void init() {


    }


    /**
     * 2次元配列(軸*ウィンドウ)を受け取り，特徴量セットを返す．
     * WekaのInstanceオブジェクトとして特徴量セットは返されるので，これに対して適宜処理を行う．
     * @param mywindow window of sensors
     */
    public Instance calcInstance(double[][] mywindow) {

        base.setWindow(Axis.ACC_RAW_X, mywindow[0], 50);
        base.setWindow(Axis.ACC_RAW_Y, mywindow[1], 50);
        base.setWindow(Axis.ACC_RAW_Z, mywindow[2], 50);
        base.setWindow(Axis.ACC_RAW_3AXIS, calc3axis(new double[][]{mywindow[0],mywindow[1],mywindow[2]}), 50);
        base.setWindow(Axis.MAG_RAW_X,mywindow[3],50);
        base.setWindow(Axis.MAG_RAW_Y,mywindow[4],50);
        base.setWindow(Axis.MAG_RAW_Z,mywindow[5],50);
        base.setWindow(Axis.MAG_RAW_3AXIS,calc3axis(new double[][]{mywindow[3],mywindow[4],mywindow[5]}),50);

        Instance instance = base.makeInstance();

        return instance;
    }


    public int predict(double[][] window){

        Instance instance = calcInstance(window);
        int result = -1;
        try {
            result = (int)pd.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result : "+Integer.toString(result));

        return result;
    }

    /**
     * @deprecated
     */
    @Override
    public double[] calcFeatures(double[][] mywindow) {

        return null;
    }

    private double[] calc3axis(double[][] window){
        double[] axis3 = new double[window.length];
        for(int i=0;i<axis3.length;i++){
            double v = Math.pow(window[i][0], 2.0)+Math.pow(window[i][1], 2.0)+Math.pow(window[i][2], 2.0);
            axis3[i] = Math.sqrt(v);
        }
        return axis3;
    }

}
