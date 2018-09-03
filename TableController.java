package com.solo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solo.model.Schedule;
import com.solo.service.DoctorService;
import com.solo.service.ScheduleService;

@Controller
@RequestMapping("/table")
public class TableController {

	@Resource
	private DoctorService doctorService;
	@Resource
	private ScheduleService scheduleService;
	
	
	@RequestMapping("/")
	public String index(){
		return "table/tableList";
	}
	
	@RequestMapping("/list")
	public @ResponseBody Map<String,Object> list(int limit,int offset){
		System.out.println("limit is:"+limit);
		System.out.println("offset is:"+offset);
		
		List<Schedule> scheduleList = new ArrayList<>();
		scheduleList = scheduleService.getScheduleList();
		int totalRecord = scheduleList.size();
		List<Schedule> resultSchedules = new ArrayList<>();
		for(int i=0;i<limit;i++){
			resultSchedules.add(scheduleList.get(offset+i));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", totalRecord);
		map.put("data", resultSchedules);
		
		return map;
	}
	
	@RequestMapping("/selectDoctors")
	@ResponseBody
	public Map<String,Object> selectDoctors(@RequestParam("idList[]")List<Integer> idList){
		
		System.out.println("select list"+idList.size());
		
		for(int id:idList){
			Schedule schedule = new Schedule();
			Schedule scheduleTarget = scheduleService.findScheduleById((long)id);
			schedule.setDay(scheduleTarget.getDay());
			schedule.setDoctorName(schedule.getDoctorName());
			schedule.setTime(schedule.getTime());
			scheduleService.save(schedule);
		}
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status","success");
		return map;
		
	}
	
	@RequestMapping("/selectList")
	@ResponseBody
	public Map<String,Object> selectList(int limit,int offset){
		
		System.out.println("limit is:"+limit);
		System.out.println("offset is:"+offset);
		
		List<Schedule> scheduleList = new ArrayList<>();
		scheduleList = scheduleService.getScheduleList();
		List<Schedule> resultSchedules = new ArrayList<>();
		if(scheduleList.size()>55){
			for(int i=55;i<scheduleList.size();i++){
				resultSchedules.add(scheduleList.get(i));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", resultSchedules.size()-55);
		map.put("data", resultSchedules);
		
		return map;
	}
	
	
}
