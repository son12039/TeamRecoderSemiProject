package mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MainComment;
import com.damoim.model.vo.Member;

@Mapper
public interface MainCommentMapper {
	void insertComment(MainComment mainComment);
	ArrayList<MainComment>allMainComment(int membershipCode);
	ArrayList<MainComment>mainReComment(int membershipCode, int mainCommentCode);
	void deleteUpdateComment(int mainCommentCode);
	void deleteComment(int mainCommentCode);
	int reCommentCount(int mainCommentCode);
	void updateComment(MainComment mainComment);
	
	
	// 탈퇴한 회원 댓글 비공개
	ArrayList<MainComment> resignedMember(MainComment mainComment);
	void resignedComment (MainComment mainComment);
	
	
	
}
