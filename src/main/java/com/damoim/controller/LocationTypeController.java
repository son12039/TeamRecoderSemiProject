package com.damoim.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.damoim.model.dto.LocationTypeDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.MembershipDTO;
import com.damoim.model.dto.RankDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.dto.searchAjaxDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;
import com.damoim.service.LocationTypeService;
import com.damoim.service.MemberService;
import com.damoim.service.MembershipService;

// 승훈 - 0814 수정

@Controller
public class LocationTypeController {
	
	@Autowired
	private LocationTypeService locationTypeservice;

	@Autowired
	private MembershipService memberService;
	
	@Autowired
	private MemberService mem;
	
	public List<MemberLocTypeDTO> locationTypeList(SearchDTO search) {

		
		// '세종' 안에 null 이 아니면 '세종시' 넣고
		if(search.getLocationSName()!=null) {
			search.setLocationSNameList(new ArrayList<>(Arrays.asList(search.getLocationSName().split(","))));	
		}
		if(search.getTypeSName()!=null) {
			search.setTypeSNameList(new ArrayList<>(Arrays.asList(search.getTypeSName().split(","))));
		}
		

		// Location type 확인후 MemberShipCode 뽑기
		List<Integer> membershipCodes = locationTypeservice.searchList(search);		
		
		List<MemberLocTypeDTO> list = new ArrayList<MemberLocTypeDTO>();
		

		// 모든 정보 합쳐서 리스트로 뿌리기
		if(membershipCodes.size()!=0) {
			// 위에서 뽑아온 코드를 다시 searchDTO에 저장
			search.setMembershipCodes(membershipCodes);
			// 받아온 코드로 다시 memberLocTypeList 관련된 지역 및 타입 가져오기 
			list = locationTypeservice.memberLocTypeList(search);

			for(MemberLocTypeDTO dto : list) {
				//그리고 dto에 만든 리스트에 또 넣기
				List<LocationCategory> locations = locationTypeservice.locationList(dto.getMembershipCode());
				List<TypeCategory> types = locationTypeservice.typeList(dto.getMembershipCode());
				Member member = locationTypeservice.selectMemberNickName(dto.getMembershipCode());
				dto.setLocations(locations);
				dto.setTypes(types);
				dto.setNickname(member.getNickname());
				dto.setId(member.getId());
				dto.setMemberImg(member.getMemberImg());
				dto.setCount(locationTypeservice.allMemberShipUser(dto.getMembershipCode()));
				dto.setMemberManner(member.getMemberManner());
			}
			
		}
		
	
		
		return list;
	}
	
	
	// 화면단에 뿌리기
	@GetMapping("/")
	public String locationType(Model model, SearchDTO search) {
		
		
	
		 
		
	ArrayList<MemberLocTypeDTO>	 test = (ArrayList<MemberLocTypeDTO>) locationTypeList(search);
	
	

		model.addAttribute("list", test);
		
	      Calendar calendar = Calendar.getInstance();
	        
	        // 오늘 날짜를 가져옵니다.
	        Date today = calendar.getTime();
	        
	        // 30일 전 날짜를 계산합니다.
	        calendar.add(Calendar.DAY_OF_MONTH, -30);
	        Date thirtyDaysAgo = calendar.getTime();
	        
	        // SimpleDateFormat 객체를 생성하여 날짜 형식을 정의합니다.
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        
	        // 날짜를 원하는 형식으로 변환합니다.
	        String todayFormatted = sdf.format(today);
	        String thirtyDaysAgoFormatted = sdf.format(thirtyDaysAgo);
	        
	        // 결과를 출력합니다.
	        List<RankDTO> mannerDto = new ArrayList<RankDTO>();
	        List<RankDTO> meetDto = new ArrayList<RankDTO>();
	    
	   
	      
	       
	      for(int i=0; i<mem.top5().size(); i++) {
	    	  int count=0;
	    	
	    	  for(int j=0; j<mem.top5().size(); j++) {
	    	  if(mem.top5().get(i).getMemberManner() < mem.top5().get(j).getMemberManner()) {
	    		  count ++; 		 
	    	  }	    		 
	    	  }
	    	  mannerDto.add(new RankDTO().builder().memberManner(mem.top5().get(i).getMemberManner()).nickname(mem.top5().get(i).getNickname()).rank(count).build()); 	
	      }
	     
	     
	      for(int i=0; i<mem.veteran().size(); i++) {
	    	  int count=0;
	    	
	    	  for(int j=0; j<mem.veteran().size(); j++) {
	    	  if(mem.veteran().get(i).getMeetCount() < mem.veteran().get(j).getMeetCount()) {
	    		  count ++; 		 
	    	  }	    		 
	    	  }
	    	  meetDto.add(new RankDTO().builder().meetCount(mem.veteran().get(i).getMeetCount()).nickname(mem.veteran().get(i).getNickname()).rank(count).build()); 	
	      }
	     
	     
	     
	      System.out.println("참여랭킹 " + meetDto);
	model.addAttribute("meetRank", meetDto);
	model.addAttribute("mannerRank", mannerDto);

	
	        model.addAttribute("today30", thirtyDaysAgoFormatted);
	        model.addAttribute("today", todayFormatted);
		// 화면 상단바
		model.addAttribute("locLaNameList", locationTypeservice.locLaNameList());
		model.addAttribute("typeLaNameList", locationTypeservice.typeLaNameList());
		model.addAttribute("locSNameList", locationTypeservice.locSNameList(search.getLocationLaName()));
		model.addAttribute("typeSNameList", locationTypeservice.typeSNameList(search.getTypeLaName()));
		
		return "index";
	}
	

	
	@ResponseBody
	@GetMapping("list")
	public List<MemberLocTypeDTO> list(SearchDTO search) {	
		return locationTypeList(search);
	}
	
	@ResponseBody
	@GetMapping("locationSList")
	public List<String> locationSList(String laName) {
		return locationTypeservice.locSNameList(laName);
	}
	
	@ResponseBody
	@GetMapping("typeSName")
	public List<String> typeSName(String typeLaName) {
		return locationTypeservice.typeSNameList(typeLaName);
	}
}
