package pl.allegro.umk.crazybill;

import org.junit.Test;
import pl.allegro.umk.crazybill.domain.Bill;
import pl.allegro.umk.crazybill.domain.BillPosition;
import pl.allegro.umk.crazybill.domain.BillResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BillCalculateTests {

    @Test
    public void should_calculate_bill() {
        // given
        List<String> pizzaPersons = new ArrayList();
        pizzaPersons.add("Jarek");

        List<BillPosition> positions = new ArrayList();
        positions.add(new BillPosition("Pizza", 20.0, pizzaPersons, UUID.randomUUID().toString()));

        Bill bill = new Bill("dummy_id", "dummy_name", positions, "email@domain.org");

        // when
        BillResult result = new BillCalculator().calculate(bill);

        // then
        assertThat(result.getPersonsCount()).isEqualTo(1);
        assertThat(result.getPriceForPerson("Jarek")).isEqualTo(20.0);
    }

    @Test
    public void should_calculate_bill_with_many_positions() throws Exception {
        // given
        List<String> kebabPersons = new ArrayList();
        kebabPersons.add("Jarek");
        kebabPersons.add("Michal");

        List<BillPosition> positions = new ArrayList<>();
        positions.add(new BillPosition("Kebab", 22.0, kebabPersons, UUID.randomUUID().toString()));

        List<String> colaPersons = new ArrayList<>();
        colaPersons.add("Michal");

        positions.add(new BillPosition("Cola", 4, colaPersons, UUID.randomUUID().toString()));

        Bill bill = new Bill("dummy_id", "dummy_name", positions, "email@domain.org");

        // when
        BillResult result = new BillCalculator().calculate(bill);

        // then
        assertThat(result.getPersonsCount()).isEqualTo(2);
        assertThat(result.getPriceForPerson("Jarek")).isEqualTo(11);
        assertThat(result.getPriceForPerson("Michal")).isEqualTo(15);
    }

    @Test
    public void should_calculate_bill_with_builder() throws Exception {
        Bill bill = Bill.builder()
                .paidFor("Kebab", 22.0).by("Michal", "Jarek")
                .paidFor("Cola", 4.00).by("Michal")
                .build();

        // when
        BillResult result = new BillCalculator().calculate(bill);

        // then
        assertThat(result.getPersonsCount()).isEqualTo(2);
        assertThat(result.getPriceForPerson("Jarek")).isEqualTo(11);
        assertThat(result.getPriceForPerson("Michal")).isEqualTo(15);
    }
}
