package Queue;

import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPriorityQueryImpTest {
    private PriorityQueue<Person> standartQuery;
    private CustomPriorityQueryImp<Person> customQuery;

    @BeforeEach
    void setUp() {
        standartQuery = new PriorityQueue<>((p1,p2)->p1.getName().compareTo(p2.getName()));
        standartQuery.add(Person.builder().id(1).name("A").salary(0.4).build());
        standartQuery.add(Person.builder().id(2).name("C").salary(1.0).build());
        standartQuery.add(Person.builder().id(3).name("D").salary(5.0).build());
        customQuery = new CustomPriorityQueryImp<>((p1,p2)->p1.getName().compareTo(p2.getName()));
        customQuery.add(Person.builder().id(1).name("A").salary(0.4).build());
        customQuery.add(Person.builder().id(2).name("C").salary(1.0).build());
        customQuery.add(Person.builder().id(3).name("D").salary(5.0).build());
    }

    @Test
    void add() {
        assertThat(customQuery.add(Person.builder().id(10).name("U").salary(0.4).build())).isEqualTo(true);
    }

    @Test
    void poll() {
        assertThat(customQuery.poll()).isEqualTo(standartQuery.poll());
    }

    @Test
    void peek() {
        assertThat(customQuery.peek()).isEqualTo(standartQuery.peek());
        customQuery.poll();
        standartQuery.poll();
        assertThat(customQuery.peek()).isEqualTo(standartQuery.peek());
    }

    @Test
    void size() {
        int size = customQuery.size();
        int expend = size-1;
        customQuery.poll();

        assertThat(customQuery.size()).isEqualTo(expend);
    }

    @Test
    void isEmpty() {
        assertThat(customQuery.isEmpty()).isEqualTo(false);
    }
}