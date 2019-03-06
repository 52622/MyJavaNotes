/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MultiAccess
 * Author:   copywang
 * Date:     2019/3/6 10:47
 * Description: 测试并发访问控制接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.testcontrolmultilink.demo;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

// https://www.cnblogs.com/pcheng/p/8127040.html
@RestController
public class MultiAccess {
  public static final RateLimiter limiter = RateLimiter.create(1); // 允许每秒最多1个任务
//
//  @RequestMapping(value = "/MultiAccess")
//  public void multiAccessTest(HttpServletRequest request, HttpServletResponse response) {
//    limiter.acquire();// 请求令牌,超过许可会被阻塞
//    System.out.println(LocalDateTime.now().toString());
//  }
  public static final Semaphore semaphore = new Semaphore(10, true); // 允许并发的任务量限制为5个

  @RequestMapping(value = "/MultiAccess")
  public void multiAccessTest(HttpServletRequest request, HttpServletResponse response) {
    try {
      semaphore.acquire();// 获取信号量,不足会阻塞
      System.out.println(LocalDateTime.now().toString());
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release(); // 释放信号量
    }
  }
}
