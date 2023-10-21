package Queue;

import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPriorityQueryImpTest {
    private PriorityQueue<Person> standartQuery;
    private CustomPriorityQueryImp<Person> customQuery;
    private PriorityQueue<Integer> standartQueryInt;
    private CustomPriorityQueryImp<Integer> customQueryInt;

    @BeforeEach
    void setUp() {
        standartQuery = new PriorityQueue<>((p1,p2)->p1.getName().compareTo(p2.getName()));
        standartQuery.add(Person.builder().id(1).name("A").salary(0.4).build());
        standartQuery.add(Person.builder().id(2).name("C").salary(1.0).build());
        standartQuery.add(Person.builder().id(3).name("D").salary(5.0).build());

        customQuery =  new CustomPriorityQueryImp<>((p1,p2)->p1.getName().compareTo(p2.getName()));
        customQuery.add(Person.builder().id(1).name("A").salary(0.4).build());
        customQuery.add(Person.builder().id(2).name("C").salary(1.0).build());
        customQuery.add(Person.builder().id(3).name("D").salary(5.0).build());

        standartQueryInt = new PriorityQueue<>();
        standartQueryInt.add(13);
        standartQueryInt.add(5);
        standartQueryInt.add(32);

        customQueryInt = new CustomPriorityQueryImp<>();
        customQueryInt.add(13);
        customQueryInt.add(5);
        customQueryInt.add(32);
    }

    @Test
    void add() {
        standartQuery.add(Person.builder().id(10).name("U").salary(0.4).build());

        assertThat(customQuery.add(Person.builder().id(10).name("U").salary(0.4).build())).isEqualTo(true);
        assertThat(customQuery.peek()).isEqualTo(standartQuery.peek());
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
        int expend = customQuery.size();

        assertThat(customQuery.size()).isEqualTo(expend);
        customQuery.poll();
        expend-=1;
        assertThat(customQuery.size()).isEqualTo(expend);
    }

    @Test
    void isEmpty() {
        boolean expected = customQuery.isEmpty();

        assertThat(customQuery.isEmpty()).isEqualTo(expected);
    }

    @Test
    void addWithInteger(){
        assertThat(customQueryInt.add(1)).isEqualTo(true);
    }

    @Test
    void pollWithInt(){
        assertThat(customQueryInt.poll()).isEqualTo(standartQueryInt.poll());
    }

    @Test
    void peekWithInt(){
        assertThat(customQueryInt.peek()).isEqualTo(standartQueryInt.peek());
        customQueryInt.poll();
        standartQueryInt.poll();
        assertThat(customQueryInt.peek()).isEqualTo(standartQueryInt.peek());
    }

}