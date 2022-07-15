package com.tang.shard.controller;

import com.tang.shard.service.HealthRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
public class HealthRecordController {
//
//    @Resource
//    private HealthRecordService healthRecordService;
//
//    @GetMapping("/create/records")
//    public String testProcessRecords() throws SQLException, InterruptedException {
//        healthRecordService.processHealthRecords();
//        Thread.sleep(1000);
//        return "ok......"+"\t"+healthRecordService.getRecords();
//    }
//
//    @GetMapping("/deleteAll/records")
//    public String delProcessRecords() throws SQLException, InterruptedException {
//        healthRecordService.deleteAll();
//        Thread.sleep(1000);
//        return "ok......"+"\t"+healthRecordService.getRecords();
//    }
}
