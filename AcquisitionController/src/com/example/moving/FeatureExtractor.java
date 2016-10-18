package com.example.moving;


public interface FeatureExtractor {

    public void init();

    /**
     * �����ʌv�Z�̖{�́D
     *
     * @param mywindow MxN��̔z��ŃE�B���h�E���f�[�^��n���DM�̓E�B���h�E�T�C�Y�CN�̓Z���T�̎����D
     * @return F�����̓����ʃx�N�g���D
     */
    public double[] calcFeatures(double[][] mywindow);
}