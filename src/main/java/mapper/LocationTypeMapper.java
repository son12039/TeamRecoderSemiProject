package mapper;

import java.util.List;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.Membership;
import com.damoim.model.vo.TypeCategory;

public interface LocationTypeMapper {
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
	
	
	
	//맴버쉽 모든거 보여주기
	List<Membership> AllMembership();
}
