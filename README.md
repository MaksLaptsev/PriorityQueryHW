## CustomPriorityQuery
CustomPriorityQuery - реализация стандартной PriorityQueue

## Задание
Требовалось написать собственную реализацию PriorityQueue, соблюдая следующие условия:
- Интерфейс должен иметь методы `add`, `peek`,  `poll`.
- Очередь должна поддерживать хранение любых элементов, реализующих Comparable.
- Реализация должна использовать по умолчанию структуру данных "Минимальная двоичная куча", то есть в вершине лежит минимальный элемент.
- Элементы требуется хранить в обычном Java массиве.
- Массив должен динамически расширяться при добавлении новых элементов.
- Начальный размер массива - 8.
- Должны быть реализованы процедуры `siftUp` и `siftDown`.
- Рекурсию использовать нельзя - итерацию осуществляем по индексам.
## Использование
Для использования необходимо: 
1. Создать экземпляр CustomPriorityQuery<Object> при необходимости добавив свой компаратор:
   ```java
    // With comparable elements
    CustomPriorityQuery<Integer> customQueue = new CustomPriorityQueryImp<>();

    // With non-comparable elements and a custom comparator
    Comparator<Person> comparator = (p1,p2)->p1.getName().compareTo(p2.getName());
    CustomPriorityQuery<Person> customQueuePerson = new CustomPriorityQueryImp<>(comparator);
   ```

  2. Использовать `add` для добавления элемента, `peek` для просмотра элемента с наивысшим приоритетом, `poll` для получения элемента с наивысшим приоритетом и удаление его из коллекции.
```java
    // Add
    customQueue.add(12);
    customQueue.add(4);
    customQueue.add(15);

    // peek
    customQueue.size(); //size: 3
    customQueue.peek(); // show element '4'
    customQueue.size(); //size: 3

    //poll
    customQueue.size(); //size: 3
    customQueue.poll(); //get element '4'
    customQueue.size(); //size: 2
```