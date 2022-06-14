package com.app.products.Services;

import com.app.products.Models.Carousel;
import com.app.products.Models.Category;
import com.app.products.Models.Discount;
import com.app.products.Models.Product;
import com.app.products.dto.CategoryRepository;
import com.app.products.dto.ProductRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CategoryRepository categoryRepository;
//    @Autowired
//    private CouponRepository couponRepository;

    public void  saveProductToDB(MultipartFile file,String name,String description
            ,int price,String brand,int quantity,String categories)
    {
        Product p = new Product();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a valid file");
        }
        try {
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setDescription(description);

        p.setName(name);
        p.setPrice(price);
        p.setBrand(brand);
        p.setQuantity(quantity);
      //  Coupon c = new Coupon();
      //  c.setDiscount(0);
      //  p.setDiscount(c);
        p = addCategoriesToProduct(p,categories);
        productRepo.save(p);
    }
    
    private Product addCategoriesToProduct(Product p ,String categories) {
        String [] catsep = categories.split(",");
        Category category = null;
        for(String str : catsep) {
            category = categoryRepository.findById(Long.parseLong(str)).get();
            p.getCategories().add(category);
        }
        return p;
    }
    
    
    
    public List<Product> getAllProduct()
    {
        return productRepo.findAll();
    }
    public void deleteProductById(Long id)
    {
        productRepo.deleteById(id);
    }
    public void changeProductName(Long id ,String name)
    {
        Product p = new Product();
        p = productRepo.findById(id).get();
        p.setName(name);
        productRepo.save(p);
    }
    public void changeProductDescription(Long id , String description)
    {
        Product p = new Product();
        p = productRepo.findById(id).get();
        p.setDescription(description);
        productRepo.save(p);
    }
    public void changeProductPrice(Long id,int price)
    {
        Product p = new Product();
        p = productRepo.findById(id).get();
        p.setPrice(price);
        productRepo.save(p);
    }

    public void changeProductBrand(Long id,String brand)
    {
        Product p;
        p = productRepo.findById(id).get();
        p.setBrand(brand);
        productRepo.save(p);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    public void addImageToProduct(MultipartFile file, Long id) {
        Product p = productRepo.findById(id).get();
        Carousel carousel = new Carousel();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if(filename.contains(".."))
        {
            System.out.println("Not a valid file");
        }
        try {
            carousel.setImage(resizeImageForUse(Base64.getEncoder().encodeToString(file.getBytes()),400,400));
        }catch (IOException e){
            e.printStackTrace();
        }
        p.getCarousel().add(carousel);
        productRepo.save(p);
    }

    public void changeDiscount(Long id, int discount) {
        Product p = productRepo.findById(id).get();
        if(p.getDiscount()== null) {
            Discount d = new Discount();
            d.setDiscount(discount);
            p.setDiscount(d);
        }else{
            p.getDiscount().setDiscount(discount);
        }
        productRepo.save(p);

    }

    public void changeProductQuantity(Long id, int quantity) {
        Product p = productRepo.findById(id).get();
        p.setQuantity(quantity);
        productRepo.save(p);

    }

    public void changeProductDiscount(Long id, int discount) {
        Product p = productRepo.findById(id).get();
        p.getDiscount().setDiscount(discount);
        productRepo.save(p);

    }
    public String resizeImageForUse(String src, int width, int height)
    {
        BufferedImage image = base64ToBufferedImage(src);
        try {
            image = resizeImage(image,width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return bufferedImageToBase64(image);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;


    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) throws IOException {
        ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
        try {
            Thumbnails.of(image).size(width,height).outputFormat("JPEG").outputQuality(1).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }

    private BufferedImage base64ToBufferedImage(String base64Image){
        BufferedImage image = null;
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        try {
            image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    private String bufferedImageToBase64(BufferedImage image) throws UnsupportedEncodingException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"JPEG", Base64.getEncoder().wrap(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString(StandardCharsets.ISO_8859_1.name());
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).get();
    }

    public List<Product> searchProductByName(String value) {
        return productRepo.findByNameContainingIgnoreCase(value);
    }

    public List<String> getAllBrands() {
        return productRepo.findAllBrandsDistinct();
    }

}
