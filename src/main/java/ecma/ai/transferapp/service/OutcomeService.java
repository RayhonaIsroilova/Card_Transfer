package ecma.ai.transferapp.service;

import ecma.ai.transferapp.entity.Card;
import ecma.ai.transferapp.entity.Income;
import ecma.ai.transferapp.entity.Outcome;
import ecma.ai.transferapp.payload.ApiResponse;
import ecma.ai.transferapp.payload.OutcomeDto;
import ecma.ai.transferapp.repository.CardRepository;
import ecma.ai.transferapp.repository.IncomeRepository;
import ecma.ai.transferapp.repository.OutcomeRepository;
import ecma.ai.transferapp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutcomeService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    OutcomeRepository outcomeRepository;

    public List<Outcome> getAll(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        return outcomeRepository.findByFromCard_Username(username);
    }

    public Outcome getOne(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        Optional<Outcome> byId = outcomeRepository.findById(id);
        if (!byId.isPresent()) return new Outcome();
        if (!username.equals(byId.get().getFromCard().getUsername())) return new Outcome();
        return byId.get();
    }



    public ApiResponse add(OutcomeDto outcomeDto, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        token = token.substring(7);

        String username = jwtProvider.getUsernameFromToken(token);//username


        Optional<Card> optionalCardFrom = cardRepository.findById(outcomeDto.getFromCardId());
        if (!optionalCardFrom.isPresent()) return new ApiResponse("from card topilmadi!", false);

        Optional<Card> optionalCardTo = cardRepository.findById(outcomeDto.getToCardId());
        if (!optionalCardTo.isPresent()) return new ApiResponse("To card topilmadi!", false);

        if (outcomeDto.getAmount() < 0) return new ApiResponse("Manfiy bo'lamsin", false);

        if (!username.equals(optionalCardFrom.get().getUsername()))
            return new ApiResponse(" szni kartesmas bu !", false);

        if (outcomeDto.getAmount() > optionalCardFrom.get().getBalance() - (outcomeDto.getAmount() * outcomeDto.getCommission()))
            return new ApiResponse("Pul yetmadi!", false);

        if (optionalCardFrom.get().getCardNumber().equals(optionalCardTo.get().getCardNumber()))
            return new ApiResponse("O'zingdan o'zingga pul ko'chirma mashennik!", false);

        if (optionalCardFrom.get().isActive() && optionalCardTo.get().isActive()) {
            Outcome outcome = new Outcome();

            outcome.setAmount(outcomeDto.getAmount());
            outcome.setCommissionPercent(outcomeDto.getCommission());
            outcome.setFromCard(optionalCardFrom.get());
            outcome.setToCard(optionalCardTo.get());
            outcome.setDate(new Date(System.currentTimeMillis()));

            outcomeRepository.save(outcome);
            Income income = new Income();

            income.setAmount(outcomeDto.getAmount());
            income.setFromCard(optionalCardFrom.get());
            income.setToCard(optionalCardTo.get());
            income.setDate(new Date(System.currentTimeMillis()));

            incomeRepository.save(income);

            Card cardFrom = optionalCardFrom.get();

            Card cardTo = optionalCardTo.get();

            cardTo.setBalance(cardTo.getBalance() + outcomeDto.getAmount());

            double total = cardFrom.getBalance() - outcomeDto.getAmount() - (outcomeDto.getAmount() * outcomeDto.getCommission());
            cardFrom.setBalance(total);

            cardRepository.save(cardFrom);
            cardRepository.save(cardTo);

        }

        return new ApiResponse("Tugadiiii!!!", true);
    }

    public ApiResponse delete(Integer id,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        Optional<Outcome> byId = outcomeRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("Id not found!",false);
        if (!username.equals(byId.get().getFromCard().getUsername()))
            return new ApiResponse("Username not found!",false);
         outcomeRepository.deleteById(id);
         return new ApiResponse("Delete success",true);

    }
}
