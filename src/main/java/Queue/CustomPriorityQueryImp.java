package Queue;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Реализация стандартной PriorityQuery
 */
@SuppressWarnings("unchecked")
public class CustomPriorityQueryImp<T> implements CustomPriorityQuery<T>{
    private static final int DEFAULT_SIZE = 8;
    private final Comparator<? super T> comparator;
    private Object[] array;
    private int size;


    public CustomPriorityQueryImp() {
        this(null,DEFAULT_SIZE);
    }

    public CustomPriorityQueryImp(int initialCapacity) {
        this(null,initialCapacity);
    }

    public CustomPriorityQueryImp(Comparator<? super T> comparator) {
        this(comparator,DEFAULT_SIZE);
    }

    /**
     * Конструктор для инициализации коллекции
     * @param comparator - компаратор, который необходим при использовании,
     *                     при его отсуствии - будет использован тот, который был использован при переопределении его
     *                     в классе объекта(которые будут хранится в нашей колекции)
     * @param initialCapacity - стартовый размер нашей колекции, при отсуствии будет равен {@link CustomPriorityQueryImp#DEFAULT_SIZE},
     *                          при initialCapacity < 1 будет проброшен IllegalArgumentException
     */
    public CustomPriorityQueryImp(Comparator<? super T> comparator, int initialCapacity) {
        this.comparator = comparator;
        if(initialCapacity < 1){
            throw new IllegalArgumentException("Capacity can not be < 1!!");
        }
        this.array = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Добавление элемента в коллекцию
     * @param t - добавляемый элемент
     * @return - возвращает true при успехе
     */
    @Override
    public boolean add(T t) {
        if(t == null){
            throw new NullPointerException();
        }
        int i = size;
        if (size >= array.length){
            upCapacity(i+1);
        }
        siftUp(i,t);
        size = i + 1;
        return true;
    }

    /**
     * Получить элемент с верхушки коллекции, элемент удаляется из коллекции
     * @return - T
     */
    @Override
    public T poll() {
        final T object = (T)array[0];
        if(object != null){
            final int lastObjectId = --size;
            final T lastObject = (T)array[lastObjectId];
            array[lastObjectId] = null;
            if(lastObjectId > 0){
                if(comparator != null){
                    siftDownWithComparator(0,lastObject,array,lastObjectId,comparator);
                }else {
                    siftDownWithComparable(0,lastObject,array,lastObjectId);
                }
            }
        }
        return object;
    }

    /**
     * Показать элемент с верхушки колекции, элемент остается в коллекции
     * @return - T
     */
    @Override
    public T peek() {
        return (T) array[0];
    }

    /**
     * Возвращает текущее кол-во элементов в коллекции
     * @return - int {@link CustomPriorityQueryImp#size}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, является ли коллекция пустой
     * @return - boolean
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Увелечение размера массива, подсмотренно в стандартной реализации {@link java.util.PriorityQueue}
     * @param startCapacity - минимально допустимый новый размер коллекции
     */
    private void upCapacity(int startCapacity){
        int oldCapacity = array.length;
        int newUpCapacity = newLength(oldCapacity,
                startCapacity - oldCapacity,
                oldCapacity < 64 ? oldCapacity + 2 : oldCapacity >> 1);
        array = Arrays.copyOf(array,newUpCapacity);
    }

    /**
     * Механизм подсчета увелечение размера массива, подсмотрена и взята реализация из {@link jdk.internal.util.ArraysSupport#newLength(int, int, int)}
     * @param oldLength - текущая длина массива (должна быть неотрицательной)
     * @param minGrowth - минимальная требуемая величина прироста (должна быть положительной)
     * @param prefGrowth -предпочтительная величина роста
     * @return - int
     */
    private int newLength(int oldLength, int minGrowth, int prefGrowth) {
                int prefLength = oldLength + Math.max(minGrowth, prefGrowth); // might overflow
        if (0 < prefLength && prefLength <= Integer.MAX_VALUE - 8) {
            return prefLength;
        } else {
            return hugeLength(oldLength, minGrowth);
        }
    }

    /**
     * Механизм подсчета увелечение размера массива, подсмотрена и взята реализация из {@link jdk.internal.util.ArraysSupport}
     * @param oldLength - текущая длина массива (должна быть неотрицательной)
     * @param minGrowth - минимальная требуемая величина прироста (должна быть положительной)
     * @return - int
     */
    private int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) {
            throw new OutOfMemoryError(
                    "Required array length " + oldLength + " + " + minGrowth + " is too large");
        } else if (minLength <= Integer.MAX_VALUE - 8) {
            return Integer.MAX_VALUE - 8;
        } else {
            return minLength;
        }
    }

    /**
     * Просеивание вверх, используется при добавлении нового элемента в коллекцию
     * @param i - индекс нового элемента(равен кол-ву элементов)
     * @param t - добавляемый элемент
     */
    private void siftUp(int i, T t){
        if(comparator != null){
            siftUpWithComparator(i,t, array, comparator);
        }else {
            siftUpWithComparable(i, t, array);
        }
    }

    /**
     * Процесс поиска места в массиве для нового элемента, при добавлении
     * @param i - начальный индекс элемента в массиве
     * @param t - сам элемент
     * @param arr - массив с элементами из коллекции
     * @param comparator - необходимая нам реализация компаратора для сравнения обьектов(задается в конструкторе)
     *                      {@link CustomPriorityQueryImp#CustomPriorityQueryImp(Comparator)}  CustomPriorityQueryImp}
     */
    private void siftUpWithComparator(int i, T t,Object[] arr, Comparator<? super T> comparator){
        while(i > 0){
            //Ищем индекс родительского объекта
            int parentObjectId = (i-2+1)/2;
            //Берем сам объект
            Object parent =  arr[parentObjectId];
            //Сравниваем объекты между собой
            if(comparator.compare(t,(T)parent) >= 0){
                break;
            }
            arr[i] = parent;
            i = parentObjectId;
        }
        arr[i] = t;
    }

    /**
     *{@link #siftUpWithComparable(int, Object, Object[])}
     */
    private void siftUpWithComparable(int i, T t,Object[] arr){
        Comparable<? super T> item = (Comparable<? super T>)t;
        while(i > 0){
            int parentObjectId = (i-2+1)/2;
            Object parent =  arr[parentObjectId];
            if(item.compareTo((T) parent) >= 0){
                break;
            }
            arr[i] = parent;
            i = parentObjectId;
        }
        arr[i] = t;
    }

    /**
     * Процесс поиска места в массиве для элемента, после удаления предыдущего
     * @param i - индекс, с которого начинается поиск
     * @param t - объект
     * @param arr - массив с элементами из коллекции
     * @param lastId - индекс последнего объекта в коллекции
     * @param comparator - необходимая нам реализация компаратора для сравнения обьектов(задается в конструкторе)
     *                     {@link CustomPriorityQueryImp#CustomPriorityQueryImp(Comparator)}  CustomPriorityQueryImp
     */
    private void siftDownWithComparator(int i, T t, Object[] arr, int lastId,Comparator<? super T> comparator){
        int half = lastId/2;
        while (i < half){
            //индекс левого потомка от верхушки
            int leftChildId = i*2+1;
            //индекс правого потомка от верхушки
            int rightChildId = leftChildId+1;
            T child = (T) arr[leftChildId];
            //Сравниваем левого и правого потомка, при удовлетворении проверки - берем правого потомка за основу
            if(rightChildId < lastId &&
                    comparator.compare(child,(T) arr[rightChildId]) > 0){
                leftChildId = rightChildId;
                child = (T)arr[leftChildId];
            }
            //в случае удовлетворения условия - новое место для нашего объекта найдено, цикл прерывается
            if(comparator.compare(t,child) <= 0){
                break;
            }
            arr[i] = child;
            i = leftChildId;
        }
        //объект перемещен на новое место
        arr[i] = t;
    }

    /**
     * {@link #siftDownWithComparator(int, Object, Object[], int, Comparator)}
     */
    private void siftDownWithComparable(int i, T t, Object[] arr, int lastId){
        Comparable<? super T> item = (Comparable<? super T>)t;
        int half = lastId/2;
        while (i < half){
            int leftChildId = i*2+1;
            int rightChildId = leftChildId+1;

            T child = (T) arr[leftChildId];
            if(rightChildId < lastId &&
                    ((Comparable<? super T>) child).compareTo((T)arr[rightChildId]) > 0){
                leftChildId = rightChildId;
                child = (T)arr[leftChildId];
            }
            if(item.compareTo(child) <= 0){
                break;
            }
            arr[i] = child;
            i = leftChildId;
        }
        arr[i] = item;
    }
}
