package mapper;

import java.util.List;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;

public interface LocationTypeMapper {
	
	// 맴버쉽 조인된 location loc_la_name 보여주기
	List<Membership> AllMembershipLocationLaname();
	// 맴버쉽 조인된 location loc_s_name 보여주기
	List<Membership> AllMembershipLocationSname(String locLaName);
	// 맴버쉽 조인된 type type_la_name 보여주기
	List<Membership> AllMembershipTypeLaname();
	// 맴버쉽 조인된 type type_s_name 보여주기	
	List<Membership> AllMembershipTypeSname(String typeLaName);
	
	
	
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
