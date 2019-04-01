import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: ObserverTest
 * Author:   copywang
 * Date:     2018/11/14 10:57
 * Description: 观察者模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class ObserverTest {

  public static void main(String[] args) {
    // 1. 新建主题
    WeatherData weatherData = new WeatherData();
    // 2. 新建观察者并注册到主题中
    CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
    StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
    // 3. 有状态更新
    weatherData.setMeasurements(0, 0, 0);
    weatherData.setMeasurements(1, 1, 1);
  }

  // 主题：注册，移除，通知所有观察者
  public interface Subject {
    void resisterObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
  }


  // 具体主题
  public static class WeatherData implements Subject {
    private List<Observer> observers; // 重点：维护了一张观察者列表
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
      observers = new ArrayList<>();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
      this.temperature = temperature;
      this.humidity = humidity;
      this.pressure = pressure;
      notifyObserver();
    }

    @Override
    public void resisterObserver(Observer o) {
      observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
      int i = observers.indexOf(o);
      if (i >= 0) {
        observers.remove(i);
      }
    }

    @Override
    public void notifyObserver() {
      for (Observer o : observers) {
        o.update(temperature, humidity, pressure);
      }
    }
  }

  // 观察者
  public interface Observer {
    void update(float temp, float humidity, float pressure);
  }

  public static class StatisticsDisplay implements Observer {

    public StatisticsDisplay(Subject weatherData) {
      weatherData.resisterObserver(this);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
      System.out.println("StatisticsDisplay.update: " + temp + " " + humidity + " " + pressure);
    }
  }

  public static class CurrentConditionsDisplay implements Observer {

    public CurrentConditionsDisplay(Subject weatherData) {
      weatherData.resisterObserver(this);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
      System.out.println("CurrentConditionsDisplay.update: " + temp + " " + humidity + " " + pressure);
    }
  }
}
