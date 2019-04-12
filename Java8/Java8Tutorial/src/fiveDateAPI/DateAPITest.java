package fiveDateAPI;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class DateAPITest {
  public static void main(String[] args) {
    // 1 Clock 类提供了访问当前日期和时间的方法，Clock 是时区敏感的，可以用来取代 System.currentTimeMillis() 来获取当前的微秒数。某一个特定的时间点也可以使用 Instant 类来表示，Instant 类也可以用来创建旧版本的java.util.Date 对象。
    Clock clock = Clock.systemDefaultZone();
    long millis = clock.millis();
    System.out.println(millis);//1552379579043
    Instant instant = clock.instant();
    System.out.println(instant);
    Date legacyDate = Date.from(instant); //2019-03-12T08:46:42.588Z
    System.out.println(legacyDate);//Tue Mar 12 16:32:59 CST 2019

    // 2 Timezones(时区) 在新API中时区使用 ZoneId 来表示。时区可以很方便的使用静态方法of来获取到。 抽象类ZoneId（在java.time包中）表示一个区域标识符。 它有一个名为getAvailableZoneIds的静态方法，它返回所有区域标识符。
    //输出所有区域标识符
    System.out.println(ZoneId.getAvailableZoneIds());

    ZoneId zone1 = ZoneId.of("Europe/Berlin");
    ZoneId zone2 = ZoneId.of("Brazil/East");
    System.out.println(zone1.getRules());// ZoneRules[currentStandardOffset=+01:00]
    System.out.println(zone2.getRules());// ZoneRules[currentStandardOffset=-03:00]

    // 3 LocalTime(本地时间)
    // LocalTime 定义了一个没有时区信息的时间，例如 晚上10点或者 17:30:15
    LocalTime now1 = LocalTime.now(zone1);
    LocalTime now2 = LocalTime.now(zone2);
    System.out.println(now1);//02:48:05.349
    System.out.println(now2);//21:48:05.350
    System.out.println(now1.isBefore(now2));  // true

    long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
    long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

    System.out.println(hoursBetween);       // 19
    System.out.println(minutesBetween);     // 1140
    //LocalTime 提供了多种工厂方法来简化对象的创建，包括解析时间字符串.
    LocalTime late = LocalTime.of(23, 59, 59);
    System.out.println(late);       // 23:59:59

    DateTimeFormatter germanFormatter =
        DateTimeFormatter
            .ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(Locale.GERMAN);

    LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
//    LocalTime leetTime = LocalTime.parse("10:15:30");
    System.out.println(leetTime);   // 10:15:30

    /**
     * SQL -> Java
     * --------------------------
     * 这3个对象都是不可变的，线程安全
     * date -> LocalDate
     * time -> LocalTime
     * timestamp -> LocalDateTime
     */
    //https://www.liaoxuefeng.com/article/00141939241051502ada88137694b62bfe844cd79e12c32000

    //LocalDate(本地日期) LocalDate 表示了一个确切的日期，比如 2014-03-11。该对象值是不可变的，用起来和LocalTime基本一致。
    //这些对象是不可变的，操作返回的总是一个新实例
    LocalDate today = LocalDate.now();//获取现在的日期
    System.out.println("今天的日期: "+today);//2019-03-12
    LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
    System.out.println("明天的日期: "+tomorrow);//2019-03-13
    LocalDate yesterday = tomorrow.minusDays(2);
    System.out.println("昨天的日期: "+yesterday);//2019-03-11
    LocalDate independenceDay = LocalDate.of(2019, Month.MARCH, 12);
    DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
    System.out.println("今天是周几:"+dayOfWeek);//TUESDAY
    //从字符串解析一个 LocalDate 类型
    String str1 = "2014==04==12 01时06分09秒";
    // 根据需要解析的日期、时间字符串定义解析所用的格式器
    DateTimeFormatter fomatter1 = DateTimeFormatter
        .ofPattern("yyyy==MM==dd HH时mm分ss秒");

    LocalDateTime dt1 = LocalDateTime.parse(str1, fomatter1);
    System.out.println(dt1); // 输出 2014-04-12T01:06:09

    String str2 = "2014$$$四月$$$13 20小时";
    DateTimeFormatter fomatter2 = DateTimeFormatter
        .ofPattern("yyy$$$MMM$$$dd HH小时");
    LocalDateTime dt2 = LocalDateTime.parse(str2, fomatter2);
    System.out.println(dt2); // 输出 2014-04-13T20:00
    //使用 DateTimeFormatter 格式化日期
    LocalDateTime rightNow=LocalDateTime.now();
    String date=DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(rightNow);
    System.out.println(date);//2019-03-12T16:26:48.29
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
    System.out.println(formatter.format(rightNow));//2019-03-12 16:26:48
    //LocalDateTime(本地日期时间) LocalDateTime 同时表示了时间和日期
    LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

    dayOfWeek = sylvester.getDayOfWeek();
    System.out.println(dayOfWeek);      // WEDNESDAY

    Month month = sylvester.getMonth();
    System.out.println(month);          // DECEMBER

    long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
    System.out.println(minuteOfDay);    // 1439

    instant = sylvester
        .atZone(ZoneId.systemDefault())
        .toInstant();

    legacyDate = Date.from(instant);
    System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014
    formatter =
        DateTimeFormatter
            .ofPattern("MM dd, yyyy - HH:mm");
    LocalDateTime parsed = LocalDateTime.parse("11 03, 2014 - 07:13", formatter);
    String string = formatter.format(parsed);
    System.out.println(string);     // 11 03, 2014 - 07:13

  }
}
