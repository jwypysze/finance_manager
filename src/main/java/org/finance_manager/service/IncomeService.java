package org.finance_manager.service;

import jakarta.persistence.NoResultException;
import org.finance_manager.dto.SimpleIncomeDto;
import org.finance_manager.entity.Income;
import org.finance_manager.repository.IncomeRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }


    public void addIncome(Double incomeSum, String comment) throws IllegalArgumentException {
        if (incomeSum > 0) {
            Income income = new Income(incomeSum, comment);
            incomeRepository.insert(income);
        } else {
            throw new IllegalArgumentException("The income sum cannot be 0 or less!");
        }
    }


    public List<SimpleIncomeDto> findAll() {
        Set<Income> incomes = incomeRepository.findAll();
        return incomes.stream()
                .map(income -> new SimpleIncomeDto(income.getId(),
                        income.getIncomeSum(), income.getIncomeDate(), income.getComment()))
                .toList();
    }

    public void deleteIncomeByIncomeSumAndDate(Double incomeSum, LocalDate incomeDate) throws NoResultException {
        Income byIncomeSumAndDate = incomeRepository.findByIncomeSumAndDate(incomeSum, incomeDate);
        incomeRepository.deleteIncomeByIncomeSumAndDate(byIncomeSumAndDate);
    }

    public List<SimpleIncomeDto> findIncomesByTheRangeOfDates(LocalDate fromDate, LocalDate toDate) {
        List<Income> incomesByTheRangeOfDates = incomeRepository.findIncomesByTheRangeOfDates(fromDate, toDate);
        return incomesByTheRangeOfDates.stream()
                .map(i -> new SimpleIncomeDto(i.getId(), i.getIncomeSum(),
                        i.getIncomeDate(), i.getComment()))
                .toList();
    }
}
