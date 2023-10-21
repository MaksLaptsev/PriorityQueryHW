import Queue.CustomPriorityQuery;
import Queue.CustomPriorityQueryImp;
import model.Person;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        Comparator<Person> comparator = (p1,p2)->p1.getName().compareTo(p2.getName());

        PriorityQueue<Person> defaultQueuePerson = new PriorityQueue<>(comparator);
        CustomPriorityQuery<Person> customQueuePerson = new CustomPriorityQueryImp<>(comparator);

        defaultQueuePerson.add(Person.builder().id(1).name("A").salary(0.4).build());
        defaultQueuePerson.add(Person.builder().id(2).name("C").salary(1.0).build());
        defaultQueuePerson.add(Person.builder().id(3).name("D").salary(5.0).build());

        customQueuePerson.add(Person.builder().id(1).name("A").salary(0.4).build());
        customQueuePerson.add(Person.builder().id(2).name("C").salary(1.0).build());
        customQueuePerson.add(Person.builder().id(3).name("D").salary(5.0).build());

        //Результаты работы методов poll у стандартной и каст коллекций
        {
            System.out.println("defaultQueuePerson size: "+defaultQueuePerson.size()+
                    "\ncustomQueuePerson size: "+customQueuePerson.size()+"\n");
            Person p1 = defaultQueuePerson.poll();
            Person p2 = customQueuePerson.poll();

            System.out.println("defaultQueuePerson poll: "+p1+
                    "\ncustomQueuePerson poll: "+p2+
                    "\nis equals object: "+p1.equals(p2)+"\n");
        }

        //Результаты работы методов peek после использования poll
        {
            System.out.println("defaultQueuePerson size after polling: "+defaultQueuePerson.size()+
                    "\ncustomQueuePerson size after polling: "+customQueuePerson.size()+"\n");

            System.out.println("defaultQueuePerson peek: "+defaultQueuePerson.peek()+
                    "\ncustomQueuePerson peek: "+customQueuePerson.peek()+
                    "\nis equals object: "+defaultQueuePerson.peek().equals(customQueuePerson.peek())+"\n");

        }

        //Добавление объекта без реализации компаратора
        try{
            CustomPriorityQuery<Person> customWithoutComparator = new CustomPriorityQueryImp<>();
            System.out.println("Попытка добавить объект без реализации компаратора");
            customWithoutComparator.add(Person.builder().id(1).name("A").salary(0.4).build());
        }catch (ClassCastException e){
            System.out.println(e);
        }
    }
}
