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

import com.damoim.model.dto.CommentDTO;
import com.damoim.model.dto.MeetCommentDTO;
import com.damoim.model.vo.Image;
import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.MeetingsAgree;
import com.damoim.model.vo.MeetingsComment;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.MembershipMeetings;
import com.damoim.model.vo.MembershipUserList;
import com.damoim.service.MeetingsCommentService;
import com.damoim.service.MembershipMeetingService;
import com.damoim.service.MembershipService;
import com.damoim.service.RemoveMemberService;


@Controller
public class MembershipMeetingController {

	
	@Autowired
	private  MembershipMeetingService service;
	@Autowired
	private MembershipService membershipService;
	@Autowired
	private MeetingsCommentService commentService;
	@Autowired
	private RemoveMemberService removeService;
	
	
	// 멤버쉽 미팅 작성 페이지로 이동 
	@GetMapping("/write")
	public String write(int membershipCode, Model model) {
		System.out.println("모임 컨트롤러 매핑 " + membershipCode);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 로그인 회원
		Member m1 = (Member) authentication.getPrincipal();
		boolean check = false;
		
		// 권한 체크 
 for(int i=0; i < m1.getMemberListDTO().size(); i++) {
	 if(m1.getMemberListDTO().get(i).getMembershipCode() == membershipCode && !(m1.getMemberListDTO().get(i).getListGrade().equals("guest") || m1.getMemberListDTO().get(i).getListGrade().equals("regular"))) {
		 
		 check = true;
	 }
	 
 }

		if(!check) {
			
			return "error";
		}
		
		
		model.addAttribute("membershipCode",membershipCode );
		return "membershipMeeting/meetingWrite";
	}
	
