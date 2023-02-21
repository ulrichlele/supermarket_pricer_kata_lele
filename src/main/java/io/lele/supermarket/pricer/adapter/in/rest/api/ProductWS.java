package io.lele.supermarket.pricer.adapter.in.rest.api;

import io.lele.supermarket.pricer.application.port.in.ProductRequestPort;
import io.lele.supermarket.pricer.application.port.in.dto.product.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.product.UpdateProductDTO;
import io.lele.supermarket.pricer.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/product")
public class ProductWS {

    ProductRequestPort productService;

    public ProductWS(ProductRequestPort productService){
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Product create(@RequestBody CreateProductDTO dto){
      return productService.create(dto);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product findRecordById(@PathVariable String id){
        return productService.findRecordById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Product update(@RequestBody UpdateProductDTO dto){
        return productService.update(dto);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public Set<Product> findAll(){
        return productService.findAll();
    }
}
