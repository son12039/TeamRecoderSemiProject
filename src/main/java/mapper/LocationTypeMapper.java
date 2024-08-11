package mapper;

import java.util.List;

import com.damoim.model.vo.LocationCategory;
import com.damoim.model.vo.TypeCategory;

public interface LocationTypeMapper {
	//위치
	//대분류 보여주기
	List<LocationCategory> allLocationLarge();
	List<LocationCategory> allLocation();
	
	//분류
	//대분류 보여주기
	List<TypeCategory> allType();
	List<TypeCategory> TypeCategoryLarge(String typeSName);
}
