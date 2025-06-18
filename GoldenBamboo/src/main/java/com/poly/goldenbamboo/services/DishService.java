package com.poly.goldenbamboo.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.goldenbamboo.dtos.Discount_DishesDTO;
import com.poly.goldenbamboo.dtos.DishesDTO;
import com.poly.goldenbamboo.dtos.FoodDTO;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.exception.ResourceNoFoundException;
import com.poly.goldenbamboo.mappers.CategoryMapper;
import com.poly.goldenbamboo.mappers.DishesMapper;
import com.poly.goldenbamboo.mappers.FoodMapper;
import com.poly.goldenbamboo.repositories.DishJPA;
import com.poly.goldenbamboo.util.ImageStorageUtil;

@Service
public class DishService {
	@Autowired
    private DishJPA dishesJPA;

    public DishesDTO createDishes(DishesDTO dishesBeans, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = ImageStorageUtil.storeImage(imageFile);
            dishesBeans.setImage(fileName);
        }
        DishEntity dishesEntity = DishesMapper.mapToDishesEntity(dishesBeans);
        DishEntity saveDishes = dishesJPA.save(dishesEntity);
        return DishesMapper.mapToDishesBeans(saveDishes);
    }

    public List<DishesDTO> getAllDishes() {
        List<DishEntity> dishes = dishesJPA.findAll();
        return dishes.stream().map((dishesEntity) -> DishesMapper.mapToDishesBeans(dishesEntity)).toList();
    }

    public List<DishesDTO> getDiscountedDishes() {
        List<DishEntity> dishes = dishesJPA.findDiscountedDishes();
        return dishes.stream()
                .map(DishesMapper::mapToDishesBeans)
                .toList();
    }

    public List<DishesDTO> getDishesWithDiscountFlag() {
        List<DishesDTO> allDishes = getAllDishes();
        // Lấy danh sách các dish ID có giảm giá
        List<Integer> discountedIds = dishesJPA.findDiscountedDishes()
                .stream()
                .map(DishEntity::getId)
                .collect(Collectors.toList());
        
        for (DishesDTO dish : allDishes) {
            if (discountedIds.contains(dish.getId())) {
                // Giả sử bạn có cách lấy phần trăm giảm giá cho món ăn này
                // Ở đây ví dụ sử dụng mức giảm cố định 10%
                Discount_DishesDTO discountDTO = new Discount_DishesDTO();
                discountDTO.setDishId(dish.getId());
                discountDTO.setDiscountPercentage(new BigDecimal("10")); // ví dụ 10%
                
                // Gọi cập nhật giảm giá
                applyDiscountToDish(discountDTO);
                
                // Lấy lại dish đã cập nhật để hiển thị finalPrice
                DishesDTO updatedDish = getDishesById(dish.getId());
                dish.setPrice(updatedDish.getPrice());
                dish.setFinalPrice(updatedDish.getFinalPrice());
                dish.setDiscounted(true);
            } else {
                dish.setDiscounted(false);
            }
        }
        
        return allDishes;
    }

    public void applyDiscountToDish(Discount_DishesDTO discountDishesDTO) {
        Optional<DishEntity> optionalDish = dishesJPA.findById(discountDishesDTO.getDishId());
        if (optionalDish.isPresent()) {
            DishEntity dish = optionalDish.get();
            // Sử dụng BigDecimal để tính toán
            BigDecimal discountPercentage = discountDishesDTO.getDiscountPercentage();
            BigDecimal originalPrice = dish.getPrice();

            // Tính số tiền được giảm: discountAmount = originalPrice * discountPercentage /
            // 100
            BigDecimal discountAmount = originalPrice.multiply(discountPercentage)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

            // Tính số tiền còn lại sau khi giảm
            BigDecimal remainingPrice = originalPrice.subtract(discountAmount);

            // Nếu muốn lưu lại giá mới cho món ăn (update vào DB), gán:
            dish.setPrice(remainingPrice);
            dishesJPA.save(dish);

            // Nếu không thay đổi giá trong DB mà chỉ hiển thị, bạn có thể gán thêm vào DTO.
            // Ví dụ, nếu bạn dùng DishesMapper để mapping, hãy thêm logic gán finalPrice.
        }
    }
    public DishesDTO getDishesById(int dishesId) {
        DishEntity dis = dishesJPA.findById(dishesId)
                .orElseThrow(() -> new ResourceNoFoundException("Dishes not found with id: " + dishesId));
        return DishesMapper.mapToDishesBeans(dis);
    }

    public void deleteDishes(int dishesId) {
        DishEntity dishesEntity = dishesJPA.findById(dishesId)
                .orElseThrow(() -> new ResourceNoFoundException("Dishes not found with id: " + dishesId));
        dishesJPA.delete(dishesEntity);
    }

    public DishesDTO updateDishes(int dishesId, DishesDTO dishesBeans, MultipartFile imageFile) {
        DishEntity dishesEntity = dishesJPA.findById(dishesId)
                .orElseThrow(() -> new ResourceNoFoundException("Dishes not found with id: " + dishesId));
        if (dishesBeans.getName() != null) {
            dishesEntity.setName(dishesBeans.getName());
        }
        if (dishesBeans.getPrice() != null) {
            dishesEntity.setPrice(dishesBeans.getPrice());
        }
        if (dishesBeans.getDescription() != null) {
            dishesEntity.setDescription(dishesBeans.getDescription());
        }
        if (dishesBeans.getImage() != null) {
            dishesEntity.setImage(dishesBeans.getImage());
        }
        if (dishesBeans.getCategoryId() != null) {
            dishesEntity.setCategory(CategoryMapper.mapToCategoryEntityById(dishesBeans.getCategoryId()));
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = ImageStorageUtil.storeImage(imageFile);
            dishesEntity.setImage(fileName);
        }
        DishEntity updatedDishes = dishesJPA.save(dishesEntity);
        return DishesMapper.mapToDishesBeans(updatedDishes);
    }

    public List<DishEntity> getDefaultMenuDishesByBranchAndCategory(Integer branchId, Integer categoryId) {
	//	return dishJPA.findDefaultMenuDishesByBranchAndCategory(branchId, categoryId);
		return null;
	}
}
