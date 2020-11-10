# Spring Testing Workshop - Workbook


## Exercise 1: Mocks

### 1. Open: A2BMapperWithoutExtensionTest


### 2. Initialise our mocks

Our SUT (Subject Under Test) maps As to Bs and it does it in 1 way. There is no permutation in the flow and so we only need to test a single flow.
Because of this we just have 1 @Test method.

Since we only have 1 @Test method we could initialise our mocks at the start of the @Test method.

Instead of that we will initialise them in a _BeforeEach_ method.
The reason for this will become clear when we replace this method with the JUnit Mockito Extension
and related annotations.

Replace
  
```java
//BEFORE-EACH
```
  
with: 
```java
    @BeforeEach
    public void doWhatTheMockitoRunnerDoesForUs() {
    }
```

>@BeforeEach is used to signal that the annotated method should be executed before each @Test method in the current test class.
> @BeforeEach methods must have a void return type, must not be private, and must not be static. 

Next, let's fill it in:

```java
    @BeforeEach
    public void doWhatTheMockitoRunnerDoesForUs() {
        cService = Mockito.mock(CService.class);
        sut = new A2BMapper(cService);
    }
```

Programatically create Mockito mocks by calling Mockito's static mock method. Here we mock our dependency, the CService, so that we can alter it's behave when our SUT interacts with it.
```java
        cService = Mockito.mock(CService.class);
```
This creates a mocked instance of our CService class.

I prefer static imports, so most of the time in this workshop you'll see:
```java
        cService = mock(CService.class);
```


```java
        sut = new A2BMapper(cService);
```
Finally, we inject the dependency into our SUT.

### 3. Create the test method 

Let's now set up the actual test.

> @Disabled: Turns off a test.

Replace :
```java
    @Test
    @Disabled
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
    }
```

With a set up of some test variables:
```java
@Test
public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
         final A a = new A();
         final C c = new C();
    }
```
We could even mock these if we wanted to be very strict and mock all code that is not within our SUT. But I think that's overkill.


A mockito mocked method that has a non-void return type will give back default values. _null_ for objects and default values for primitives.
We can override this behaviour by using various methods, the most common of which being
the _when()_ method.

Expand the test to include the following:
```java
@Test
public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        System.out.println("C findCForA before mocking: " + cService.findCForA(a));
        when(cService.findCForA(a)).thenReturn(c);
        System.out.println("C findCForA after mocking: " + cService.findCForA(a));
}
```
And then run the test.

Look at the console and see how before mocking we get the default null but after mocking
we receive the value we told Mockito to return.

Now let's call our test method and assert our expected behaviour.

```java
@Test
public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final A a = new A();
        final C c = new C();
        
        System.out.println("C findCForA before mocking: " + cService.findCForA(a));
        when(cService.findCForA(a)).thenReturn(c);
        System.out.println("C findCForA after mocking: " + cService.findCForA(a));
        
        final B output = sut.map(a);
        
        assertThat(output).isNotNull();
        assertThat(output.getA()).isEqualTo(a);
        assertThat(output.getC()).isEqualTo(c);
}
```

The assertions at the end of the test are done using the fluent API of AssertJ that comes packaged with Spring-Boot-Starter-Test.

Run the test.

The test should pass.

Lastly we will check to verify that our SUT is using the dependency we inject, the CService, in the expected way.

Add the following line after the assertions and then re-run the test:

```java
Mockito.verify(cService).findCForA(a);
```

Error!

What verify(someMock).someMethodOfTheMock(); says is "verify that someMock.someMethodOfTheMock() was called 1 time and 1 time only"

We are calling our verified method 3 times, twice in the test method and once from the SUT and so the assertion fails.

We can specify a different number of times with the verify method like this:
```java
verify(cService, times(3)).findCForA(a);
verify(cService, never()).findCForA(a);
verify(cService, atMost(3)).findCForA(a);
verify(cService, atLeast(3)).findCForA(a);
```

### 4. Let Mockito initialise

Open: **A2BMapperTest**

In order to use Mockito annotations to do our initialisation we need to extend Junit with Mockito.

Add
```java
@ExtendWith(MockitoExtension.class)
``` 
To the class declaration. This allows Mockito to scan our test class for Mockito annotations and take necessary actions.

Add
```java
@Mock
``` 
to the cService field we mocked by calling Mockito.mock() in **A2BMapperWithoutExtensionTest**

thus:
```java
@Mock
private CService cService;
```

If we were to start the test now then Mockito would effectively do the following:
```java
private CService cService = Mockito.mock(CService.class);
```

Next we can tell Mockito to construct a **real** instance of our SUT but to inject our Mocks as dependencies.

Add this annotation to our SUT:
```java
    @InjectMocks
    private A2BMapper sut;
```

Mockito will look at the constructor of A2BMapper:
```java
public A2BMapper(final CService cService) {
        this.cService = cService;
    }
```
and see that we need an CService parameter. 
Mockito then looks at which Mocks it has created with @Mock.
We have only 1 mock and it is of type CService.

Bingo.

Mockito will then _effectively_ run the following during test execution:
```java
    private A2BMapper sut = new A2BMapper(cService);
```

If we had other mocked instances they would just be left alone during this _InjectMocks_ process.

You can probably also start to see what the limitations of this Mockito extension are. It's doing its best
guess. It sees we need an AgeLookupService parameter sees we have one lying around and just plonks it into the constructor.

But what if we have two mocked instances of CService in our test? Then Mockito will just
drunkenly take a stab in the dark and pick one of them. What if the constructor contains two CService's ? Then mockito will just reuse the same mocked instance it finds twice.

When things are not **100% unambiguous** for Mockito then it's best to do things **programmatically**.

Finally remove @Disabled and run the test.

## Advanced challenge:

For the people that knew everything and got to the bottom of the workbook very quickly.

Make a unit test for A2BMapper using mocking ... without Mockito.

Instead create your own mocked class by using a CGLib proxy class mechanism.

Here is a hint to get started:
```java
        Enhancer e = new Enhancer();
        e.setClassLoader(CService.class.getClassLoader());
        e.setSuperclass(CService.class);
        e.setCallback(???????);
        final CService cService = (CService) e.create(new Class[]{OtherDepedencyWeDontWantToTest.class}, new Object[]{null});
```

An excellent deep dive into proxy magic and Spring can be found on youtube. Given by Victor Rentea, perhaps the fastest live coder I've ever seen code in person: https://www.youtube.com/watch?v=HbbvyZh3IZo 