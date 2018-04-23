package com.hfkj.bbt.vo.restvo;

/**
 * Created by Administrator on 2017-12-26.
 */
public class Environment {
    //温度
    private String temperature;
    //湿度
    private String humidity;
    //照度
    private String illuminate;
    //噪音
    private String noise;
    //pm2.5
    private String pm25;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getIlluminate() {
        return illuminate;
    }

    public void setIlluminate(String illuminate) {
        this.illuminate = illuminate;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }
}