	// 작성완료 
	@PostMapping("/write")
	public String write1(int membershipCode, MembershipMeetings meeting) throws IOException {

		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		
		
		meeting.setId(authentication.getName());
	

		service.addMeeting(meeting);
		
	


	   
	  
	   
		return "redirect:/club/"+membershipCode;
	}
	
	
	// 멤버쉽 미팅 상세 페이지로 이동 
	@GetMapping("/meetingDetail")
	public String meetingDetail(int meetCode , Model model) {
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//로그인 정보 가져옴 
		Member mem = (Member) authentication.getPrincipal();
		
		MembershipMeetings meet = service.meetSelect(meetCode);
	
	

		// 멤버쉽 이미지가 필요해서 memebershipCode를 추출 
		
		int membershipCode = service.meetSelect(meetCode).getMembershipCode();
		
		boolean check = false;
		for( int j=0; j<mem.getMemberListDTO().size(); j++) {
			if(mem.getMemberListDTO().get(j).getMembershipCode() == membershipCode && !mem.getMemberListDTO().get(j).getListGrade().equals("guest") ) {
				
				check = true;
				
			}
			
		} 
	
		
		
		if(meet.getMeetInfo()==null || !check) {
			
			return "error";
		}
		
		model.addAttribute("meet", meet);
		
		
		  
	//동의 명단을 구하는거 ok 얘네가 listgrade 없음 memebershipUserList로 가져와야함
List<MembershipUserList> agree = new ArrayList<MembershipUserList>();

for( int i=0; i<service.meetMember(meetCode).size(); i++) {
	for(int j=0; j<membershipService.MembershipAllRegular(membershipCode).size(); j++) {
		if(membershipService.MembershipAllRegular(membershipCode).get(j).getMember().getId().equals(service.meetMember(meetCode).get(i).getMember().getId())){
			agree.add(membershipService.MembershipAllRegular(membershipCode).get(j));
		}
		
	}
	
	
	
}
model.addAttribute("agree", agree);
model.addAttribute("count",agree.size());

	    model.addAttribute("regular", membershipService.MembershipAllRegular(membershipCode));
		// 캘린더 추가 ? 
		model.addAttribute("allmeet", service.allMeetings(membershipCode));
		// 해당 코드로 멤버쉽 유저리스트 불러와서 membership 정보 가져옴  
	
		model.addAttribute("allInfo", membershipService.MembershipAllInfo(membershipCode));
		
		model.addAttribute("adminList", membershipService.adminUser(membershipCode));
		// 해당 멤버쉽 코드의 모든 멤버정보 
	

		String id = service.meetSelect(meetCode).getId();
		
		// 글쓴이 찾아오기 
		for(int i =0; i <membershipService.MembershipAllInfo(membershipCode).size(); i ++) {
			
			if( id.equals(membershipService.MembershipAllInfo(membershipCode).get(i).getMember().getId())){
				model.addAttribute("writer",membershipService.MembershipAllInfo(membershipCode).get(i).getMember());
			}
		}
	
	
	
	

   
	
		ArrayList<MeetingsComment> commList = commentService.allMeetingsComment(meetCode); // 일반댓글
		ArrayList<MeetCommentDTO> dtoList = new ArrayList<MeetCommentDTO>(); //합칠예정
		
		for (int i = 0; i < commList.size(); i++) {
			MeetCommentDTO commentDTO = new MeetCommentDTO().builder()
		            .meetCommentCode(commList.get(i).getMeetCommentCode())
		            .meetCommentText(commList.get(i).getMeetCommentText())
		            .meetCommentDate(commList.get(i).getMeetCommentDate())
		            .id(commList.get(i).getMember().getId())
		            .nickname(commList.get(i).getMember().getNickname())
		            .memberImg(commList.get(i).getMember().getMemberImg())
		            .meetCode(commList.get(i).getMeetCode()) 
		            .recoment(new ArrayList<>()) 
		            .build();
		    
		    dtoList.add(commentDTO);
		    ArrayList<MeetingsComment> recommList = commentService.MeetingsReComment(meetCode, commentDTO.getMeetCommentCode());
		    if(recommList.size()> 0) {
		    for (int j = 0; j < recommList.size(); j++) {
		    	MeetCommentDTO recommentDTO = new MeetCommentDTO().builder()
		                .meetCommentCode(recommList.get(j).getMeetCommentCode())
		                .meetCommentText(recommList.get(j).getMeetCommentText())
		                .meetCommentDate(recommList.get(j).getMeetCommentDate())
		                .id(recommList.get(j).getMember().getId())
		                .nickname(recommList.get(j).getMember().getNickname())
		                .memberImg(recommList.get(j).getMember().getMemberImg())
		                .meetCode(recommList.get(j).getMeetCode()) 
		                .meetParentsCommentCode(commList.get(i).getMeetCommentCode())
		                .build();
		        
		     
		        commentDTO.getRecoment().add(recommentDTO);
		    }
		    
		}
		}
		

		
		model.addAttribute("comment", dtoList); 
	
	 
	 
	  
	
	
			
		return "membershipMeeting/meetingDetail";
	}
	
	
	
	@ResponseBody
	@PostMapping("/go")
	public String participation(MeetingsAgree ma , String yN) {
		System.out.println("참가 컨트롤러 접속 ");
		System.out.println(yN);
	//	service.participation(ma);
		if(yN.equals("yes")) {
			service.participation(ma);
			
		} else {
			service.participationCancle(ma);
			
		}
			
			
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
	public String update(MembershipMeetings meetings, Model model) {
		
	  int meetCode = meetings.getMeetCode();
	  
	  model.addAttribute("meetingInfo" ,service.meetSelect(meetCode));
	  
	 
	  
	  
	  
		return "membershipMeeting/meetingUpdate";
	}
  
  
  
  @PostMapping("/meetingUpdate")
	public String updateSubmit(MembershipMeetings meetings, Model model) {
		
	  int meetCode = meetings.getMeetCode();
	  
	  int membershipCode = service.meetSelect(meetCode).getMembershipCode();
	  
	 
	  service.meetingUpdate(meetings);
	  
	  
		return "redirect:/club/"+membershipCode;
	}
	
	

	// 미팅 삭제 부분 
	@GetMapping("/meetingDelete")
	public String remove(int meetCode) throws IllegalStateException, IOException {
		
		int membershipCode = service.meetSelect(meetCode).getMembershipCode();
	 MembershipMeetings meetings= service.meetSelect(meetCode);
	
		
		removeService.deleteMeeting(meetCode);
		
		
		return "redirect:/club/"+membershipCode;

	
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
