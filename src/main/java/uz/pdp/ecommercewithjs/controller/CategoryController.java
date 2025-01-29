package uz.pdp.ecommercewithjs.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommercewithjs.dto.CategoryDTO;
import uz.pdp.ecommercewithjs.entity.Category;
import uz.pdp.ecommercewithjs.repo.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/category")

public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @PostMapping
    public void save(@RequestBody CategoryDTO categoryDTO){
    Category category = Category
        .builder()
        .name(categoryDTO.getName())
        .build();
    categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id){
        categoryRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody CategoryDTO categoryDTO, @PathVariable Integer id){
    Category category = Category
            .builder()
            .id(id)
            .name(categoryDTO.getName())
            .build();
    categoryRepository.save(category) ;
    }




}
