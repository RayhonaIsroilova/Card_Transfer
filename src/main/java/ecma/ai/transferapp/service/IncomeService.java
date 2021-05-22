package ecma.ai.transferapp.service;

import ecma.ai.transferapp.entity.Income;
import ecma.ai.transferapp.entity.Outcome;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.repository.IncomeRepository;
import ecma.ai.transferapp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IncomeRepository incomeRepository;

    public List<Income> getAll(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        return incomeRepository.findByFromCard_Username(username);
    }

    public Income getOne(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        Optional<Income> byId = incomeRepository.findById(id);
        if (!byId.isPresent()) return new Income();
        if (!username.equals(byId.get().getFromCard().getUsername())) return new Income();
        return byId.get();
    }

    public ApiResponse delete(Integer id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        Optional<Income> byId = incomeRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("Id not found!",false);
        if (!username.equals(byId.get().getFromCard().getUsername()))
            return new ApiResponse("Username not found!",false);
        incomeRepository.deleteById(id);
        return new ApiResponse("Delete success",true);

    }

}
