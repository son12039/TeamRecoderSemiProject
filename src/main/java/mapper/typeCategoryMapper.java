package mapper;

import java.util.List;

import com.damoim.model.vo.TypeCategory;

public interface typeCategoryMapper {
	
	List<TypeCategory> allType();
    List<TypeCategory> TypeCategoryLarge(String typeSName);
}
