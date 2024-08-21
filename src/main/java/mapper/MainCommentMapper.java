package mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.damoim.model.vo.MainComment;

@Mapper
public interface MainCommentMapper {
	void insertComment(MainComment mainComment);
	ArrayList<MainComment>allMainComment(int membershipCode);
	ArrayList<MainComment>mainReComment(int membershipCode, int mainCommentCode);
}
