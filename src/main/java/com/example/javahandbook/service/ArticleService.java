package com.example.javahandbook.service;

import com.example.javahandbook.model.ArticleData;
import com.example.javahandbook.model.MenuSection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    private final Map<String, ArticleData> articles = new LinkedHashMap<>();
    private final Map<String, String> menuGroups = new LinkedHashMap<>();

    public ArticleService() {
        menuGroups.put("java", "Java Core");
        menuGroups.put("oop", "OOP");
        menuGroups.put("collections", "Collections");
        menuGroups.put("spring", "Spring");

        articles.put("java-types", new ArticleData(
                "Типи даних у Java",
                "Primitive та reference types у Java.",
                "java-types",
                "java",
                """
                <h2>Що таке типи даних?</h2>

                <p>
                    Типи даних у Java визначають, які значення може зберігати змінна.
                    У Java є дві основні групи типів: primitive types та reference types.
                </p>

                <h2>Primitive types</h2>

                <ul>
                    <li>byte</li>
                    <li>short</li>
                    <li>int</li>
                    <li>long</li>
                    <li>float</li>
                    <li>double</li>
                    <li>char</li>
                    <li>boolean</li>
                </ul>

                <h2>Приклад</h2>

                <pre><code>public class Main {
    public static void main(String[] args) {
        int age = 30;
        double salary = 1500.50;
        boolean active = true;

        System.out.println(age);
        System.out.println(salary);
        System.out.println(active);
    }
}</code></pre>
                """
        ));

        articles.put("java-operators", new ArticleData(
                "Оператори у Java",
                "Арифметичні, логічні, порівняльні та інші оператори.",
                "java-operators",
                "java",
                """
                <h2>Що таке оператори?</h2>

                <p>
                    Оператори в Java використовуються для виконання дій над змінними та значеннями.
                    Наприклад: додавання, віднімання, порівняння або логічна перевірка.
                </p>

                <h2>Основні групи операторів</h2>

                <ul>
                    <li>арифметичні оператори;</li>
                    <li>оператори порівняння;</li>
                    <li>логічні оператори;</li>
                    <li>оператори присвоєння;</li>
                    <li>інкремент і декремент.</li>
                </ul>

                <h2>Приклад</h2>

                <pre><code>public class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 5;

        System.out.println(a + b);
        System.out.println(a > b);
        System.out.println(a == b);
    }
}</code></pre>
                """
        ));

        articles.put("java-methods", new ArticleData(
                "Методи у Java",
                "Методи, параметри, return та перевантаження методів.",
                "java-methods",
                "java",
                """
                <h2>Що таке метод?</h2>

                <p>
                    Метод — це блок коду, який виконує певну дію. Методи допомагають
                    структурувати програму, уникати дублювання коду та робити код зрозумілішим.
                </p>

                <h2>Приклад методу</h2>

                <pre><code>public class Main {

    public static void sayHello(String name) {
        System.out.println("Hello, " + name);
    }

    public static void main(String[] args) {
        sayHello("Java");
    }
}</code></pre>
                """
        ));

        articles.put("java-classes", new ArticleData(
                "Класи у Java",
                "Класи, об'єкти, поля, методи та конструктори.",
                "java-classes",
                "java",
                """
                <h2>Що таке клас?</h2>

                <p>
                    Клас — це шаблон для створення об'єктів. Він описує стан об'єкта через поля
                    та поведінку через методи.
                </p>

                <h2>Приклад класу</h2>

                <pre><code>public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printInfo() {
        System.out.println(name + " " + age);
    }
}</code></pre>
                """
        ));

        articles.put("oop-encapsulation", new ArticleData(
                "Інкапсуляція в Java",
                "Приховування внутрішнього стану об'єкта.",
                "oop-encapsulation",
                "oop",
                """
                <h2>Що таке інкапсуляція?</h2>

                <p>
                    Інкапсуляція — це принцип OOP, який означає приховування внутрішнього стану
                    об'єкта та надання доступу до нього через методи.
                </p>

                <h2>Приклад</h2>

                <pre><code>public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}</code></pre>
                """
        ));

        articles.put("oop-inheritance", new ArticleData(
                "Наслідування в Java",
                "Повторне використання коду через extends.",
                "oop-inheritance",
                "oop",
                """
                <h2>Що таке наслідування?</h2>

                <p>
                    Наслідування дозволяє одному класу отримати поля та методи іншого класу.
                    У Java для цього використовується ключове слово extends.
                </p>

                <h2>Приклад</h2>

                <pre><code>class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking");
    }
}</code></pre>
                """
        ));

        articles.put("oop-polymorphism", new ArticleData(
                "Поліморфізм у Java",
                "Один інтерфейс — багато реалізацій.",
                "oop-polymorphism",
                "oop",
                """
                <h2>Що таке поліморфізм?</h2>

                <p>
                    Поліморфізм дозволяє працювати з різними об'єктами через спільний тип.
                    Наприклад, змінна типу Animal може посилатися на Dog або Cat.
                </p>

                <h2>Приклад</h2>

                <pre><code>class Animal {
    void makeSound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Woof");
    }
}</code></pre>
                """
        ));

        articles.put("oop-abstraction", new ArticleData(
                "Абстракція в Java",
                "Виділення головного та приховування деталей реалізації.",
                "oop-abstraction",
                "oop",
                """
                <h2>Що таке абстракція?</h2>

                <p>
                    Абстракція дозволяє описати загальну поведінку без деталізації її реалізації.
                    У Java для цього використовуються abstract class та interface.
                </p>

                <h2>Приклад</h2>

                <pre><code>abstract class Shape {
    abstract double getArea();
}

class Circle extends Shape {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double getArea() {
        return Math.PI * radius * radius;
    }
}</code></pre>
                """
        ));

        articles.put("collections-list", new ArticleData(
                "List у Java",
                "Списки в Java: ArrayList та LinkedList.",
                "collections-list",
                "collections",
                """
                <h2>Що таке List?</h2>

                <p>
                    List — це колекція, яка зберігає елементи у визначеному порядку
                    та дозволяє дублікати.
                </p>

                <h2>Приклад ArrayList</h2>

                <pre><code>import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List&lt;String&gt; names = new ArrayList&lt;&gt;();
        names.add("Java");
        names.add("Spring");

        System.out.println(names);
    }
}</code></pre>
                """
        ));

        articles.put("collections-set", new ArticleData(
                "Set у Java",
                "Колекція, яка не дозволяє дублікати.",
                "collections-set",
                "collections",
                """
                <h2>Що таке Set?</h2>

                <p>
                    Set — це колекція, яка не дозволяє зберігати дублікати.
                    Найпоширеніші реалізації: HashSet, LinkedHashSet, TreeSet.
                </p>

                <h2>Приклад HashSet</h2>

                <pre><code>import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set&lt;String&gt; languages = new HashSet&lt;&gt;();
        languages.add("Java");
        languages.add("Java");
        languages.add("Spring");

        System.out.println(languages);
    }
}</code></pre>
                """
        ));

        articles.put("collections-map", new ArticleData(
                "Map у Java",
                "Зберігання даних у форматі key-value.",
                "collections-map",
                "collections",
                """
                <h2>Що таке Map?</h2>

                <p>
                    Map — це структура даних, яка зберігає пари ключ-значення.
                    Кожен ключ у Map має бути унікальним.
                </p>

                <h2>Приклад HashMap</h2>

                <pre><code>import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map&lt;String, Integer&gt; users = new HashMap&lt;&gt;();
        users.put("Ivan", 25);
        users.put("Olena", 30);

        System.out.println(users.get("Ivan"));
    }
}</code></pre>
                """
        ));

        articles.put("collections-queue", new ArticleData(
                "Queue у Java",
                "Черга в Java та принцип FIFO.",
                "collections-queue",
                "collections",
                """
                <h2>Що таке Queue?</h2>

                <p>
                    Queue — це колекція, яка працює за принципом FIFO:
                    first in, first out. Перший доданий елемент обробляється першим.
                </p>

                <h2>Приклад Queue</h2>

                <pre><code>import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue&lt;String&gt; queue = new LinkedList&lt;&gt;();

        queue.add("First");
        queue.add("Second");

        System.out.println(queue.poll());
    }
}</code></pre>
                """
        ));

        articles.put("spring-bean", new ArticleData(
                "Spring Bean",
                "Об'єкт, яким керує Spring Container.",
                "spring-bean",
                "spring",
                """
                <h2>Що таке Spring Bean?</h2>

                <p>
                    Spring Bean — це об'єкт, створенням і життєвим циклом якого керує Spring Container.
                </p>

                <h2>Приклад</h2>

                <pre><code>@Component
public class UserService {

    public void printUser() {
        System.out.println("User service");
    }
}</code></pre>
                """
        ));

        articles.put("spring-di", new ArticleData(
                "Dependency Injection",
                "Механізм передачі залежностей у Spring.",
                "spring-di",
                "spring",
                """
                <h2>Що таке Dependency Injection?</h2>

                <p>
                    Dependency Injection — це підхід, при якому об'єкт не створює свої залежності самостійно,
                    а отримує їх ззовні. У Spring цим займається контейнер.
                </p>

                <h2>Приклад constructor injection</h2>

                <pre><code>@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}</code></pre>
                """
        ));

        articles.put("spring-mvc", new ArticleData(
                "Spring MVC",
                "Model-View-Controller у Spring.",
                "spring-mvc",
                "spring",
                """
                <h2>Що таке Spring MVC?</h2>

                <p>
                    Spring MVC — це модуль Spring для створення веб-додатків за архітектурним шаблоном
                    Model-View-Controller.
                </p>

                <h2>Приклад Controller</h2>

                <pre><code>@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}</code></pre>
                """
        ));

        articles.put("spring-data-jpa", new ArticleData(
                "Spring Data JPA",
                "Спрощена робота з базою даних через repositories.",
                "spring-data-jpa",
                "spring",
                """
                <h2>Що таке Spring Data JPA?</h2>

                <p>
                    Spring Data JPA спрощує роботу з базою даних. Він дозволяє створювати repository
                    інтерфейси без ручного написання SQL для базових CRUD-операцій.
                </p>

                <h2>Приклад Repository</h2>

                <pre><code>public interface UserRepository extends JpaRepository&lt;User, Long&gt; {
    List&lt;User&gt; findByName(String name);
}</code></pre>
                """
        ));
    }

    public ArticleData findBySlug(String slug) {
        return articles.get(slug);
    }

    public void addArticle(ArticleData articleData) {
        articles.put(articleData.slug(), articleData);
    }

    public List<MenuSection> getMenuSections() {
        List<MenuSection> sections = new ArrayList<>();

        for (Map.Entry<String, String> menuGroup : menuGroups.entrySet()) {
            String groupCode = menuGroup.getKey();
            String groupTitle = menuGroup.getValue();

            List<ArticleData> groupArticles = articles.values()
                    .stream()
                    .filter(article -> article.menuGroup().equals(groupCode))
                    .toList();

            sections.add(new MenuSection(groupCode, groupTitle, groupArticles));
        }

        return sections;
    }
}