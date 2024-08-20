package main.sensoryexperimentplatform.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;

import java.io.IOException;

public class Periodic implements Model{
    private long amount;
    private long forAmount;
    private String daiLuong;
    private String time;
    private String forDaiLuong;
    public Periodic( long grams, String daiLuong, String time,long forAmount, String forDaiLuong) {
        this.time = time;
        this.amount = grams;
        this.daiLuong = daiLuong;
        this.forAmount = forAmount;
        this.forDaiLuong = forDaiLuong;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getForAmount() {
        return forAmount;
    }

    public void setForAmount(long forAmount) {
        this.forAmount = forAmount;
    }

    public String getDaiLuong() {
        return daiLuong;
    }

    public void setDaiLuong(String daiLuong) {
        this.daiLuong = daiLuong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getForDaiLuong() {
        return forDaiLuong;
    }

    public void setForDaiLuong(String forDaiLuong) {
        this.forDaiLuong = forDaiLuong;
    }
    //    public Periodic (long seconds){
//        this.seconds = seconds;
//    }


}
