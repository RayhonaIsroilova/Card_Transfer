package ecma.ai.transferapp.controller;

import ecma.ai.transferapp.entity.Card;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.payload.CardDto;
import ecma.ai.transferapp.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping
    public HttpEntity<?> getAll(HttpServletRequest request){
        List<Card> all = cardService.getAll(request);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id,HttpServletRequest request){
        Card one = cardService.getOne(id, request);
        return ResponseEntity.ok(one);
    }

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody CardDto dto, HttpServletRequest httpServletRequest) {

        ApiResponse response = cardService.add(dto, httpServletRequest);

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    HttpEntity<?> edit(@PathVariable Integer id, @RequestBody CardDto cardDto, HttpServletRequest httpServletRequest) {
        ApiResponse response = cardService.edit(id, cardDto, httpServletRequest);

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id,HttpServletRequest request){
        ApiResponse delete = cardService.delete(id, request);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }

}
