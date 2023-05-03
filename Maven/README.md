# Quality Assurance for Java Maven classes

Class attributes are starting with _ as a syntax.

Java 18 or higher is required.

### These classes can be used to improve the testing experience of JUnit with:

* The Laustrup Printer utility displaying
  * The items of ARRANGE
  * Performance of ACT
* More clean AAA code
* Super classes implemented for Bandwich models (can also be chosen to be without, which is recommended for non Bandwich projects)
* Attributes available:
  * _random, which is a simple Random for generating random values
  * _password, which is a password, that will be generated at each beforeEach
  * _addings and _adding, some objects that can be reused to insert into the acting method
  * _expected, the object that is to be expected
  * _exception, any kind of exception

### How to use

Firstly, the class used for the testing, needs to extend the Tester class

```class Tests extends Tester<T><R>```

The T is the type of input into lambda functions and R is the return.

Now Tester's methods, superclasses such as Arranger, Actor and Asserter is available for use.

Lambda functions are used to include functions in each steps, the method itself needs to be as a lambda function.

```
void canTest() {
  test(t ->
    ...
  );
}
```

Each step itself is including a lambda function, which can be like

```
void canTest() {
  test(t -> {
    R[] arrangement = arrange(e -> {0,1});
    
    R act = act(e -> methodToTest(arrangement[0],arrangement[1]));
    
    asserting(act, arrangement[0]);
    
    return end("canTest");
  });
}
```

Where end("canTest") is by defining, that the test is completed.