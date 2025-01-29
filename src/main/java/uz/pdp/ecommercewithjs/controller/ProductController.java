package uz.pdp.ecommercewithjs.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommercewithjs.dto.ProductDTO;
import uz.pdp.ecommercewithjs.entity.Product;
import uz.pdp.ecommercewithjs.repo.AttachmentRepository;
import uz.pdp.ecommercewithjs.repo.CategoryRepository;
import uz.pdp.ecommercewithjs.repo.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository, AttachmentRepository attachmentRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @GetMapping
    public List<Product> getAllProducts(){
       return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getOneProduct(@PathVariable Integer id){
        return productRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public void saveProduct(@RequestBody ProductDTO productDTO){
    Product product = Product
            .builder()
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .category(categoryRepository.findById(productDTO.getCategoryId()).orElseThrow())
            .attachment(attachmentRepository.findById(productDTO.getAttachmentId()).orElseThrow())
            .build();
    productRepository.save(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Integer id){
        Product product = Product
                .builder()
                .id(id)
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .category(categoryRepository.findById(productDTO.getCategoryId()).orElseThrow())
                .attachment(attachmentRepository.findById(productDTO.getAttachmentId()).orElseThrow())
                .build();
        productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id){
        System.out.println("id = " + id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productRepository.deleteById(product.getId());
            System.out.println("product ochirildi");
        }
        else {
            System.out.println("Product mavjud emas");
        }
    }
}
