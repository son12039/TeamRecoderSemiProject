package com.damoim.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.model.vo.Image;
import com.damoim.model.vo.MeetingsAgree;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipMeetings;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.service.MembershipMeetingService;
import com.damoim.service.MembershipService;


@Controller
public class MembershipMeetingController {

	
	@Autowired
	private  MembershipMeetingService service;
	@Autowired
	private MembershipService membershipService;
	
	@GetMapping("/write")
	public String write(int membershipCode, Model model) {
		System.out.println("모임 컨트롤러 매핑 " + membershipCode);
		model.addAttribute("membershipCode",membershipCode );
		return "membershipMeeting/meetingWrite";
	}
	
	@PostMapping("/write")
	public String write1(int membershipCode, MembershipMeetings meeting) throws IOException {
		/*
		 * System.out.println("작성 완료 컨트롤러 매핑 ");
		 * 
		 * Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication();
		 * 
		 * System.out.println(files.get(0).getOriginalFilename());
		 * meeting.setId(authentication.getName());
		 * 
		 * 
		 * // 미팅을 생성 service.addMeeting(meeting);
		 * 
		 * 
		 * // 생성된 미팅을 바탕으로 이미지를 저장할 경로에 폴더 생성 int meetCode = meeting.getMeetCode(); Path
		 * directoryPath =
		 * Paths.get("\\\\\\\\192.168.10.51\\\\damoim\\\\membership\\"+membershipCode+"\
		 * \"+meetCode+"\\"); Files.createDirectories(directoryPath);
		 * 
		 * Image img = new Image(); // 이미지를 전부 업로드 for(int i=0; i<files.size(); i++) {
		 * 
		 * img.setMembershipCode(membershipCode);
		 * img.setMeetCode(meeting.getMeetCode());
		 * img.setImgUrl("http://192.168.10.51:8081/membership/"+membershipCode+"/"+
		 * meetCode+"/"+fileUpload(files.get(i),membershipCode,meeting.getMeetCode()));
		 * service.addImage(img);
		 * 
		 * 
		 * } // DB에 업로드전에 url을 세팅
		 * 
		 * 
		 * 
		 * 
		 * 
		 * int size = membershipService.MembershipAllInfo(membershipCode).size();
		 * for(int i=0; i<size; i++) {
		 * if(!membershipService.MembershipAllInfo(membershipCode).get(i).getListGrade()
		 * .equals("guest")) {
		 * 
		 * System.out.println("게스트가 아닌애들 이름 " +
		 * membershipService.MembershipAllInfo(membershipCode).get(i).getMember().getId(
		 * ));
		 * ma.setId(membershipService.MembershipAllInfo(membershipCode).get(i).getMember
		 * ().getId()); ma.setMeetCode(meeting.getMeetCode()); service.yesOrNo(ma); } }
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		
		
		meeting.setId(authentication.getName());

		service.addMeeting(meeting);
		
		List<MembershipUserList> list = membershipService.MembershipAllInfo(membershipCode);
		MeetingsAgree ma = new MeetingsAgree();
		List<String> ides = new ArrayList<String>();
		
		for(MembershipUserList membership : list) {
			if(!membership.getListGrade().equals("guest")) {
				ides.add(membership.getMember().getId());
			}
		}
		ma.setIdes(ides);
		ma.setMeetCode(meeting.getMeetCode());
		service.yesOrNo(ma);

		/*int size = membershipService.MembershipAllInfo(membershipCode).size();
		MeetingsAgree ma = new MeetingsAgree();
	
		for (int i = 0; i < size; i++) {
			if (!membershipService.MembershipAllInfo(membershipCode).get(i).getListGrade().equals("guest")) {
				ma.setId(membershipService.MembershipAllInfo(membershipCode).get(i).getMember().getId());
				ma.setMeetCode(meeting.getMeetCode());
				service.yesOrNo(ma);

			}
		}*/

		System.out.println(meeting);
	   
	  
	   
		return "redirect:/club/"+membershipCode;
	}
	
