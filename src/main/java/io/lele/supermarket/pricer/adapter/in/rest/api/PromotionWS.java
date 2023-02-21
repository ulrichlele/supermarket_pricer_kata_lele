package io.lele.supermarket.pricer.adapter.in.rest.api;

import io.lele.supermarket.pricer.application.port.in.PromotionRequestPort;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.CreatePromotionDTO;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.UpdatePromotionDTO;
import io.lele.supermarket.pricer.domain.Promotion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/promotion")
public class PromotionWS {

    PromotionRequestPort requestPort;

    public PromotionWS(PromotionRequestPort requestPort){
        this.requestPort = requestPort;
    }

    @PostMapping
    public ResponseEntity<Promotion> create(@RequestBody CreatePromotionDTO dto){
        Promotion promotion = requestPort.create(dto);
      return ResponseEntity.ok(promotion);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Promotion> findRecordById(@PathVariable String id){
        return ResponseEntity.ok(requestPort.findRecordById(id));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Promotion update(@RequestBody UpdatePromotionDTO dto){
        return requestPort.update(dto);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public Set<Promotion> findAll(){
        return requestPort.findAll();
    }
}
