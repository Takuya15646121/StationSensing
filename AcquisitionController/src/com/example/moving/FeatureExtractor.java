package com.example.moving;


public interface FeatureExtractor {

    public void init();

    /**
     * 特徴量計算の本体．
     *
     * @param mywindow MxN列の配列でウィンドウ生データを渡す．Mはウィンドウサイズ，Nはセンサの次元．
     * @return F次元の特徴量ベクトル．
     */
    public double[] calcFeatures(double[][] mywindow);
}