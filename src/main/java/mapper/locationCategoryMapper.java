package mapper;

import java.util.List;

import com.damoim.model.vo.LocationCategory;

public interface locationCategoryMapper {
	List<LocationCategory> allLocationLarge();
	List<LocationCategory> allLocationSmall(String location);
}
