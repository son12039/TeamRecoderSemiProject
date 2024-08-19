package mapper;

import java.util.List;

import com.damoim.model.dto.LocationTypeDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Member;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;

public interface LocationTypeMapper {
	
	// 08_14
	/* 위치 영역 */
	// 1.화면 옵션에 도시 이름 전체 리스트 보여주기
	List<String> locLaNameList();
	// 도시 소분류 뽑기
	List<String> locSNameList(String laName);
	
	
	/* 타입 영역 */
	List<String> typeLaNameList();
	
	List<String> typeSNameList(String typeLaName);
	
	
	// 3. 파라미터 값을 받고 code 숫자 뽑아오기
	List<Integer> searchList(SearchDTO dto);
	
	List<MemberLocTypeDTO> memberLocTypeList(SearchDTO dto);
	List<LocationCategory> locationList(int code);
	List<TypeCategory> typeList(int code);
	
	
	//맴버 닉네임 뽑기
	Member selectMemberNickName(int code);

	
}
