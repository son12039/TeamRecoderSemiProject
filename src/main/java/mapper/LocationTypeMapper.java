package mapper;

import java.util.List;

import com.damoim.model.dto.LocationTypeDTO;
import com.damoim.model.dto.MemberLocTypeDTO;
import com.damoim.model.dto.SearchDTO;
import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;

public interface LocationTypeMapper {
	
	// 08_14
	/* 위치 영역 */
	// 1.화면 옵션에 도시 이름 전체 리스트 보여주기
	List<String> locLaNameList();
	// 도시 소분류 뽑기
	List<String> locSNameList(String laName);
	// 3. 파라미터 값을 받고 code 숫자 뽑아오기
	List<Integer> searchLocLaNameList(String location);
	
	List<MemberLocTypeDTO> memberLocTypeList(SearchDTO dto);
	List<LocationCategory> locationList(int code);
	List<TypeCategory> typeList(int code);
	
	
	/* 타입 영역 */
	List<String> typeLaNameList();
	//<select id="typeLaName" resultType="String">
	
	
	
	
	
	
	
	
	
	// 맴버쉽 조인된 location loc_la_name 보여주기
	List<Membership> AllMembershipLocationLaname();
	// 맴버쉽 조인된 location loc_s_name 보여주기
	List<Membership> AllMembershipLocationSname(String locLaName);
	// 맴버쉽 조인된 type type_la_name 보여주기
	List<Membership> AllMembershipTypeLaname();
	// 맴버쉽 조인된 type type_s_name 보여주기	
	List<Membership> AllMembershipTypeSname(String typeLaName);
	
	
	//분류하기
	List<Membership> classification(LocationTypeDTO dto);
	
	
	
	
	
	
	
	
	//맴버쉽 모든거 보여주기	
	List<Membership> AllMembership();
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//위치
	//대분류 보여주기
	List<LocationCategory> allLocationLarge();
	//all 클럽 위치 더미
	List<LocationCategory> allLocation();
	//소분류 찾아주기
	List<LocationCategory> locationDistinction(String locationDistinction);
	
//	<select id="locationDistinction" parameterType="String" resultMap="Location">
	
	//분류
	//대분류 보여주기
	List<TypeCategory> allType();
	List<TypeCategory> TypeCategoryLarge(String typeSName);
}
