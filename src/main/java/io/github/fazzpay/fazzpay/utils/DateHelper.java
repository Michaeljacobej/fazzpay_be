package io.github.fazzpay.fazzpay.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateHelper {
  public static List<String> getDatesInLastSevenDays() {
    List<String> dates = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date now = new Date();

    for (int i = 0; i < 7; i++) {
      Date currentDate = new Date(now.getTime() - (86400000 * i));
      String date = dateFormat.format(currentDate);
      dates.add(date);
    }

    return dates;
  }
}
