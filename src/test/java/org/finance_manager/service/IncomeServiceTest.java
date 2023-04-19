package org.finance_manager.service;

import jakarta.persistence.EntityManager;
import org.finance_manager.DbConnection;
import org.finance_manager.dto.SimpleIncomeDto;
import org.finance_manager.entity.Income;
import org.finance_manager.repository.IncomeRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IncomeServiceTest {
    private final IncomeRepository incomeRepository = new IncomeRepository();
    private final IncomeService incomeService = new IncomeService(incomeRepository);

    @BeforeEach
    public void setUp() {
        EntityManager entityManager = DbConnection.getEntityManager();
        Income income = new Income();
        income.setIncomeSum(154.45);
        income.setIncomeDate(LocalDate.of(2023,04,19));
        income.setComment("salary");
        entityManager.getTransaction().begin();
        entityManager.persist(income);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterEach
    public void cleanAfterEach() {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Income i").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void shouldFindOneIncome() {
        List<SimpleIncomeDto> allIncomes = incomeService.findAll();
        Assertions.assertEquals(1, allIncomes.size());
    }

    @Test
    public void shouldFindTwoIncomesAfterAddingTheSecondIncomeWithComment() {
        incomeService.addIncome(15D,"from my dad");
        List<SimpleIncomeDto> allIncomes = incomeService.findAll();
        Assertions.assertEquals(2, allIncomes.size());
    }

    @Test
    public void shouldFindTwoIncomesAfterAddingTheSecondIncomeWithoutComment() {
        incomeService.addIncome(15D,"");
        List<SimpleIncomeDto> allIncomes = incomeService.findAll();
        Assertions.assertEquals(2, allIncomes.size());
    }

    @Test
    public void shouldFindOneIncomeAfterAddingAndDeletingTheSecondIncome() {
        incomeService.addIncome(785D, "scholarship");
        incomeService.deleteIncomeByIncomeSumAndDate(785D, LocalDate.of(2023,04,19));
        List<SimpleIncomeDto> allIncomes = incomeService.findAll();
        Assertions.assertEquals(1, allIncomes.size());
    }

    @Test
    public void shouldFindOneIncomeByTheRangeOfDates() {
        List<SimpleIncomeDto> incomesByTheRangeOfDates = incomeService.findIncomesByTheRangeOfDates
                (LocalDate.of(2023, 04, 19), LocalDate.of(2023, 04, 19));
        Assertions.assertEquals(1, incomesByTheRangeOfDates.size());
    }

    @Test
    public void shouldFindNoIncomesAfterDeletingByIncomeSumAndDate() {
        incomeService.deleteIncomeByIncomeSumAndDate(154.45D, LocalDate.of(2023, 04,19));
        List<SimpleIncomeDto> allIncomes = incomeService.findAll();
        Assertions.assertEquals(0, allIncomes.size());
    }

    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            incomeService.addIncome(0D, "");
        });

        String expectedMessage = "The income sum cannot be 0 or less!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
