package ecma.ai.transferapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Card fromCard; //Jafar 8600

    @ManyToOne
    private Card toCard; // M 0011

    private double amount;

    private Date date;

    private double commissionPercent;

}
