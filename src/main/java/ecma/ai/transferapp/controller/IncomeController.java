package ecma.ai.transferapp.controller;

import ecma.ai.transferapp.entity.Income;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @GetMapping
    public HttpEntity<?> getAll(HttpServletRequest request){
        List<Income> all = incomeService.getAll(request);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id,HttpServletRequest request){
        Income one = incomeService.getOne(id, request);
        return ResponseEntity.ok(one);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id,HttpServletRequest request){
        ApiResponse delete = incomeService.delete(id, request);
        return ResponseEntity.status(delete.isSuccess()?200:409).body(delete);
    }
}