	@GetMapping("/meetingDetail")
	public String meetingDetail(int meetCode , Model model) {
		System.out.println("디테일 컨트롤러 연결 : " + meetCode);
		model.addAttribute("meet", service.meetSelect(meetCode));
		model.addAttribute("list", service.meetMember(meetCode));
	//	String name = service.meetMember(meetCode).get(0).getMember().getId();
	//	MeetingsAgree asd =(MeetingsAgree) service.meetMember(meetCode).get(0);

		List<MeetingsAgree> agree = new ArrayList<>();
		
	  for(int i=0; i<service.meetMember(meetCode).size(); i++) {
		  String name = service.meetMember(meetCode).get(i).getMember().getId();
		  MeetingsAgree asd =(MeetingsAgree) service.meetMember(meetCode).get(i);
		  asd.setId(name);
		  agree.add(asd);
		  System.out.println("바뀐 애들" + asd);
	  }
	  
	  System.out.println(agree);
	  
	
	 
	 
	  
	
	  
	
			
		return "membershipMeeting/meetingDetail";
	}
	
	@ResponseBody
	@PostMapping("/go")
	public String participation(MeetingsAgree ma) {
		System.out.println("참가 컨트롤러 접속 ");
		service.participation(ma);
		
		return "redirect:/meetingDetail";
	}
		
	
	
	
	
  public String fileUpload(MultipartFile file,int membershipCode,int meetCode) throws IllegalStateException, IOException {
		
		UUID uuid= UUID.randomUUID();
		
		String fileName = uuid.toString() + "_" + file.getOriginalFilename();
			
		File copyFile = new File("\\\\\\\\192.168.10.51\\\\damoim\\\\membership\\"+ membershipCode+"\\"+meetCode+"\\"+fileName);  
		
		file.transferTo(copyFile); 
		
	
		
	
		
		return fileName;
		
		
	}
		
  
  @GetMapping("/meetingUpdate")
  public String update(int no, Model model) {
		
		System.out.println(no);
	//	model.addAttribute("no1", service.one(no));
		
		return "/update";
	}
	
	/*
	 * @PostMapping("/update") public String update(List<Image> image) throws
	 * IllegalStateException, IOException { System.out.println("업데이트 보드 " + image);
	 * 
	 * 
	 * // 수정전 해당 보드의 url for(int i =0; i<image.size(); i++ ) { String url =
	 * image.get(i).getImgUrl(); // 수정전 해당 보드의 url이 존재 할 경우 삭제 if(url != null) {
	 * System.out.println("url 후 ");
	 * 
	 * String newPath =
	 * "\\\\192.168.10.51\\damoim\\membership\\"+image.get(i).getMembershipCode()+"\
	 * \"+image.get(i).getMeetCode()+"\\";
	 * 
	 * 
	 * // URL에서 파일 이름 부분 추출 String fileName = url.substring(url.lastIndexOf('/') +
	 * 1);
	 * 
	 * 
	 * // 새로운 전체 경로 생성 String newFilePath = newPath + fileName; File copyFile = new
	 * File(newFilePath);
	 * 
	 * 
	 * 
	 * if (copyFile.exists()) { boolean deleted = copyFile.delete(); if (deleted) {
	 * System.out.println("파일이 성공적으로 삭제되었습니다."); } else {
	 * System.out.println("파일 삭제 실패."); } } else {
	 * System.out.println("파일이 존재하지 않습니다."); }
	 * 
	 * 
	 * }
	 * 
	 * // 사용자가 수정시 업로드한 파일이 존재 할 경우
	 * 
	 * if( !board.getFile().getOriginalFilename().equals("")) { UUID uuid=
	 * UUID.randomUUID();
	 * 
	 * String fileName2 = uuid.toString() + "_" +
	 * board.getFile().getOriginalFilename();
	 * 
	 * 
	 * 
	 * File copyFile2 = new File("\\\\192.168.10.51\\damoim\\sungil\\" + fileName2);
	 * 
	 * board.getFile().transferTo(copyFile2);
	 * 
	 * 
	 * 
	 * board.setUrl("http://192.168.10.51:8081/sungil/" + fileName2);
	 * 
	 * }
	 * 
	 * service.update(board);
	 * 
	 * } return "redirect:/list";
	 * 
	 * 
	 * }
	 */
	
	@GetMapping("/meetingDelete")
	public String remove(int meetCode) throws IllegalStateException, IOException {
		
		
		return null;

	
	}
	
	public void fileDelete(String file, int membershipCode, int meetCode) throws IllegalStateException, IOException {
		if(file == null) {
			System.out.println("삭제할 파일이 없습니다");
		}
		else {
			System.out.println("삭제될 URL : "  + file);
		File f = new File("\\\\192.168.10.51\\damoim\\membership\\"+ Integer.toString(membershipCode) + "\\"+Integer.toString(meetCode) + "\\" + file);
		f.delete();
		
		}
	
	}
	
}
