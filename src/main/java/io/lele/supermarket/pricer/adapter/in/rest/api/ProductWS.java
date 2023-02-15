package io.lele.supermarket.pricer.adapter.in.rest.api;

import io.lele.supermarket.pricer.application.port.in.ProductServicePort;
import io.lele.supermarket.pricer.application.port.in.dto.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.UpdateProductDTO;
import io.lele.supermarket.pricer.core.BaseResponse;
import io.lele.supermarket.pricer.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/product")
public class ProductWS {

    ProductServicePort productService;

    public ProductWS(ProductServicePort productService){
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Product> create(@RequestBody CreateProductDTO dto){
      return productService.create(dto);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Product> findRecordById(@PathVariable String id){
        return productService.findRecordById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Product> update(@RequestBody UpdateProductDTO dto){
        return productService.update(dto);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Set<Product>> findAll(){
        return productService.findAll();
    }
}
